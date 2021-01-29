package de.herhold.server.controller;

import de.herhold.server.mapper.ItemMapper;
import de.herhold.server.model.Item;
import de.herhold.server.service.ItemService;
import de.herhold.shopping_list.api.java_server.handler.ShoppingApi;
import de.herhold.shopping_list.api.java_server.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.validation.Valid;


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
        item.log().subscribe(System.out::println);
        return Mono.just(new ResponseEntity<>(HttpStatus.CREATED));
    }

    /**
     * Leider ist das item immer null. Wenn ich @ModelAttribute wähle dann sind die attribute im Item null. Das Objekt an
     * sich existiert dann aber.
     * @param item
     * @param exchange
     * @return
     */
    @PostMapping(value = "/item1")
    public Mono<ResponseEntity<de.herhold.shopping_list.api.java_server.model.Item>> testcreateItem(@ModelAttribute Mono<de.herhold.shopping_list.api.java_server.model.Item> item, ServerWebExchange exchange) {
        System.out.println("Input");
        item.log().subscribe(System.out::println);
        System.out.println("InputOut");
        de.herhold.shopping_list.api.java_server.model.Item test = new de.herhold.shopping_list.api.java_server.model.Item();
        test.setName("name");
        test.setAmount("amount");
        Mono<de.herhold.shopping_list.api.java_server.model.Item> testMono = Mono.just(test);
        testMono.subscribe(System.out::println);
        Mono<Item> mappedMono = testMono.map(itemMapper::convertItem);

        System.out.println("CreaetdMono");
        itemService.createItem(mappedMono);
        return Mono.just(new ResponseEntity<>(HttpStatus.CREATED));
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteItem(Integer id, ServerWebExchange exchange) {
        itemService.deleteItemWithId(id).log().subscribe();
        System.out.println("Deleted");
        return Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
}
