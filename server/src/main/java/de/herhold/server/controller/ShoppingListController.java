package de.herhold.server.controller;

import de.herhold.server.mapper.ItemMapper;
import de.herhold.server.model.Item;
import de.herhold.server.service.ItemService;
import de.herhold.shopping_list.api.java_server.handler.ShoppingApi;
import de.herhold.shopping_list.api.java_server.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.validation.Valid;
import java.time.Duration;


/**
 * This Controller is for various stuff. For example get and test hashmaps for an ArrayList
 */
@Controller
public class ShoppingListController implements ShoppingApi {
    private final ItemService itemService;
    private final ItemMapper itemMapper;

    public ShoppingListController(ItemService itemService, ItemMapper itemMapper) {
        this.itemService = itemService;
        this.itemMapper = itemMapper;
    }

    @Override
    public Mono<ResponseEntity<Flux<de.herhold.shopping_list.api.java_server.model.Item>>> getItems(ServerWebExchange exchange) {
        Flux<Item> itemFlux = itemService.getList();
        Flux<de.herhold.shopping_list.api.java_server.model.Item> mappedFlux = itemFlux.log().publishOn(Schedulers.newParallel("ItemMapper"))
                .map(itemMapper::mapItem);
        return Mono.just(new ResponseEntity<>(mappedFlux, HttpStatus.OK));
    }

    @Override
    public Mono<ResponseEntity<Response>> getHash(ServerWebExchange exchange) {
        int hash = itemService.getListHash();
        Response response = new Response();
        response.setValue(String.valueOf(hash));
        return Mono.just(new ResponseEntity<>(response, HttpStatus.OK));
    }

    @Override
    public Mono<ResponseEntity<de.herhold.shopping_list.api.java_server.model.Item>> createItem(@Valid Mono<de.herhold.shopping_list.api.java_server.model.Item> item, ServerWebExchange exchange) {
       /* Mono<Item> dbItem = item.log().map(itemMapper::convertItem);
        item.subscribe(  value -> System.out.println(value),
                error -> error.printStackTrace(),
                () -> System.out.println("completed without a value"));
        Item item2 = new Item();
        item2.setAmount("500g");
        item2.setName("TEST");
        dbItem.subscribe();
        dbItem = itemService.createItem(item2);
        System.out.println(item2);*/
        return Mono.just(new ResponseEntity<>(item.publishOn(Schedulers.immediate()).block((Duration.ofMillis(500))),HttpStatus.CREATED));
    }


    @PostMapping(value = "/item1")
    public Mono<ResponseEntity<Mono<Item>>> createItem(@RequestBody() de.herhold.shopping_list.api.java_server.model.Item item) {
        Mono<de.herhold.shopping_list.api.java_server.model.Item> itemMono = Mono.just(item);
        Mono<Item> mappedMono = itemMono.map(itemMapper::convertItem);

        System.out.println("CreaetdMono");
        /*Item mappedItem = itemMapper.convertItem(item);
        Mono<Item> createdItem = itemService.createItem(mappedItem);*/
        return Mono.just(new ResponseEntity<>(itemService.createItem(mappedMono), HttpStatus.CREATED));
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteItem(Integer id, ServerWebExchange exchange) {
        itemService.deleteItemWithId(id).log().subscribe();
        System.out.println("Deleted");
        return Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
}
