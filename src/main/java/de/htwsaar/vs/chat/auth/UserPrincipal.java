package de.htwsaar.vs.chat.auth;

import de.htwsaar.vs.chat.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
* 인증을 위해 Spring Security에서 사용하는 {@link UserDetails} 구현
 * 및 승인.
 *
 * @author Arthur Kelsch
 * @see User
 */
public record UserPrincipal(User user) implements UserDetails {

    public String getId() {
        return user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SortedSet<GrantedAuthority> authorities = new TreeSet<>(Comparator.comparing(GrantedAuthority::getAuthority));
        authorities.addAll(user.getRoles());
        authorities.addAll(user.getAuthorities());

        return Collections.unmodifiableSet(authorities);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
