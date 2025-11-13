package dev.anurag.JournalApp.scheduler;

import dev.anurag.JournalApp.cache.AppCache;
import dev.anurag.JournalApp.entity.JournalEntity;
import dev.anurag.JournalApp.model.SentimentData;
import dev.anurag.JournalApp.entity.Users;
import dev.anurag.JournalApp.enums.Sentiments;
import dev.anurag.JournalApp.repository.UserRepositoryImpl;
import dev.anurag.JournalApp.service.EmailService;
import dev.anurag.JournalApp.service.JournalEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AppCache appCache;

    @Autowired
    private JournalEntityService journalEntityService;

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;


    @Scheduled(cron = "0 0 9 * * SUN") //cron=time that when will the function will run like in how much time will it repeat the function.
    public void fetchUsersAndSendSAMail(){
        List<Users> users = userRepository.getUsersForSA();
        for (Users user:users) {
            List<JournalEntity> journalEntities = user.getJournalEntities();
            List<Sentiments> collectedSentiment = journalEntities.stream()
                    .filter(x -> x.getDate().isAfter(LocalDateTime.now().minusDays(7)))
                    .map(x -> x.getSentiment())
                    .collect(Collectors.toList());
            HashMap<Sentiments, Integer> sentimentCount = new HashMap<>();
            for(Sentiments sentiment : collectedSentiment){
                if(sentiment!=null){
                    sentimentCount.put(sentiment, sentimentCount.getOrDefault(sentiment,0)+1);
                }
                Sentiments mostFrequentSentiment = null;
                int maxCount = 0;
                for(Map.Entry<Sentiments, Integer> entry :sentimentCount.entrySet()){
                    if(entry.getValue()>maxCount){
                        maxCount=entry.getValue();
                        mostFrequentSentiment=entry.getKey();
                    }
                }
                if(mostFrequentSentiment!=null){
                   // String sentiment = sentimentAnalysisService.getSentiment(entry);
                    SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for 7 days " + mostFrequentSentiment ).build();
                    try{
                        kafkaTemplate.send("weekly-sentiments" , sentimentData.getEmail(), sentimentData);
                    }catch (Exception e){
                        emailService.sendMail(sentimentData.getEmail(), "Sentiment for 7 days", sentimentData.getSentiment());
                    }

                   // emailService.sendMail(user.getEmail(), "Sentiment for 7 days" , mostFrequentSentiment.toString());

                }

            }

        }
    }


    /*
    @Scheduled(cron = "")
    public void clearAppCache(){
        appCache.init();
    } */



}
