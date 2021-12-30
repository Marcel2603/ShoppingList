package de.herhold.server.mapper;

import de.herhold.server.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemMapper {
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    de.herhold.shopping_list.api.java_server.model.Item mapItem(Item item);

    Item convertItem(de.herhold.shopping_list.api.java_server.model.Item item);
}
