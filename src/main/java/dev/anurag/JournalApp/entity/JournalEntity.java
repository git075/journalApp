package dev.anurag.JournalApp.entity;


import com.fasterxml.jackson.annotation.JsonTypeId;
import dev.anurag.JournalApp.enums.Sentiments;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;
import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "journalEntity")
@Data
@NoArgsConstructor  //required during deserialization i.e json to pojo.
public class JournalEntity {

    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;
    private Sentiments sentiment;
}
