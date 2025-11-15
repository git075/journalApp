package dev.anurag.JournalApp.service;

import dev.anurag.JournalApp.entity.Users;
import dev.anurag.JournalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;



@Component
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByuserName(username);
        if(user!=null){
            UserDetails userDetails = User.builder().
                    username(user.getUserName()).
                    password(user.getPassword()).
                    roles(user.getRoles().toArray(new String[0])).
                    build();
            return userDetails;
        }
        return null;
    }
}































/*
We can access username with username without implementing userdetailsservice but spring security demands userdetails object and it does
not understand out user entity. Also we have to check if the user is blocked or not or account expired because it is possible that
it check the jwt token and it is not expired but user is blocked by the admin. So we do not completely rely on jwt token and we have to
make db calls also to check the user status.
* */