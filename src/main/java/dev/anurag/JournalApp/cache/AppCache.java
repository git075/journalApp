package dev.anurag.JournalApp.cache;
import dev.anurag.JournalApp.entity.ConfigJournalEntity;
import dev.anurag.JournalApp.repository.ConfigJournalRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class AppCache {

    public enum keys{
        weather_api
    }

    @Autowired
    private ConfigJournalRepository configJournalRepository;

    public Map<String, String> APP_CACHE;

    @PostConstruct //this method will invoke when the bean of the class or object of the class is created. Can be used when we want the class to do sth as soon as its bean is created.
    public void init(){
        APP_CACHE = new HashMap<>(); //There is always a reason behind everything.
        List<ConfigJournalEntity> configJournalEntities= configJournalRepository.findAll();
        for(ConfigJournalEntity configJournalEntity : configJournalEntities){
            APP_CACHE.put(configJournalEntity.getKey(), configJournalEntity.getValue());
        }
    }
}
