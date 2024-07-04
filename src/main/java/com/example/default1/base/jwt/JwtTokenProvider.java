package com.example.default1.base.jwt;

import com.example.default1.base.redis.RedisObject;
import com.example.default1.base.redis.RedisRepository;
import com.example.default1.exception.CustomException;
import com.example.default1.utils.StringUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {
    private static final Supplier<Date> refreshTokenExpiresInSupplier = () -> new Date((new Date()).getTime() + 60 * 60 * 24 * 3 * 1000); // 3일
    private static final Supplier<Date> accessTokenExpiresInSupplier = () -> new Date(System.currentTimeMillis() + 60 * 15 * 1000); // 15분

    private final RedisRepository redisRepository;
    private final Key key;

    public JwtTokenProvider(RedisRepository redisRepository, @Value("${jwt.secret}") String secretKey) {
        this.redisRepository = redisRepository;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * SecretKey 생성 메서드
     *
     * @return Base64로 인코딩된 SecretKey
     * @throws NoSuchAlgorithmException
     */
    public static String createSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        keyGenerator.init(256);
        SecretKey secretKey = keyGenerator.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    /**
     * 유저 정보를 가지고 RefreshToken, AccessToken 생성하는 메서드
     * @param authentication
     * @return
     */
    public JwtTokenInfo createJwtTokenInfo(Authentication authentication) {
        String loginId = authentication.getName();
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String refreshToken = this.createRefreshToken(loginId, authorities);
        String accessToken = this.createAccessToken(refreshToken);
        redisRepository.save(new RedisObject(authentication.getName(), refreshToken, 3L));

        return JwtTokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     *  loginId와 권한으로 refreshToken 생성
     * @param loginId
     * @param authorities
     * @return
     */
    public String createRefreshToken(String loginId, String authorities) {
        return Jwts.builder()
                .setSubject(loginId)
                .claim("auth", authorities)
                .setExpiration(refreshTokenExpiresInSupplier.get())
                .setIssuer(loginId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    /**
     * 리프레쉬 토큰 삭제
     *
     * @param refreshToken 
     */
    public void removeRefreshToken(String refreshToken) {
        StringUtils.ifNotBlank(refreshToken, it -> redisRepository.deleteRawByKey(refreshToken));
    }

    /**
     * refreshToken을 이용해서 엑세스토큰 생성하기
     * @param refreshToken
     * @return
     */
    public String createAccessToken(String refreshToken) {
        Claims claims = this.parseClaimsByToken(refreshToken);
        String loginId = claims.getSubject();
        String auth = (String) claims.get("auth");

        return Jwts.builder()
                .setSubject(loginId)
                .claim("auth", auth)
                .setExpiration(accessTokenExpiresInSupplier.get())
                .setIssuer(loginId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    /**
     * accessToken 재발급
     * @param jwtTokenInfo
     * @return
     */
    public JwtTokenInfo createAccessToken(JwtTokenInfo jwtTokenInfo) {
        String refreshToken = jwtTokenInfo.getRefreshToken();
        Claims claims = this.parseClaimsByToken(refreshToken);
        String loginId = (String) claims.get("loginId");

        try {
            if (StringUtils.isBlank(refreshToken)) {
                throw new CustomException(2999, "토큰값이 존재하지 않습니다.");
            }

            if(this.validateToken(refreshToken)) {
                // 1. redis에서 token 정보를 찾을수가 없을때
                RedisObject redisObject = redisRepository.findValueByKey(loginId);
                String originRefreshToken = redisObject.getValue();

                // 2. refreshToken이 새로발급됬을때
                if (!originRefreshToken.equals(refreshToken)) {
                    throw new CustomException(2997, "중복로그인이 발생했습니다. 재로그인 해주세요.");
                }
            }
        } catch (Exception e) {
            throw new CustomException(2997, "refresh 토큰이 만료되었습니다. 재로그인 해주세요.");
        }

        return JwtTokenInfo.builder()
                .grantType("Bearer")
                .accessToken(this.createAccessToken(refreshToken))
                .build();
    }

    /**
     * token으로 claims 추출
     *
     * @param token
     * @return
     */
    public Claims parseClaimsByToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        log.info("parseClaimsByToken claims: {}", claims);

        try {
            return claims;
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    /**
     * JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
     *
     * @param token
     * @return
     */
    public Authentication getAuthenticationByToken(String token) {
        // 토큰 복호화
        Claims claims = parseClaimsByToken(token);

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }


    /**
     * 토큰 유효성 검사
     *
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT Token", e);
            throw new CustomException(2999, "유효하지 않은 JWT 토큰입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT Token", e);
            throw new CustomException(2999, "만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT Token", e);
            throw new CustomException(2999, "지원하지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty.", e);
            throw new CustomException(2999, "JWT 클레임 문자열이 비어 있습니다.");
        } catch (JwtException e) {
            log.error("JWT processing error.", e);
            throw new CustomException(2999, "JWT 처리 오류입니다.");
        }
    }
}