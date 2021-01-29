package de.herhold.server.service;

import de.herhold.server.model.Item;
import de.herhold.server.repository.ItemRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

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
        return item.map(this::saveItemToDB);
    }

    private Item saveItemToDB(Item item) {
        Mono<Item> savedMono = itemRepository.save(item);
        savedMono.subscribe(item1 -> item.setId(item1.getId()));
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
