package de.herhold.server.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Item {
    @Id
    private int id;
    private String name;
    private String amount = "1";
}
