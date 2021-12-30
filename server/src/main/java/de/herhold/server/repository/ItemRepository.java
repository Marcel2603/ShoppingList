package de.herhold.server.repository;

import de.herhold.server.model.Item;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ItemRepository extends ReactiveCrudRepository<Item, Integer> {

    Flux<Item> findAll();

    Mono<Item> save(Mono<Item> item);

}
