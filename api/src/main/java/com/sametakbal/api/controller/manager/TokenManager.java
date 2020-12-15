package com.sametakbal.api.controller.manager;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class TokenManager {

    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String getToken(int id) {
        Date currentDate = new Date(System.currentTimeMillis());
        Date expDate = new Date(System.currentTimeMillis() + 86400000);

        return Jwts.builder()
                .setId(String.valueOf(id))
                .setIssuedAt(currentDate)
                .setExpiration(expDate)
                .signWith(key).compact();
    }

    public Jws<Claims> encode(String jwsString) {
        Jws<Claims> jws = null;

        try {
            jws = Jwts.parserBuilder()  // (1)
                    .setSigningKey(key)         // (2)
                    .build()                    // (3)
                    .parseClaimsJws(jwsString); // (4)

            // we can safely trust the JWT
        } catch (JwtException ex) {       // (5)
            System.out.println(ex.getMessage());
        }
        return jws;
    }

    public boolean isExpired(String jwsString) {
        Jws<Claims> jws = null;
        try {
            jws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwsString);

        } catch (JwtException ex) {
            System.out.println(ex.getMessage());
        }
        return jws.getBody().getExpiration().after(jws.getBody().getIssuedAt());
    }

}
