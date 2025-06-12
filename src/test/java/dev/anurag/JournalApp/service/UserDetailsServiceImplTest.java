package dev.anurag.JournalApp.service;

import dev.anurag.JournalApp.entity.Users;
import dev.anurag.JournalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

public class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Disabled
    @Test
    void loadUserByUserNameTest(){
        when(userRepository.findByuserName(ArgumentMatchers.anyString())).thenReturn(Users.builder().userName("ram").password("fuihhvui").roles(new ArrayList<>()).build());
        UserDetails user = userDetailsService.loadUserByUsername("ram");
        Assertions.assertNotNull(user);
    }
}
