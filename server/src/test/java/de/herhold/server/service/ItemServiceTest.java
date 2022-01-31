package de.herhold.server.service;

import de.herhold.server.model.Item;
import de.herhold.server.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    ItemRepository itemRepository;

    @InjectMocks
    ItemService itemService;

    @Test
    void getListHashTest() {
        List<Item> returnedList = List.of(new Item(), new Item());

        doReturn(Flux.fromIterable(returnedList)).when(itemRepository).findAll();

        Mono<Integer> integerMono = assertDoesNotThrow(() -> itemService.getListHash());
        StepVerifier.create(integerMono).expectNext(returnedList.hashCode()).verifyComplete();
    }

    @Test
    void getListTest() {
        List<Item> returnedList = List.of(new Item(), new Item());

        doReturn(Flux.fromIterable(returnedList)).when(itemRepository).findAll();

        Flux<Item> actualList = itemService.getList();

        StepVerifier.create(actualList)
                .expectNext(new Item(), new Item()).verifyComplete();
    }

}