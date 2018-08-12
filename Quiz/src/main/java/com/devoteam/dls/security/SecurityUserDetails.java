package com.devoteam.dls.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.devoteam.dls.domain.Employee;
import com.devoteam.dls.domain.Role;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by basakpie on 2017. 5. 11..
 */
public class SecurityUserDetails extends Employee implements UserDetails {

    private static final long serialVersionUID = 1L;

    public SecurityUserDetails(Employee user) {
        if(user==null) return;
        this.setEmployee_ID(user.getEmployee_ID());
        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());
        this.setCreated_date(user.getCreated_date());
        this.setLast_modified_date(user.getLast_modified_date());
        this.setRoles(user.getRoles());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for(Role role : super.getRoles()) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleType().name());
            authorities.add(authority);
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
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
