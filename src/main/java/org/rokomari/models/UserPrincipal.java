package org.rokomari.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Abdullah Al Amin on 7/26/2018.
 */
@AllArgsConstructor
public class UserPrincipal implements UserDetails {

    @Getter@Setter
    private int id;

    @Getter@Setter
    private String firstName;

    @Getter@Setter
    private String lastName;

    @Getter@Setter
    private String email;

    @Getter@Setter
    private String username;

    @Getter@Setter
    private String mobile;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal create(User user){
        List<GrantedAuthority> authorities = user
                                            .getRoles()
                                            .stream()
                                            .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                                            .collect(Collectors.toList());
        return new UserPrincipal(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUsername(),
                user.getMobile(),
                user.getPassword(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPrincipal that = (UserPrincipal) o;

        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }
}
