package dev.anurag.JournalApp.service;

import dev.anurag.JournalApp.entity.Users;
import dev.anurag.JournalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

   // private static final Logger logger = LoggerFactory.getLogger(UserService.class);  //Use annotation slf4j instead.


    public boolean saveNewUser(Users user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        }catch (Exception e){
            log.error("Error occured for {}", user.getUserName(), e);
            return false;
        }
    }
    public void saveUser(Users user){
        userRepository.save(user);
    }

    public void saveAdminUser(Users user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(user);
    }

    public List<Users> findAll(){
        return userRepository.findAll();
    }

    public Optional<Users> findById(ObjectId id){
        return userRepository.findById(id);
    }

    public String deleteById(ObjectId id){
        userRepository.deleteById(id);
        return "User Deleted";
    }
    public void deleteByUserName(String UserName){
        userRepository.deleteByUserName(UserName);
    }

    public Users findByUsername(String userName){
        return userRepository.findByuserName(userName);
    }






}
