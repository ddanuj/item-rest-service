package com.sap.itemservice;

import com.sap.itemservice.model.ItemObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootApplication
public class ItemServiceApplication {
    public static Map<Long, ItemObject> itemMap = new LinkedHashMap<>();
    public static void main(String[] args) {
        SpringApplication.run(ItemServiceApplication.class, args);
    }
}
