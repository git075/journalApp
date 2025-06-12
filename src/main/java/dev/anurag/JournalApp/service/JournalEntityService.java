package dev.anurag.JournalApp.service;


import dev.anurag.JournalApp.entity.JournalEntity;
import dev.anurag.JournalApp.entity.Users;
import dev.anurag.JournalApp.repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntityService {

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private UserService userService;




    public List<JournalEntity> findAll(){
        return journalRepository.findAll();
    }


    @Transactional
    public void addEntry(JournalEntity entity, String userName){
        try {
            Users user = userService.findByUsername(userName);
            entity.setDate(LocalDateTime.now());
            JournalEntity saved = journalRepository.save(entity);
            user.getJournalEntities().add(saved);
            userService.saveUser(user);
        }catch (Exception e){
            throw new RuntimeException("Something went wrong");
        }


    }

    public void addEntry(JournalEntity entity){
        journalRepository.save(entity);

    }



    public Optional<JournalEntity> getById(ObjectId id){
        return journalRepository.findById(id);
    }


    @Transactional
    public boolean deleteById(ObjectId id , String userName){
        boolean removed = false;
        Users user = userService.findByUsername(userName);
        removed = user.getJournalEntities().removeIf(x -> x.getId().equals(id));
        if(removed){
            userService.saveUser(user);
            journalRepository.deleteById(id);
        }
        return removed;
    }

}
