package org.example.JsonStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/json")
@CrossOrigin(origins = "http://localhost:3000")
public class JsonStorageController {
    private final JsonStorageService jsonStorageService;

    @Autowired
    public JsonStorageController(JsonStorageService jsonStorageService) {
        this.jsonStorageService = jsonStorageService;
    }

    @GetMapping
    public ResponseEntity<List<JsonStorage>> getJson() {
        List<JsonStorage> jsonStorageList = jsonStorageService.getAllJson();
        return ResponseEntity.ok(jsonStorageList);
    }

    @PostMapping
    public ResponseEntity<String> saveJson(@RequestBody JsonStorage jsonStorage) {
        System.out.println("JsonStorageController.saveJson: " + jsonStorage.getJsonData());
        System.out.println("JsonStorageController.saveMode: "+jsonStorage.getMode());
        String savedJson = jsonStorageService.saveJson(jsonStorage);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedJson);
    }

    @GetMapping("/{mode}/{id}")
    public ResponseEntity<JsonStorage> getJsonById(@PathVariable("id") Long id) {
        JsonStorage jsonStorage = jsonStorageService.getJsonById(id);
        return ResponseEntity.ok(jsonStorage);
    }

    @DeleteMapping("/{mode}/{id}")
    public ResponseEntity<Void> deleteJson(@PathVariable("id") Long id) {
        jsonStorageService.deleteJson(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{mode}/deleteAll")
    public ResponseEntity<Void> deleteAllJson() {
        System.out.println("JsonStorageController.deleteAllJson: ");
        jsonStorageService.deleteAllJson();
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update/{mode}/{id}")
    public ResponseEntity<String> updateJson(@PathVariable("id") Long id, @RequestBody JsonStorage jsonStorage) {
        System.out.println("JsonStorageController.updateJson: " + jsonStorage.getJsonData());
        String updatedJson = jsonStorageService.updateJson(id, jsonStorage);
        return ResponseEntity.ok(updatedJson);
    }
    // Add other controller methods as needed
}
