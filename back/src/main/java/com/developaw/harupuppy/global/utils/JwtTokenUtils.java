package com.developaw.harupuppy.global.utils;

import com.developaw.harupuppy.domain.user.dto.TokenDto;
import com.developaw.harupuppy.domain.user.dto.response.UserDetailResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtTokenUtils {
    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.access-expired-time-ms}")
    private Long accessExpiredTimeMs;
    @Value("${jwt.refresh-expired-time-ms}")
    private Long refreshExpiredTimeMs;

    public TokenDto generateToken(UserDetailResponse response) {
        Claims claims = Jwts.claims();
        Long userId = response.userId();
        String sessionId = UUID.randomUUID().toString();
        claims.put("userId", userId);
        claims.put("email", response.email());
        claims.put("sessionId", sessionId);

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiredTimeMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiredTimeMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return new TokenDto(accessToken, refreshToken, getKey(userId, sessionId));
    }

    public boolean isExpired(String token) {
        Date expiredDate = extractClaims(token).getExpiration();
        return expiredDate.before(new Date());
    }

    public Long resolveUserId(String token) {
        return extractClaims(token).get("userId", Long.class);
    }

    public String resolveTokenKey(String token) {
        Claims claims = extractClaims(token);
        Long userId = claims.get("userId", Long.class);
        String sessionId = claims.get("sessionId", String.class);
        return getKey(userId, sessionId);
    }

    private String getKey(Long userId, String sessionId) {
        return String.format("%d_%s", userId, sessionId);
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
