package dev.anurag.JournalApp.controller;
import dev.anurag.JournalApp.cache.AppCache;
import dev.anurag.JournalApp.entity.Users;
import dev.anurag.JournalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;

    @RequestMapping("/all-users")
    public ResponseEntity<List<Users>> getAllUsers(){
        List<Users> allUsers = userService.findAll();
        if(!allUsers.isEmpty() && allUsers!=null){
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @RequestMapping("/create-admin-user")
    public void createAdmin(Users user){
        userService.saveAdminUser(user);
    }


    @GetMapping("/clear-app-cache")
    public void clearAppCache(){
        appCache.init();
    }

}
