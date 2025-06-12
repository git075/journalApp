package dev.anurag.JournalApp.controller;

import dev.anurag.JournalApp.entity.Users;
import dev.anurag.JournalApp.apiResponse.WhetherApiResponse;
import dev.anurag.JournalApp.service.UserService;
import dev.anurag.JournalApp.service.WhetherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private WhetherService whetherService;




    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody Users user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Users userInDb = userService.findByUsername(userName);
        if(userInDb!=null){
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveNewUser(userInDb);
            return new ResponseEntity<>(userInDb, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUserByUsername(@RequestBody Users user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        userService.deleteByUserName(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<String> greeting(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        WhetherApiResponse response = whetherService.getWhetherDetails("Mumbai");
        String greeting = "";
        if(response!=null){
            greeting = " Whether feels like " + response.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi " + username + "how are you " + greeting, HttpStatus.OK);

    }


}
