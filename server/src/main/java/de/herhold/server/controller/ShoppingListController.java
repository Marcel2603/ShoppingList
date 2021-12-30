package de.herhold.server.controller;

import de.herhold.server.mapper.ItemMapper;
import de.herhold.server.service.ItemService;
import de.herhold.shopping_list.api.java_server.handler.ShoppingApi;
import de.herhold.shopping_list.api.java_server.model.Item;
import de.herhold.shopping_list.api.java_server.model.Response;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;


/**
 * This Controller is for various stuff. For example get and test hashmaps for an ArrayList
 */
@RestController
public class ShoppingListController implements ShoppingApi {

    private final ItemService itemService;

    public ShoppingListController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public Mono<ResponseEntity<Flux<Item>>> getItems(ServerWebExchange exchange) {
        return Mono.just(
                ResponseEntity.ok(
                        itemService.getList()
                                .map(ItemMapper.INSTANCE::mapItem)
                ));
    }

    @Override
    public Mono<ResponseEntity<Response>> getHash(ServerWebExchange exchange) {
        return itemService.getListHash()
                .map(hashValue -> new Response().value(String.valueOf(hashValue)))
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Item>> createItem(@ApiParam(value = "item to be created", required = true) @Valid @RequestBody Mono<Item> item, ServerWebExchange exchange) {
        return itemService.createItem(item.map(ItemMapper.INSTANCE::convertItem))
                .map(ItemMapper.INSTANCE::mapItem)
                .map(itemMono -> ResponseEntity.status(200).body(itemMono));
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteItem(Integer id, ServerWebExchange exchange) {
        return itemService.deleteItemWithId(id).map(obj -> ResponseEntity.noContent().build());
    }
}
