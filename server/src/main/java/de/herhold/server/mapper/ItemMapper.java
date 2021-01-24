package de.herhold.server.mapper;

import de.herhold.server.model.Item;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    @SneakyThrows
    public de.herhold.shopping_list.api.java_server.model.Item mapItem(Item item) {
        Thread.sleep(1000);
        de.herhold.shopping_list.api.java_server.model.Item mappedItem = new de.herhold.shopping_list.api.java_server.model.Item();
        mappedItem.setName(item.getName());
        mappedItem.setAmount(item.getAmount());
        mappedItem.setId(item.getId());
        return mappedItem;
    }
}
