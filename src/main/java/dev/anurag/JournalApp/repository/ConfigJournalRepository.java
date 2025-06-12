package dev.anurag.JournalApp.repository;


import dev.anurag.JournalApp.entity.ConfigJournalEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface ConfigJournalRepository extends MongoRepository<ConfigJournalEntity, ObjectId> {
}
