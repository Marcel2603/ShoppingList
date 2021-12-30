package de.herhold.server.service;

import de.herhold.server.model.Item;
import de.herhold.server.repository.ItemRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Mono<Integer> getListHash() {
        return itemRepository.findAll().collectList().map(List::hashCode);
    }

    public Mono<Item> createItem(Mono<Item> itemMono) {
        return itemMono.flatMap(itemRepository::save);
    }

    public Flux<Item> getList() {
        return itemRepository.findAll();
    }

    public Mono<Void> deleteItemWithId(Integer id) {
        return itemRepository.deleteById(id);
    }
}
