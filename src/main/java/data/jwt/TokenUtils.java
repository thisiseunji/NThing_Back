package data.jwt;

import data.dto.UserDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtils {
    private String SECRET_KEY;
    private String REFRESH_KEY;
    private String DATA_KEY = "id";

    @Autowired
    public TokenUtils(@Value("${secret_key}") String SECRET_KEY, @Value("${refresh_key}") String REFRESH_KEY) {
        this.SECRET_KEY = SECRET_KEY;
        this.REFRESH_KEY = REFRESH_KEY;
    }


    // 엑세스 토큰 생성
    public String generateJwtToken(UserDto userDto) {

        System.out.println(SECRET_KEY);
        System.out.println(REFRESH_KEY);
        return Jwts.builder()
                .setSubject(userDto.getId()+"")
                .setHeader(createHeader())
                .setClaims(createClaims(userDto))
                .setExpiration(createExpireDate(1000 * 60 * 60 * 24)) // 24시간
                .signWith(SignatureAlgorithm.HS256, createSigningKey(SECRET_KEY))
                .compact();
    }

    // 리프레시토큰 생성
    public String saveRefreshToken(UserDto userDto) {
        return Jwts.builder()
                .setSubject(userDto.getId()+"")
                .setHeader(createHeader())
                .setClaims(createClaims(userDto))
                .setExpiration(createExpireDate(1000 * 60 * 300))
                .signWith(SignatureAlgorithm.HS256, createSigningKey(REFRESH_KEY))
                .compact();
    }

    // access, refresh 토큰 유효성 확인 => 토큰값을 String으로 넘긴다.
    public boolean isValidToken(String token) {
        try {
            Claims accessClaims = getClaimsFormToken(token);
            System.out.println(accessClaims.toString());
            System.out.println("Access expireTime: " + accessClaims.getExpiration());
            System.out.println("Access userId: " + accessClaims.get("id"));
            return true;
        } catch (ExpiredJwtException exception) {
            System.out.println("Token Expired UserID : " + exception.getClaims().getSubject());
            return false;
        } catch (JwtException exception) {
            System.out.println("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            System.out.println("Token is null");
            return false;
        }
    }

    public boolean isValidRefreshToken(String token) {
        try {
            Claims accessClaims = getClaimsToken(token);
            System.out.println("Access expireTime: " + accessClaims.getExpiration());
            System.out.println("Access userId: " + accessClaims.get("id"));
            return true;
        } catch (ExpiredJwtException exception) {
            System.out.println("Token Expired UserID : " + exception.getClaims().getSubject());
            return false;
        } catch (JwtException exception) {
            System.out.println("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            System.out.println("Token is null");
            return false;
        }
    }

    // 유효시간 설정
    private Date createExpireDate(long expireDate) {
        long curTime = System.currentTimeMillis();
        return new Date(curTime + expireDate);
    }

    // 토큰 생성시 Header 부분 생성
    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "ACCESS_TOKEN");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());

        return header;
    }

    // Payload 생성
    private Map<String, Object> createClaims(UserDto userDto) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(DATA_KEY, userDto.getId()); // 왜 얘가 null이 들어갈까? 유틸을 갈아끼워야 하나?
        return claims;
    }

    // 해당 키로 암호화
    private Key createSigningKey(String key) {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    // 유효성 검색을 위해 token 정보를 읽어옴
    private Claims getClaimsFormToken(String token) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(token)
                .getBody();
    }
    // 유효성 검색을 위해 token 정보를 읽어옴
    private Claims getClaimsToken(String token) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(REFRESH_KEY))
                .parseClaimsJws(token)
                .getBody();
    }
}