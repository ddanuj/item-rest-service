package com.sap.itemservice.controller;

import com.sap.itemservice.ItemServiceApplication;
import com.sap.itemservice.model.ItemObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ItemController {

    @GetMapping("/items")
    public List<ItemObject> getItems() {
        Long lastTwoSeconds = System.currentTimeMillis() - 2000;
        List<ItemObject> inLastTwoSeconds = ItemServiceApplication.itemMap
                .entrySet()
                .stream()
                .filter(o -> o.getKey() >= lastTwoSeconds)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        if (inLastTwoSeconds.size() > 100) {
            return inLastTwoSeconds;
        }
        return ItemServiceApplication.itemMap
                .entrySet()
                .stream()
                .limit(100)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    @PostMapping("/items")
    public ResponseEntity createItem(@Valid @RequestBody ItemObject itemObject) {
        ItemServiceApplication.itemMap.put(System.currentTimeMillis(), itemObject);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
