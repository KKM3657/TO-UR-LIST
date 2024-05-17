package com.eminyidle.gateway.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * jwt 발급 jwt 검증
 */

@Component
public class JWTUtil {

    private final Key key;

    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {

        byte[] byteSecretKey = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(byteSecretKey); // 객체 key 생성
    }

    // username 검증
    public String getUserId(String token) {
        //sigingkey 부분이 유효성 검증하는 부분
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody()
                .get("userId", String.class);
    }

    // 남은 시간 가져오기
    public Long getExpiredDate(String token) {
        Date expiredate = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
                .getBody().getExpiration();
        Date now = new Date();
        return expiredate.getTime() - now.getTime();
    }

    // 토큰 유효 확인
    public Boolean isValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isInvalid(String token) {
        return !isValid(token);
    }
}