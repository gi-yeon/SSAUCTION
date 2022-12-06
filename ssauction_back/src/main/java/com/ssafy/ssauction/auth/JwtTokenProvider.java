package com.ssafy.ssauction.auth;

import com.ssafy.ssauction.service.users.UsersService;
import com.ssafy.ssauction.web.dto.users.UsersAuthResponseDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {


    //secretkey
    private Key accessKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    //유효시간 30분 (access token)
    private long tokenValidTime = 1000 * 60 * 30L;


    private final UserDetailsService userDetailsService;

    //secretkey Base64 인코딩
    String encodedAccessString = Base64.getEncoder().encodeToString(accessKey.getEncoded());

    //key 변환
    //base64를 byte[]로 변환
    byte[] decodedAccessByte = Base64.getDecoder().decode(encodedAccessString.getBytes());

    SecretKey keyForAccessToken = Keys.hmacShaKeyFor(decodedAccessByte);


    //JWT 토큰 생성
    public String createAccessToken(Map<String, Object> userMap) {
        return Jwts.builder().setHeaderParam("typ", "JWT")
                .claim("userEmail", userMap.get("userEmail")).claim("userNickname", userMap.get("userNickname")).claim("roles", userMap.get("authority"))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidTime))
                .signWith(keyForAccessToken).compact();

    }


    //refresh token 생성 (유효시간 3일)
    public String createRefreshToken(String userEmail) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim("userEmail", userEmail)
                .setSubject(userEmail)
                .setExpiration(new Date(System.currentTimeMillis() + 1000* 60 * 30 * 48 * 3L))
                .signWith(keyForAccessToken).compact();
    }

    //토큰에서의 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //토큰에서 회원 정보 추출
    public String getUserEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(keyForAccessToken)
                .build().parseClaimsJws(token).getBody().get("userEmail", String.class);
    }

    public String getUserNo(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(keyForAccessToken)
                .build().parseClaimsJws(token).getBody().get("userNo", String.class);
    }

    public String getUserNickname(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(keyForAccessToken)
                .build().parseClaimsJws(token).getBody().get("userNickname", String.class);
    }

    //Cookie에서 token 값을 가져온다.
    public String resolveToken(HttpServletRequest request) {

        String token = null;
        Cookie cookie = WebUtils.getCookie(request, "accessToken");
        if(cookie != null) token = cookie.getValue();
        return token;
    }

    //accessToken의 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(keyForAccessToken).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            System.out.println("token expired");
            return false;
        } catch (PrematureJwtException e) {
            System.out.println("premature");
            return false;
        } catch (ClaimJwtException e) {
            System.out.println("claimJwtException");
            return false;
        } catch (MalformedJwtException e) {
            System.out.println("malformed");
            return false;
        } catch (SignatureException e) {
            System.out.println("signature");
            return false;
        } catch (UnsupportedJwtException e) {
            System.out.println("unsupported");
            return false;
        } catch (JwtException e) {
            return false;
        }
    }

    //refreshToken의 유효성 검증 ->유효하다면 새로운 accessToken 생성, 유효하지 않다면 null 반환
    public String validateRefreshToken(String refreshToken, Map<String, Object> userMap) {

        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(keyForAccessToken).build().parseClaimsJws(refreshToken);
            if (!claims.getBody().getExpiration().before(new Date())) {

                String newAccessToken = createAccessToken(userMap);
                return newAccessToken;
            } else {
                return null;
            }
        } catch (JwtException e) {
            return null;
        }
    }


}
