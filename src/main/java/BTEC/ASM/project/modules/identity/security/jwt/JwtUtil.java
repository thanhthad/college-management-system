package BTEC.ASM.project.modules.identity.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final long ACCESS_TOKEN_EXP = 15 * 60 * 1000; // 15 phút
    private final long REFRESH_TOKEN_EXP = 7 * 24 * 60 * 60 * 1000; // 7 ngày

    public String generateAccessToken(Long userId, String email, List<String> roles) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("email", email)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXP))
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(Long userId) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXP))
                .signWith(key)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
