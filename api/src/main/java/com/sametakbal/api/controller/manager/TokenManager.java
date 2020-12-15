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

    public Jws<Claims> encode(String jwsString)  throws JwtException{
        Jws<Claims> jws = null;
            jws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwsString);
        return jws;
    }

    public boolean isExpired(String jwsString) {
        Jws<Claims> jws = null;
        try {
            jws = encode(jwsString);
        } catch (JwtException ex) {
            System.out.println(ex.getMessage() +"--");
        }

        return jws != null && jws.getBody().getExpiration().after(jws.getBody().getIssuedAt());
    }

    public int getUserId(String token){
        Jws<Claims> jws = null;
        try {
            jws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

        } catch (JwtException ex) {
            System.out.println(ex.getMessage());
        }
        return Integer.parseInt(jws.getBody().getId());
    }

}
