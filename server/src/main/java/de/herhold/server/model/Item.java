package de.herhold.server.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table
public class Item {

    @Id
    private Integer id;
    private String name;
    private String amount = "1";

    public void clone(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.amount = item.getAmount();
    }
}
