package de.htwsaar.vs.chat.auth.jwt;

import de.htwsaar.vs.chat.util.JwtUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
* Authorization HTTP 헤더의 토큰을 {@link 인증}으로 변환
 * 물체. 여기에는 토큰의 서명 확인이 포함됩니다.
 * <p>
 * Authorization 헤더가 비어 있으면 쿼리 매개변수에서
 * 서버 전송 이벤트로서의 토큰은 사용자 정의 HTTP 헤더 설정을 지원하지 않습니다.
 * 이를 위해서는 {@code text/event-stream} Accept 헤더를 설정해야 합니다.
 *
 */
public class JwtAuthorizationConverter implements ServerAuthenticationConverter {

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();

        Mono<String> sseToken = Mono.justOrEmpty(request.getQueryParams().getFirst("token"))
                .filter(token -> request.getHeaders().getAccept().contains(MediaType.TEXT_EVENT_STREAM));

        return Mono.justOrEmpty(request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .switchIfEmpty(sseToken)
                .mapNotNull(JwtUtils::unwrapBearerToken)
                .map(JwtUtils::verifyToken)
                .map(JwtUtils::getName)
                .map(username -> new UsernamePasswordAuthenticationToken(username, null));
    }
}
