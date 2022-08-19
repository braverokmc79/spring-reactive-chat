package de.htwsaar.vs.chat.auth;

import de.htwsaar.vs.chat.model.Chat;
import de.htwsaar.vs.chat.model.Message;
import de.htwsaar.vs.chat.model.User;
import de.htwsaar.vs.chat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
* Spring Security에서 사용하는 Web Security Expression을 포함하는 클래스
 * (예: {@link PreAuthorize} 및 {@link PostAuthorize}와 같은 주석)
 *
 */
@Component
@RequiredArgsConstructor
public class WebSecurity {

    private final UserRepository userRepository;

    public boolean hasChatAuthority(Authentication authentication, String chatId) {
        return authentication.getAuthorities().contains(new ChatAuthority(chatId));
    }

    public boolean addChatAuthority(Authentication authentication, Chat chat) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.user();

        user.addAuthority(new ChatAuthority(chat.getId()));
        userRepository.save(user).subscribe();

        return true;
    }

    public boolean removeChatAuthority(Authentication authentication, String chatId) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.user();

        if (user.removeAuthority(new ChatAuthority(chatId))) {
            userRepository.save(user).subscribe();
        }

        return true;
    }

    public boolean isMessageSender(Authentication authentication, Message message) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.user();

        return user.getId().equals(message.getSender().getId());
    }
}
