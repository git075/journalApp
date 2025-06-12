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
