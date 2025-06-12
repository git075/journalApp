package dev.anurag.JournalApp.repository;

import dev.anurag.JournalApp.entity.Users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<Users , ObjectId> {
    Users findByuserName(String userName);  //the implementation of these methods will be injected in runtime. i.e Query Method DSL. That is this will be converted to query during runtime will the help of Query method DSL by spring boot.

    void deleteByUserName(String userName);

}


















/*

Where Are the Unimplemented Methods Defined?
The logic for methods like .save(), .findById(), .delete() (from MongoRepository) is provided by Spring Data MongoDB via proxy classes.

Here's how it works:

Spring automatically generates the implementation for repository interfaces using Dynamic Proxy or CGlib Proxies.
The method logic is handled internally by Spring Data MongoDB based on the method's signature.
For example, findByUserName() is interpreted by Spring Data as:
find → Fetch data.
ByUserName → The criteria for the query.
Spring uses naming conventions to generate the appropriate MongoDB query in the background.

4. Why Don’t Methods Require @Abstract?
In Java:

Interfaces are inherently abstract — all methods inside an interface are implicitly public and abstract.
Adding @Abstract or abstract is redundant and not required in interfaces.

 extends is used because MongoRepository is also an interface.
 MongoRepository’s methods are implemented dynamically by Spring Data MongoDB using proxy classes.
 Interface methods are implicitly abstract, so @Abstract or abstract is unnecessary.
 Custom methods like findByUserName() rely on Spring Data’s query creation using naming conventions.
*
*
* */
