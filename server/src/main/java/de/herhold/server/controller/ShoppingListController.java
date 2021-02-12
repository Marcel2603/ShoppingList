package de.herhold.server.controller;

import de.herhold.server.mapper.ItemMapper;
import de.herhold.server.service.ItemService;
import de.herhold.shopping_list.api.java_server.handler.ShoppingApi;
import de.herhold.shopping_list.api.java_server.model.Item;
import de.herhold.shopping_list.api.java_server.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.validation.Valid;


/**
 * This Controller is for various stuff. For example get and test hashmaps for an ArrayList
 */
@RestController
public class ShoppingListController implements ShoppingApi {

    private final ItemService itemService;
    private final ItemMapper itemMapper;

    public ShoppingListController(ItemService itemService, ItemMapper itemMapper) {
        this.itemService = itemService;
        this.itemMapper = itemMapper;
    }

    @Override
    public Flux<Item> getItems(ServerWebExchange exchange) {
        Flux<de.herhold.server.model.Item> itemFlux = itemService.getList();
        Flux<de.herhold.shopping_list.api.java_server.model.Item> mappedFlux = itemFlux.log().publishOn(Schedulers.newParallel("ItemMapper"))
                .map(itemMapper::mapItem);
        return mappedFlux;
    }

    @Override
    public Mono<Response> getHash(ServerWebExchange exchange) {
        int hash = itemService.getListHash();
        Response response = new Response();
        response.setValue(String.valueOf(hash));
        return Mono.just(response);
    }

    @Override
    public Mono<Item> createItem(@Valid @RequestBody Mono<Item> item, ServerWebExchange exchange) {
        return itemService.createItem(item.map(itemMapper::convertItem)).map(itemMapper::mapItem);
    }

    @PostMapping(value = "/test")
    public Mono<Item> test(@RequestBody Mono<Item> item, ServerWebExchange exchange) {
        return itemService.createItem(item.map(itemMapper::convertItem)).map(itemMapper::mapItem);
    }

    @Override
    public Mono<Void> deleteItem(Integer id, ServerWebExchange exchange) {
        itemService.deleteItemWithId(id).log().subscribe();
        System.out.println("Deleted");
        return Mono.empty();
    }
}
