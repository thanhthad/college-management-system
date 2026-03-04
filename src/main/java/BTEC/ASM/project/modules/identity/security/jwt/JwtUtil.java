package BTEC.ASM.project.modules.identity.security.jwt;

import BTEC.ASM.project.modules.identity.security.userdetails.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "VERY_SECRET_KEY_12345678901234567890";
    private final long ACCESS_TOKEN_EXPIRE = 1000 * 60 * 15; // 15 phút

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateAccessToken(Long userId, String userCode, List<String> roles) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("userCode", userCode)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public CustomUserDetails getUserDetails(String token) {
        Claims claims = extractClaims(token);

        Long userId = Long.parseLong(claims.getSubject());
        String userCode = claims.get("userCode", String.class);
        List<String> roles = claims.get("roles", List.class);

        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setId(userId);
        customUserDetails.setUserCode(userCode);
        customUserDetails.setAuthorities(authorities);
        customUserDetails.setStatus("ACTIVE");

        return customUserDetails;
    }

    public String getUserCode(String token) {
        return extractClaims(token).get("userCode", String.class);
    }


    public List<String> getRoles(String token) {
        Claims claims = extractClaims(token);
        return claims.get("roles", List.class);
    }


    public Long getUserId(String token) {
        return Long.parseLong(extractClaims(token).getSubject());
    }

    public boolean isTokenValid(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
