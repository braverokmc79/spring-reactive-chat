package de.htwsaar.vs.chat.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import de.htwsaar.vs.chat.auth.UserPrincipal;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;

import java.util.Date;

/**
 * JSON 웹 토큰으로 작업하기 위한 메서드를 제공하는 유틸리티 클래스입니다.
 *
 */
@UtilityClass
public final class JwtUtils {

    private static final long EXPIRES_IN = 24 * 60 * 60 * 1000L;

    private static final String SECRET = "avtQCXgvuLGn93dB3Mm8UXL9yLNqUXDM";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);

    /**
     * 다음 클레임으로 새 JWT를 만듭니다.
     * <ol>
     * <li>sub = user id</li>
     * <li>name = user name</li>
     * <li>exp = 24h from now</li>
     * </ol>
     * <p>
     * JWT는 HMAC-SHA256(HS256)을 사용하여 서명됩니다.
     *
     * @param 인증 현재 인증된 사용자
     * @return은 위에서 설명한 페이로드로 JWT를 반환합니다.
     */
    public static String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return JWT.create()
                .withSubject(userPrincipal.getId())
                .withClaim("name", userPrincipal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRES_IN))
                .sign(ALGORITHM);
    }

    public static String unwrapBearerToken(String bearerToken) {
        String bearerPrefix = "bearer ";
        if (bearerToken.toLowerCase().startsWith(bearerPrefix)) {
            return bearerToken.substring(bearerPrefix.length());
        }

        return null;
    }

    /**
     * 주어진 JWT의 서명을 확인합니다.
     *
     * @param 토큰 검증할 JWT
     * @return 확인 및 디코딩된 JWT
     */
    public static DecodedJWT verifyToken(String token) {
        return JWT.require(ALGORITHM)
                .withClaimPresence("name")
                .build()
                .verify(token);
    }

    /**
     * 주어진 디코딩된 JWT에서 "name"이라는 클레임을 가져옵니다.
     *
     * @param jwt 디코딩된 JWT
     * @return "이름" 클레임
     */
    public static String getName(DecodedJWT jwt) {
        return jwt.getClaim("name").asString();
    }
}
