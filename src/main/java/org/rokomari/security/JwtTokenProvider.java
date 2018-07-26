package org.rokomari.security;

import io.jsonwebtoken.*;
import org.rokomari.models.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Abdullah Al Amin on 7/26/2018.
 */
@Component
public class JwtTokenProvider {

    @Value("${application.jwtSecret}")
    private String jwtSecret;

    @Value("${application.jwtExpiresInMS}")
    private int expiresInMS;

    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    public String generateToken(Authentication authentication){

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        Date today = new Date();
        Date expirationDate = new Date(today.getTime()+expiresInMS);

        return Jwts
                .builder()
                .setSubject(Integer.toString(principal.getId()))
                .setIssuedAt(today)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

    }

    public int getUserIdFromJwt(String token){
        Claims claims = Jwts
                        .parser()
                        .setSigningKey(jwtSecret)
                        .parseClaimsJws(token)
                        .getBody();
        return Integer.parseInt(claims.getSubject());
    }

    public boolean validateToken(String token){
        try {
            Jwts
                    .parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token);

            return true;

        }catch (SignatureException e){
            log.error("invalid jwt signature");
        }catch (MalformedJwtException e){
            log.error("invalid jwt token");
        }catch (ExpiredJwtException e){
            log.error("expired jwt token");
        }catch (UnsupportedJwtException e){
            log.error("unsupported jwt token");
        }catch (IllegalArgumentException e){
            log.error("jwt claims string is empty");
        }
        return false;
    }

}
