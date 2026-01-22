package BTEC.ASM.project.modules.identity.security.userdetails;

import BTEC.ASM.project.modules.identity.entity.User;
import BTEC.ASM.project.modules.identity.entity.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private Long id;
    private String userCode;
    private String email;
    private String status;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(User user) {
        this.id = user.getId();
        this.userCode = user.getUserCode();
        this.email = user.getEmail();
        this.status = user.getStatus();

        this.authorities = mapRolesToAuthorities(user.getUserRoles());
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<UserRole> roles) {
        if (roles == null) return Set.of();

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().getCode()))
                .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return userCode;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // JWT nên trả null
    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !"LOCKED".equalsIgnoreCase(status);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return "ACTIVE".equalsIgnoreCase(status);
    }
}
