package dev.anurag.JournalApp.service;
import dev.anurag.JournalApp.cache.AppCache;
import dev.anurag.JournalApp.constants.Placeholders;
import dev.anurag.JournalApp.entity.Users;
import dev.anurag.JournalApp.apiResponse.WhetherApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WhetherService {
    @Value("${Weather.api.key}")
    private String API_KEY;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;



    public WhetherApiResponse getWhetherDetails(String city){
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Key", "value");
//        Users user = Users.builder().userName("anurag").password("duhduhd").build();
//        HttpEntity<Users> httpEntity = new HttpEntity<>(user);  //this can be send in place of requestEntity if the external API is a Post call and demand some entity like username, pass or anything.
//        HttpEntity<Users> httpEntity1 = new HttpEntity<>(user, httpHeaders); //hence anything which the api expects can be send using HttpEntity.

        WhetherApiResponse cachedResponse = redisService.get("Whether of_ " + city, WhetherApiResponse.class);
        if(cachedResponse!=null){
            return cachedResponse;
        }else{
            String finalUrl = appCache.APP_CACHE.get(AppCache.keys.weather_api.toString()).replace(Placeholders.API_KEY, API_KEY ).replace(Placeholders.CITY , city);
            ResponseEntity<WhetherApiResponse> response = restTemplate.exchange(finalUrl, HttpMethod.GET, null, WhetherApiResponse.class);
            WhetherApiResponse body = response.getBody();
            if(body!=null){
                redisService.set("Whether of " + city,body, 300L );
            }
            return body;
        }
    }
}
