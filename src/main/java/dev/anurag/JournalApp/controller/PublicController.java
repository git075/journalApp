package dev.anurag.JournalApp.controller;


import dev.anurag.JournalApp.entity.Users;
import dev.anurag.JournalApp.service.UserDetailsServiceImpl;
import dev.anurag.JournalApp.service.UserService;
import dev.anurag.JournalApp.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody Users user){
        userService.saveNewUser(user);
        return new ResponseEntity<>("New User Created" , HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users user){
        //I will write login attempt limitter and blocking user logic here later.
        try{
            Authentication authenticate = authenticationManager.authenticate
                    (new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch(Exception e){
            log.error("Error occured during the login process", e);
            return new ResponseEntity<>("Incorrect password or username", HttpStatus.BAD_REQUEST);
        }
    }
}
