package com.sun.repository;

import com.sun.entity.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Flux<User> ageBetween(int start, int end);

    @Query("{age:{'$gte':'20','$lte':'30'}}")
    Flux<User> findOld();
}
