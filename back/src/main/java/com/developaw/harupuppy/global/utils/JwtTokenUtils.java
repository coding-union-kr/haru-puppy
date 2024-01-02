package com.developaw.harupuppy.global.utils;

import com.developaw.harupuppy.domain.user.dto.TokenDto;
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

    public TokenDto generateToken(String email) {
        Claims claims = Jwts.claims();
        claims.put("email", email);

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiredTimeMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        String refreshToken = UUID.randomUUID().toString();

        return new TokenDto(accessToken, refreshToken);
    }

    public boolean isExpired(String token) {
        Date expiredDate = extractClaims(token).getExpiration();
        return expiredDate.before(new Date());
    }

    public String resolveToken(String token) {
        return extractClaims(token).get("email", String.class);
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}