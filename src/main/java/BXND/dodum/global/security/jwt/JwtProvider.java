package BXND.dodum.global.security.jwt;

import BXND.dodum.domain.auth.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@Slf4j
public class JwtProvider {
    private final SecretKey secretKey;
    @Value("${spring.jwt.access-expiration}")
    private long AccessExpiration;
    @Value("${spring.jwt.refresh-expiration}")
    private long RefreshExpiration;

    public JwtProvider(@Value("${spring.jwt.secret}")String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String AccessToken(String username, Role role) {
        Date now  = new Date();
        Date accessexpiration = new Date(now.getTime() + AccessExpiration);
        return Jwts.builder()
                .subject("ACCESS")
                .claim("username", username)
                .claim("role", role)
                .issuedAt(now)
                .expiration(accessexpiration)
                .signWith(secretKey)
                .compact();
    }

    public String RefreshToken(String username) {
        Date now  = new Date();
        Date refreshexpiration = new Date(now.getTime() + RefreshExpiration);
        return Jwts.builder()
                .subject("REFRESH")
                .claim("username", username)
                .issuedAt(now)
                .expiration(refreshexpiration)
                .signWith(secretKey)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.get("username", String.class);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }
        catch (Exception e) {
            log.error("에러 발생: {}", e.getMessage(), e);
            return false;
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


}
