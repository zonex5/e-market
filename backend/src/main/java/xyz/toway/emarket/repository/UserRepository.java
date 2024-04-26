package xyz.toway.emarket.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.entity.UserEntity;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, Integer> {
  Flux<UserEntity> getAllByActive(boolean active);

  Mono<Boolean> existsByUsername(String username);
}
