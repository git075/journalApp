package dev.anurag.JournalApp.repository;

import dev.anurag.JournalApp.entity.JournalEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Repository
public interface JournalRepository extends MongoRepository<JournalEntity, ObjectId> {
}
