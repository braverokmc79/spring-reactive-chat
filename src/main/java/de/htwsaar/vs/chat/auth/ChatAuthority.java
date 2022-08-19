package de.htwsaar.vs.chat.auth;

import de.htwsaar.vs.chat.model.Chat;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**

* {@link SimpleGrantedAuthority}와 유사한 {@link GrantedAuthority} 구현.
 * <p>
 * 권한 문자열에는 {@code CHAT_%s_MOD} 패턴이 있습니다. 여기서 {@code %s}
 *는 {@link Chat} ID여야 합니다.
 * <p>
 * 중요: 권한을 가져오려면 인수가 없는 생성자가 필요합니다.
 * 데이터베이스는 패턴 문자열로 다시 래핑되지 않습니다!
 */
@NoArgsConstructor
@EqualsAndHashCode
public class ChatAuthority implements GrantedAuthority {

    private String authority;

    public ChatAuthority(String chatId) {
        this.authority = "CHAT_%s_MOD".formatted(chatId);
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public String toString() {
        return getAuthority();
    }
}
