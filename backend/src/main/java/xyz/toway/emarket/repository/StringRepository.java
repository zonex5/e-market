package xyz.toway.emarket.repository;

import java.lang.Integer;
import java.util.Collection;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.entity.StringEntity;

@Repository
public interface StringRepository extends ReactiveCrudRepository<StringEntity, Integer> {
    Mono<StringEntity> getFirstByItemKeyAndLang(String key, String lang);

    Flux<StringEntity> getAllByItemKeyIn(Collection<String> keys);
}
