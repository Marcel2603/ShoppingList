package de.herhold.server.service;

import de.herhold.server.model.Item;
import de.herhold.server.repository.ItemRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public int getListHash() {
        Flux<Item> itemFlux = itemRepository.findAll();
        List<Item> itemList = new ArrayList<>();
        itemFlux.log()
                .subscribe(itemList::add);
        return itemList.hashCode();
    }

    public  Mono<Item> createItem(Mono<Item> item) {
        item.log().map(this::test);
        return item;
    }

    private Item test(Item item) {
        item.setId(8);
        return item;
    }

    public Flux<Item> getList() {
        return itemRepository.findAll();
    }

    public Mono<Void> deleteItemWithId(Integer id) {
        System.out.println("deleting");
        return itemRepository.deleteById(id);
    }
}
