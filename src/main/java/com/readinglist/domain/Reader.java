package com.readinglist.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Collection;

@Getter
@Setter
@Entity
public class Reader implements UserDetails {

    private static final long serialVersionUID=1L;

    @Id
    private String username;
    private String fullname;
    private String password;

    // UserDetails 메서드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // READER 권한 부여
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_READER"));
    }

    @Override
    public boolean isAccountNonExpired(){ // 계정이 만료되지 않음
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){ // 계정이 잠겨있지 않음
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 자격이 유효하다
        return true;
    }

    @Override
    public boolean isEnabled() { // 계정이 활성화되어 있다
        return true;
    }
}
