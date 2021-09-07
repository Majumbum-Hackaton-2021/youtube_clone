package com.majumbum.youtube_clone.scopes.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.base.Strings;
import com.majumbum.youtube_clone.scopes.user.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class TokenServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    private static final String TOKEN_SECRET = "s4T2zOIWHMM1sxq";

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * Creates a Json-Web-Token based on the given email and current Date
     *
     * @param user
     * @return
     */


    public String createToken(User user) {
        return Jwts.builder()
                .setSubject((user.getEmail()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET)
                .compact();
    }

    /**
     * Extracts Email from the given Token
     *
     * @param token
     * @return
     */

    public String getEmailFromToken(String token) {
        return Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Extracts date from the given Token
     *
     * @param token
     * @return The date when the token was created or null
     */
    public Date getDateFromToken(String token) {
        if (Strings.isNullOrEmpty(token)) {
            throw new IllegalArgumentException("Can't get the Date from empty or null Token");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("createdAt").asDate();
        } catch (JWTVerificationException exception) {
            logger.error("Internal Error: JWT-Token verification failed");
        } catch (Exception e) {
            logger.error("Internal Error: Wrong Encoding Message in JWT-Token");
        }
        return null;
    }

    public boolean isTokenValidWithDate(String token) {
        String email = this.getEmailFromToken(token);
        Date date = this.getDateFromToken(token);
        Calendar currentDateBeforeOneDay = Calendar.getInstance();
        currentDateBeforeOneDay.add(Calendar.DAY_OF_MONTH, -1);
        return (email != null) && !(date.before(currentDateBeforeOneDay.getTime()));
    }

    public boolean isTokenValid(String token) {
        String email = this.getEmailFromToken(token);
        return email != null;
    }


}
