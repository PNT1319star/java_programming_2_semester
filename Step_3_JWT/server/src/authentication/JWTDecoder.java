package authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.util.Date;


public class JWTDecoder {
    private static final String SECRET_KEY = "aU!9D#kL@zP$1Fg";

    private static Claims decodeJWT(String jwtToken) {
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes())
                .parseClaimsJws(jwtToken);
        return claimsJws.getBody();
    }

    public static String checkJWT(String jwtToken) {
        Claims claims = decodeJWT(jwtToken);
        if (claims != null) {
            String username = claims.get("username", String.class);
            Date expirationDate = claims.getExpiration();
            if (expirationDate.before(new Date())) {
                return "Token has expired!";
            }
            return username;
        }
        return "Invalid JWT token";
    }
}
