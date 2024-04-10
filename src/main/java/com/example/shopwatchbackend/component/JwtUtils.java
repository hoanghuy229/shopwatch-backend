package com.example.shopwatchbackend.component;

import com.example.shopwatchbackend.models.Customer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtils {
    @Value("${jwt.secretkey}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(Customer customer) throws Exception {
        Map<String, Object> claims = new HashMap<>();
        claims.put("phoneNumber",customer.getPhoneNumber());
        claims.put("customerId",customer.getCustomerId());
        try{
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(customer.getPhoneNumber())
                    .setExpiration(new Date(System.currentTimeMillis()+ expiration))
                    .signWith(getSignedKey(),SignatureAlgorithm.HS256)
                    .compact();
        }
        catch (Exception e){
            throw new Exception("cannot create token");
        }
    }

    private Key getSignedKey(){
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(getSignedKey()).build().parseClaimsJws(token).getBody();
    }

    private <T> T extractClaim(String token, Function<Claims,T> ClaimResolver){
        final Claims claims = extractAllClaims(token);
        return ClaimResolver.apply(claims);
    }

    public Boolean tokenExpired(String token){
        Date date = extractClaim(token,Claims::getExpiration);
        return date.before(new Date());
    }
    public String extractSubject(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public Boolean validated(String token,Customer customer){
        final String phoneNumber = extractSubject(token);
        return (phoneNumber.equals(customer.getPhoneNumber()) && !tokenExpired(token));
    }

}
