package de.htwsaar.vs.chat.util;

import de.htwsaar.vs.chat.auth.UserPrincipal;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

/**
* Spring Security(인증 등)에 메소드를 제공하는 유틸리티 클래스.
 *
 */
@UtilityClass
public class SecurityUtils {

	/**
     * {@link UserPrincipal} 개체를 가져옵니다(맞춤형 {@link UserDetails}
     * 구현) 현재 Spring Security 인증에서.
     * <p>
     * 중요: Spring은 컨텍스트 또는 다른 방법으로 Mono를 구독해야 합니다.
     * 결과는 비어 있습니다.
     *
     * @return a {@link Mono}의 {@link UserPrincipal}
     */
    public static Mono<UserPrincipal> getPrincipal() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .cast(UserPrincipal.class);
    }
}
