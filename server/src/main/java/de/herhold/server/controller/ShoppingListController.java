package de.herhold.server.controller;

import de.herhold.server.model.Item;
import de.herhold.server.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * This Controller is for various stuff. For example get and test hashmaps for an ArrayList
 */
@Controller
public class ShoppingListController {

    private final ItemService itemService;

    public ShoppingListController(ItemService itemService) {
        this.itemService = itemService;
    }


    @GetMapping(value = "/items")
    public ResponseEntity<String> getAllItems() {
        Flux<Item> itemFlux = itemService.getList();
        List<Item> item = itemFlux.collectList().block();
        return new ResponseEntity<>(item.toString(), HttpStatus.OK);
    }

    @GetMapping(value = "/item/{name}")
    public ResponseEntity<Item> createItem(@PathVariable(name = "name") String name) {
        Item item = new Item();
        item.setName(name);
        Item createdItem = itemService.createItem(item);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    @GetMapping(value = "/items/hash")
    public ResponseEntity<String> getHashFromList() {
        int hash = itemService.getListHash();
        String response = String.format("Hash for elements is %s \n", hash);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
