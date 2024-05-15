package authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.util.Date;
import java.util.List;


public class JWTDecoder {
    private static final String SECRET_KEY = "aU!9D#kL@zP$1Fg";

    private static Claims decodeJWT(String jwtToken) {
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes())
                .parseClaimsJws(jwtToken);
        return claimsJws.getBody();
    }

    public static boolean checkJWT(String jwtToken) {
        Claims claims = decodeJWT(jwtToken);
        if (claims != null) {
            Date expirationDate = claims.getExpiration();
            if (expirationDate.before(new Date())) return false;
            return true;
        } else return false;
    }

    public static String getUsername(String jwtToken) {
        Claims claims = decodeJWT(jwtToken);
        return claims.get("username", String.class);
    }
    public static List<String> getFunctionList(String jwtToken) {
        Claims claims = decodeJWT(jwtToken);
        return claims.get("function", List.class);
    }
}
