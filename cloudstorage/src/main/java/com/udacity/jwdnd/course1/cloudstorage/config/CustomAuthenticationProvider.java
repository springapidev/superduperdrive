package com.udacity.jwdnd.course1.cloudstorage.config;

import com.udacity.jwdnd.course1.cloudstorage.entity.UserRole;
import com.udacity.jwdnd.course1.cloudstorage.entity.Users;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private UsersMapper usersMapper;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();

        final List<UserRole> userRoles = getUserIfAuthenticated(username, password);

        return new UsernamePasswordAuthenticationToken(username, null, getGrantedAuthorities(userRoles));
    }

    private Collection<? extends GrantedAuthority> getGrantedAuthorities(final List<UserRole> userRoles) {
        return userRoles
                .stream()
                .map(role -> (GrantedAuthority) role::getRole)
                .collect(Collectors.toList());
    }

    private List<UserRole> getUserIfAuthenticated(final String username, final String password) {
        final Users entity = usersMapper.findByUserName(username);

        if (entity == null) {
            throw new UsernameNotFoundException("Authentication failed");
        }

        if (!entity.getPassword().equals(password)) {
            throw new UsernameNotFoundException("Authentication failed");
        }

        return null;//entity.getUserRoles();
    }

    @Override
    public boolean supports(final Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}