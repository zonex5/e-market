package xyz.toway.emarket.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.entity.UserDetailsEntity;

@Repository
public interface UserDetailsRepository extends ReactiveCrudRepository<UserDetailsEntity, Integer> {
    Mono<UserDetailsEntity> getByUsername(String username);
}
