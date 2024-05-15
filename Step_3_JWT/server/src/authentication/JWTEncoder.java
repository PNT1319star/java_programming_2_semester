package authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import utilities.Roles;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JWTEncoder {
    private static final String SECRET_KEY = "aU!9D#kL@zP$1Fg";

    public static String encodeJWT(String username, Roles role, List<String> functionList)  {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("role", role);
        claims.put("function", functionList);

        long nowMillis = System.currentTimeMillis();
        long expirationMillis = nowMillis + 3600000;
        Date expirationDate = new Date(expirationMillis);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(nowMillis))
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

}