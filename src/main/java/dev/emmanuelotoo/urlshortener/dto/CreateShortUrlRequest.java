package dev.emmanuelotoo.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateShortUrlRequest(
        @NotBlank(message = "URL is required")
        @Size(max = 2048, message = "URL is too long")
        String url,

        @Size(min = 3, max = 32, message = "Alias must be between 3 and 32 characters")
        String alias,

        Integer expiryDays
) {}
