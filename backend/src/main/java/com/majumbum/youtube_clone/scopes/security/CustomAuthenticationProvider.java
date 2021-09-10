package com.majumbum.youtube_clone.scopes.security;

import com.majumbum.youtube_clone.scopes.user.entities.User;
import com.majumbum.youtube_clone.scopes.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    public UserService userService;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        final String email = authentication.getName().toLowerCase();
        final String password = authentication.getCredentials().toString();

        final Optional<User> user = userService.findUserByEmail(email);
        if (!user.isPresent())
            throw new UsernameNotFoundException("Email '" + email + "' not found.");

        if (!passwordEncoder.matches(password, user.get().getHashedPassword()))
            throw new BadCredentialsException("Password wrong.");

        final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.get(), user.get().getHashedPassword(),
                user.get().getAuthorities());

        return auth;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
