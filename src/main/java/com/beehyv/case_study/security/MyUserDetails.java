package com.beehyv.case_study.security;

import com.beehyv.case_study.entities.MyUserCredentials;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {

    private MyUserCredentials myUserCredentials;

    public MyUserCredentials getMyUserCredentials() {
        return myUserCredentials;
    }

    public void setMyUserCredentials(MyUserCredentials myUserCredentials) {
        this.myUserCredentials = myUserCredentials;
    }

    MyUserDetails(MyUserCredentials myUserCredentials) {
        this.myUserCredentials = myUserCredentials;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(myUserCredentials.getAuthorities().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return myUserCredentials.getPassword();
    }

    @Override
    public String getUsername() {
        return myUserCredentials.getEmail();
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
