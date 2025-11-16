package dev.anurag.JournalApp.auth;

import dev.anurag.JournalApp.service.RedisLoginAttemptService;
import dev.anurag.JournalApp.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private RedisLoginAttemptService redisLoginAttemptService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final int MAX_ATTEMPTS = 5;





    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String rawPassword = authentication.getCredentials().toString();

        int attempts = redisLoginAttemptService.getAttempts(username);
        if(attempts>=MAX_ATTEMPTS){
            throw new LockedException("Account Blocked due to many attempts at once");
        }
        UserDetails user = userDetailsService.loadUserByUsername(username);

        if(!passwordEncoder.matches(rawPassword, user.getPassword())){
            redisLoginAttemptService.incrementAttempt(username);
            throw new BadCredentialsException("Wrong Password or Username");
        }

        redisLoginAttemptService.resetAttempts(username);

        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication){
        return authentication.equals(UsernamePasswordAuthenticationFilter.class);
    }

}
