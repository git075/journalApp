package dev.anurag.JournalApp;

import com.mongodb.client.MongoClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableScheduling  // used for scheduling the task which we did using crons in userScheduling class.
public class JournalAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalAppApplication.class, args);
	}
}























/*
✅ User Types the Domain: www.anuragblogs.com
✅ DNS Lookup: DNS converts www.anuragblogs.com → 203.0.113.0
✅ Request Sent to Hosting Server: The request reaches your AWS EC2 server.
✅ Spring Boot’s Embedded Server Handles It: Tomcat (port 8080) processes the request.
✅ Data Fetched from MongoDB Atlas (if needed): If data is required, the app queries MongoDB Atlas.
✅ Response Sent Back to the User.
*
* */
