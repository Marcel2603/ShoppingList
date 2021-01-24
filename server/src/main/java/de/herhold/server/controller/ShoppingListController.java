package de.herhold.server.controller;

import de.herhold.server.mapper.ItemMapper;
import de.herhold.server.model.Item;
import de.herhold.server.service.ItemService;
import de.herhold.shopping_list.api.java_server.handler.ItemsApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;


/**
 * This Controller is for various stuff. For example get and test hashmaps for an ArrayList
 */
@Controller
public class ShoppingListController implements ItemsApi {
    private final ItemService itemService;
    private final ItemMapper itemMapper;

    public ShoppingListController(ItemService itemService, ItemMapper itemMapper) {
        this.itemService = itemService;
        this.itemMapper = itemMapper;
    }


    @Override
    public Mono<ResponseEntity<Flux<de.herhold.shopping_list.api.java_server.model.Item>>> getItems(ServerWebExchange exchange) {
        System.out.println("Test");
        Item item = new Item();
        item.setName("test");
        item.setAmount("500g");
        itemService.createItem(item);
        Flux<Item> itemFlux = itemService.getList();
        Flux<de.herhold.shopping_list.api.java_server.model.Item> mappedFlux = itemFlux.publishOn(Schedulers.newParallel("ItemMapper"))
                .map(itemMapper::mapItem);
        return Mono.just(new ResponseEntity<>(mappedFlux,HttpStatus.OK));
    }

    @GetMapping(value = "/item/{name}")
    public Mono<ResponseEntity<Mono<Item>>> createItem(@PathVariable(name = "name") String name) {
        Item item = new Item();
        item.setName(name);
        Mono<Item> createdItem = itemService.createItem(item);
        return Mono.just(new ResponseEntity<>(createdItem, HttpStatus.CREATED));
    }

    @GetMapping(value = "/items/hash")
    public Mono<String> getHashFromList() {
        int hash = itemService.getListHash();
        String response = String.format("Hash for elements is %s \n", hash);
        return Mono.just(response);
    }
}
