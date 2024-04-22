package xyz.toway.emarket.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.entity.UserTokenEntity;

@Repository
public interface UserTokenRepository extends ReactiveCrudRepository<UserTokenEntity, Integer> {
    Mono<UserTokenEntity> getByUserId(Integer userId);
}
