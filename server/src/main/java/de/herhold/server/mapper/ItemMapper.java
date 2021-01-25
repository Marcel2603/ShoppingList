package de.herhold.server.mapper;

import de.herhold.server.model.Item;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    @SneakyThrows
    public de.herhold.shopping_list.api.java_server.model.Item mapItem(Item item) {
        de.herhold.shopping_list.api.java_server.model.Item mappedItem = new de.herhold.shopping_list.api.java_server.model.Item();
        mappedItem.setName(item.getName());
        mappedItem.setAmount(item.getAmount());
        mappedItem.setId(item.getId());
        return mappedItem;
    }

    public Item convertItem(de.herhold.shopping_list.api.java_server.model.Item item) {
        Item convertedItem = new Item();
        convertedItem.setName(item.getName());
        convertedItem.setAmount(item.getAmount());
        return convertedItem;
    }
}
