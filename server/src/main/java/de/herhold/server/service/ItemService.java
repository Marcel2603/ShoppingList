package de.herhold.server.service;

import de.herhold.server.model.Item;
import de.herhold.server.repository.ItemRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public int getListHash() {
        Flux<Item> itemFlux = itemRepository.findAll();
        return Objects.requireNonNull(itemFlux.collectList().block()).hashCode();
    }

    public Mono<Item> createItem(Item item) {
        return itemRepository.save(item);
    }

    public Flux<Item> getList() {
        return itemRepository.findAll();
    }
}
