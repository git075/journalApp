package dev.anurag.JournalApp.service;

import dev.anurag.JournalApp.entity.Users;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest  //because while autowiring things here, null error will be shown because we know that spring inject beans during runtime but here the application is not started so how will spring injetc beans.
public class UserServiceTest {

    @Autowired
    private UserService userService;  //can be shown null if @springboot annotation is not there.



    @Disabled
    @ParameterizedTest
    @ValueSource(strings={
            "ram",
            "shyam",
            "name"
    })
    public void testFindByUserName(){
        assertEquals(4, 2+2);
        assertNotNull(userService.findByUsername("ram"));

    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "3,2,1",
            "4,3,5"
    }

    )
    public void test(int a, int b, int expected){
        assertEquals(expected, a+b);
    }


    @Disabled
    @ParameterizedTest
    @ArgumentsSource(UserArgumentProvider.class)
    public void test2(Users user){
        assertTrue(userService.saveNewUser(user));
    }



}
