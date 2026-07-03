package org.nityam.sarathi.Configuration;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.nityam.sarathi.Model.User;
import org.nityam.sarathi.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {

    @Autowired
    UserRepo userRepo;

    public String getToken(String username){
        Map<String,Object> claims = new HashMap<>();
        return  Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*3))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey()  {
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            keyGenerator.init(256);
            Key key1 = keyGenerator.generateKey();
            return key1;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private Key key=getKey();

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean validateToken(String token, String username) {
        User u=userRepo.findByUsername(username);
        if(u!=null && !isTokenExpired(token)) {
            return true;
        }
        return false;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }
}
