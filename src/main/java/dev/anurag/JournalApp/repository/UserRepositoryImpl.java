package dev.anurag.JournalApp.repository;
import dev.anurag.JournalApp.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class UserRepositoryImpl {


    @Autowired
    private MongoTemplate mongoTemplate;
    public List<Users> getUsersForSA(){
        Query query = new Query();
        Criteria criteria = new Criteria();
        //Criteria.where("email").regex("/[a-z0-9\\._%+!$&*=^|~#%'`?{}/\\-]+@([a-z0-9\\-]+\\.){1,}([a-z]{2,16})/");
        criteria.andOperator(Criteria.where("email").exists(true), Criteria.where("sentimentAnalysis").is(true),
                Criteria.where("email").ne(null).ne(""));
        return mongoTemplate.find(query, Users.class);
    }
}
