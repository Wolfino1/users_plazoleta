package com.plazoleta.usuarios.infrastructure.security;

import com.plazoleta.usuarios.common.configurations.util.Constants;
import com.plazoleta.usuarios.domain.exceptions.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final String SECRET_KEY      = Constants.SECRET_KEY;
    private static final long   EXPIRATION_TIME = Constants.EXPIRATION_TIME;

    public String generateToken(CustomUserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        String fullRole = userDetails.getAuthorities().stream()
                .findFirst()
                .map(a -> a.getAuthority().replaceFirst("^ROLE_", ""))
                .orElse("UNKNOWN");
        claims.put("role", fullRole);

        Long id = userDetails.getId();
        switch (fullRole) {
            case "ADMIN"    -> claims.put("adminId", id);
            case "OWNER"    -> {
                claims.put("ownerId", id);
                Long restId = userDetails.getRestaurantId();
                if (restId != null) {
                    claims.put("restaurantId", restId);
                }
            }

            case "EMPLOYEE" -> {
                claims.put("employeeId", id);
                Long restId = userDetails.getRestaurantId();
                if (restId != null) {
                    claims.put("restaurantId", restId);
                }
            }
            case "CLIENT"   -> claims.put("clientId", id);
        }

        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + EXPIRATION_TIME))
                .signWith(
                        Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)),// La clave hacerla variable de entorno
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserIdFromSecurityContext() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new UnauthorizedException(Constants.USER_NOT_AUTHENTICATED);
        }

        Object principal = auth.getPrincipal();
        if (principal instanceof CustomUserDetails cud) {
            return cud.getId();
        }
        if (principal instanceof Claims claims) {
            Number ownerId = claims.get("ownerId", Number.class);
            if (ownerId != null) {
                return ownerId.longValue();
            }
        }

        throw new UnauthorizedException(Constants.USER_NOT_AUTHENTICATED);
    }

}

