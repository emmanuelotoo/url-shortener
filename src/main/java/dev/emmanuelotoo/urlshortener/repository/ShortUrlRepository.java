package dev.emmanuelotoo.urlshortener.repository;

import dev.emmanuelotoo.urlshortener.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
    Optional<ShortUrl> findByCode(String code);
    boolean existsByCode(String code);
}
