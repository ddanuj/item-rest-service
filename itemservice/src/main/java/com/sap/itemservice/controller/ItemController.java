package com.sap.itemservice.controller;

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
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

@RestController
public class ItemController {
    private Map<Long, ItemObject> itemMap = new ConcurrentSkipListMap<>();
    private int counter = 0;

    @GetMapping("/items")
    public List<ItemObject> getItems() {
        Long lastTwoSeconds = System.currentTimeMillis() - 2000;
        List<ItemObject> inLastTwoSeconds = itemMap.entrySet()
                .stream()
                .filter(o -> o.getKey() >= lastTwoSeconds)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        if (inLastTwoSeconds.size() > 100) {
            counter++;
            if (counter > 5){
                System.out.println("Before: " + itemMap.entrySet().size());
                itemMap.entrySet()
                        .stream()
                        .filter(o -> o.getKey() < lastTwoSeconds)
                        .forEach(o -> itemMap.remove(o.getKey()));
                System.out.println("After: " + itemMap.entrySet().size());
                counter = 0;
            }
            return inLastTwoSeconds;
        }
        System.out.println("Returning last 100 records ...");
        return itemMap.entrySet()
                .stream()
                .limit(100)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    @PostMapping("/items")
    public ResponseEntity createItem(@Valid @RequestBody ItemObject itemObject) {
        itemMap.put(System.currentTimeMillis(), itemObject);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
