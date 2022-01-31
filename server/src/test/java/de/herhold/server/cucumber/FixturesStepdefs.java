package de.herhold.server.cucumber;

import de.herhold.server.model.Item;
import de.herhold.server.repository.ItemRepository;
import io.cucumber.java.en.Given;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FixturesStepdefs {
    @Autowired
    private ItemRepository itemRepository;

    @Getter
    private Item item;

    @Given("an Item in the database")
    public void anItemInTheDatabase() {
        Item item = new Item();
        item.setName("SomeTestName");
        item.setAmount("500g");
        itemRepository.save(item).doOnSuccess((savedItem) -> this.item = savedItem).subscribe();
    }

    @Given("no Item is in the database")
    public void noItemIsInTheDatabase() {
        Mono<Long> count = itemRepository.count();
        StepVerifier.create(count).expectNext(0L).verifyComplete();
    }
}
