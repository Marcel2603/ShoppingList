package de.herhold.server.cucumber;

import de.herhold.shopping_list.api.java_server.model.Item;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.List;

public class ShoppingApiStepdefs {
    @Autowired
    WebTestClient webTestClient;

    @Autowired
    private FixturesStepdefs fixturesStepdefs;

    private WebTestClient.ResponseSpec responseSpec;

    private Item item;
    @Given("an Item {string} {string}")
    public void anItem(String name, String amount) {
        Item item = new Item();
        item.setName(name);
        item.setAmount(amount);
        this.item = item;
    }

    @When("the item gets created")
    public void theItemGetsCreated() {
        responseSpec = webTestClient.post().uri("/item").body(BodyInserters.fromValue(this.item)).exchange();
    }

    @When("getting hash")
    public void gettingHash() {
        responseSpec = webTestClient.get().uri("/items/hash").exchange();
    }

    @Then("the server returns {string}")
    public void theServerReturns(String code) {
        responseSpec.expectStatus().isEqualTo(Integer.parseInt(code));
    }

    @Then("the hash is equal to {string}")
    public void theHashIsEqualTo(String hashCode) {
        responseSpec.expectBody().jsonPath("$.value").isEqualTo(hashCode);
    }

    @Then("the hash is equal to the item as list")
    public void theHashIsEqualToTheItemAsList() {
        de.herhold.server.model.Item item = fixturesStepdefs.getItem();
        long expectedHash = List.of(item).hashCode();
        responseSpec.expectBody().jsonPath("$.value").isEqualTo(expectedHash);
    }
}
