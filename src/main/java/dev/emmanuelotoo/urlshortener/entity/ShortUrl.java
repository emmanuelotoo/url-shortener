package dev.emmanuelotoo.urlshortener.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "short_urls", indexes = {
        @Index(name = "idx_code_unique", columnList = "code", unique = true)
})
public class ShortUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 32)
    private String code;

    @Column(name = "target_url", nullable = false, length = 2048)
    private String targetUrl;

    private Instant expiresAt;
    private long hits = 0;
    private Instant lastAccessedAt;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;
}
