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
    private Integer amount = 1;
}
