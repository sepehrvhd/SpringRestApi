package com.example.project.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {


    private static final String secretKey ="a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7r8s9t0u1v2w3x4y5z6";

//claims in payload are give some information about an entity typically user (گزاره)
    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
    final Claims claims=extractAllClaims(token);
    return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    public String generateToken(Map<String,Object> extraclaims,
                                UserDetails userDetails){
        return Jwts.builder().setClaims(extraclaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+20 * 60 * 1000)).signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public String exractUsername(String token) {
        return extractClaim(token,Claims ::getSubject);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token).getBody();
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username=exractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractEpiration(token).before(new Date());
    }

    private Date extractEpiration(String token)  {
        return extractClaim(token, Claims::getExpiration);
    }


    private Key getSigninKey() {
        byte[] keyBytes= Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
