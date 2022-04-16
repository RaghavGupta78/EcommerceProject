package Raghav.EcommerceProject.security.helper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private String SECRET_KEY = "RaghavSecretPassword123456789RaghavSecretPassword123456789RaghavSecretPassword123456789";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 2*30))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Date getExpirationFromJwt(String jwtToken) {
        return getClaimsFromJwt(jwtToken).getExpiration();

    }



    private Claims getClaimsFromJwt(String jwtToken) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(jwtToken)
                .getBody();
    }

//    public String setExpiration(Date date){
//        Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(date)
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
//
//        return "success";
//    }

    public String setExpirationabc(String jwtToken,Date date){

        Claims claims = getClaimsFromJwt(jwtToken);

        System.out.println(claims);

        claims.setExpiration(date);
        System.out.println(date);

        Jwts.builder().setClaims(claims).setExpiration(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();

       // Jwts.claims(claims).setExpiration(new Date(System.currentTimeMillis()));



        System.out.println(claims);

        return "success";
    }






}
