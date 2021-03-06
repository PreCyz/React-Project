package react.pw.carly.security.services;

import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@AllArgsConstructor
public class JwtTokenService implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    private final String secret;
    private final long expirationMs;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
//        if (userDetails.isAccountNonExpired()){
//            claims.put("isAccountNonExpired",true);
//        }else {
//            claims.put("isAccountNonExpired",false);
//        }

        return doGenerateToken(claims, userDetails.getUsername());
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject) {
//        if ((boolean)claims.get("isAccountNonExpired")){
//            return Jwts.builder()
//                    .setClaims(claims)
//                    .setSubject(subject)
//                    .setIssuedAt(new Date(System.currentTimeMillis()))
//                    .signWith(SignatureAlgorithm.HS512, secret)
//                    .compact();
//        }else{
//            return Jwts.builder()
//                    .setClaims(claims)
//                    .setSubject(subject)
//                    .setIssuedAt(new Date(System.currentTimeMillis()))
//                    .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
//                    .signWith(SignatureAlgorithm.HS512, secret)
//                    .compact();
//        }
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        if (userDetails.isAccountNonExpired()){
            return username.equals(userDetails.getUsername());
        }
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}