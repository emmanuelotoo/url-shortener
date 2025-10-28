package dev.emmanuelotoo.urlshortener.service;

import dev.emmanuelotoo.urlshortener.dto.CreateShortUrlRequest;
import dev.emmanuelotoo.urlshortener.entity.ShortUrl;
import dev.emmanuelotoo.urlshortener.repository.ShortUrlRepository;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class UrlService {
    private final ShortCodeService codeService;
    private final ShortUrlRepository repository;

    public UrlService(ShortCodeService codeService, ShortUrlRepository repository) {
        this.codeService = codeService;
        this.repository = repository;
    }

    public ShortUrl create(CreateShortUrlRequest request) {
        String target = normalizeUrl(request.url());
        String code = request.alias() != null
                ? codeService.validateCustomAlias(request.alias())
                :codeService.generateUniqueCode();

        Instant expiresAt = null;
        if (request.expiryDays() != null && request.expiryDays() > 0) {
            expiresAt = Instant.now().plus(request.expiryDays(), ChronoUnit.DAYS);
        }

        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setCode(code);
        shortUrl.setTargetUrl(target);
        shortUrl.setExpiresAt(expiresAt);
        shortUrl.setCreatedAt(Instant.now());
        return repository.save(shortUrl);
    }

    public Optional<ShortUrl> lookUpActive(String code) {
        return repository.findByCode(code)
                .filter(url -> url.getExpiresAt() == null || url.getExpiresAt().isAfter(Instant.now()));
    }

    public void registerHit(ShortUrl url) {
        url.setHits(url.getHits() + 1);
        url.setLastAccessedAt(Instant.now());
        repository.save(url);
    }

    private String normalizeUrl(String input) {
        try {
            URI uri = new URI(input.trim());
            if (uri.getScheme() == null) {
                uri = new URI("https://" + input.trim());
            }
            if (!uri.getScheme().equalsIgnoreCase("http") && !uri.getScheme().equalsIgnoreCase("https")) {
                throw new IllegalArgumentException("Only HTTP and HTTPS are supported");
            }
            return uri.normalize().toString();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URL");
        }
    }
}