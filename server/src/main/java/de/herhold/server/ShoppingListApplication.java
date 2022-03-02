package de.herhold.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class ShoppingListApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingListApplication.class, args);
    }
}
