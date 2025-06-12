
package dev.anurag.JournalApp.controller;


import dev.anurag.JournalApp.entity.JournalEntity;
import dev.anurag.JournalApp.entity.Users;
import dev.anurag.JournalApp.service.JournalEntityService;
import dev.anurag.JournalApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
@Slf4j
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntityService journalEntityService;

    @Autowired
    private UserService userService;

    @GetMapping("/getEntry")
    public ResponseEntity<List<JournalEntity>> getAllEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Users user = userService.findByUsername(userName);
        List<JournalEntity> all = user.getJournalEntities();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/createEntry")
    public ResponseEntity<JournalEntity> createEntryOfUser(@RequestBody JournalEntity myEntry){
        try{
           // String authentication = SecurityContextHolder.getContext().getAuthentication().getName();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalEntityService.addEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }catch (Exception e){
            log.error("Something went wrong :" , e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myid}")
    public ResponseEntity<JournalEntity> getById(@PathVariable ObjectId myid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String UserName = authentication.getName();
        Users user = userService.findByUsername(UserName);
        List<JournalEntity> collect = user.getJournalEntities().stream().filter(x -> x.getId().equals(myid)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<JournalEntity> entity= journalEntityService.getById(myid);
            if(entity.isPresent()){
                return new ResponseEntity<>(entity.get(), HttpStatus.OK);
            }
        }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myid}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId myid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users user = userService.findByUsername(username);
        boolean removed = journalEntityService.deleteById(myid , username);
        if(removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


   @PutMapping("/update/{myid}")
    public ResponseEntity<?> updateById(@PathVariable ObjectId myid, @RequestBody JournalEntity newEntity){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users user = userService.findByUsername(username);
        List<JournalEntity> collect = user.getJournalEntities().stream().filter(x -> x.getId().equals(myid)).collect(Collectors.toList());

        if(!collect.isEmpty()){
            JournalEntity old = journalEntityService.getById(myid).orElse(null);
            if(old!=null){
                old.setTitle(newEntity.getTitle()!=null && !newEntity.getTitle().equals("") ? newEntity.getTitle() : old.getTitle());
                old.setContent(newEntity.getContent()!=null && !newEntity.getContent().equals("") ? newEntity.getContent() : old.getContent());
                journalEntityService.addEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
        }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
