package de.htwsaar.vs.chat.error;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.core.codec.DecodingException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import javax.validation.ConstraintViolationException;
import java.util.Map;
import java.util.Optional;

/**
* 응답 HTTP 상태 코드를 변경하기 위해 {@link DefaultErrorAttributes}를 확장합니다.
 * 잘 알려진 예외의 경우.
 *
 * @author Julian Quint
 */
@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(request, options);
        Optional<HttpStatus> errorStatus = determineHttpStatus(getError(request));

        errorStatus.ifPresent(httpStatus -> {
            errorAttributes.replace("status", httpStatus.value());
            errorAttributes.replace("error", httpStatus.getReasonPhrase());
        });

        return errorAttributes;
    }

    private Optional<HttpStatus> determineHttpStatus(Throwable error) {
        if (error instanceof DecodingException
                || error instanceof ConstraintViolationException
                || error instanceof JWTVerificationException) {
            return Optional.of(HttpStatus.BAD_REQUEST);
        }

        if (error instanceof DuplicateKeyException) {
            return Optional.of(HttpStatus.CONFLICT);
        }

        return Optional.empty();
    }
}
