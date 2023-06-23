package org.example.JsonStorage;

import org.example.models.JwtUser;
import org.example.models.JwtUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/json")
@CrossOrigin(origins = "http://localhost:3000")
public class JsonStorageController {
    private final JsonStorageService jsonStorageService;

    @Autowired
    private JwtUserRepository jwtUserRepository;



    // Set the created by and updated by field
    @Autowired
    public JsonStorageController(JsonStorageService jsonStorageService) {
        this.jsonStorageService = jsonStorageService;
    }

    @GetMapping
    public ResponseEntity<List<JsonStorageDTO>> getJson() {

        List<JsonStorage> jsonStorageList = jsonStorageService.getAllJson();
        List<JsonStorageDTO> jsonStorageDTOList = new ArrayList<>();
        for(JsonStorage i: jsonStorageList){
            JsonStorageDTO temp= new JsonStorageDTO(i.getId(),i.getJsonData(),i.getMode());
            jsonStorageDTOList.add(temp);
        }
        return ResponseEntity.ok(jsonStorageDTOList);
    }
    @PostMapping
    public ResponseEntity<String> saveJson(@RequestBody JsonStorage jsonStorage) {
        String savedJson;
        savedJson = jsonStorageService.saveJson(jsonStorage);
        System.out.println("JsonStorageController.saveJson: " + jsonStorage.getJsonData());
        System.out.println("JsonStorageController.saveMode: "+jsonStorage.getMode());
        System.out.println("JsonStorageController.saveCreatedTimeStamp: "+jsonStorage.getCreatedOn());
        System.out.println("JsonStorageController.saveUpdatedTimeStamp: "+jsonStorage.getLastUpdatedOn());
        System.out.println("JsonStorageController.saveCreatedBy: "+jsonStorage.getCreatedBy());
        System.out.println("JsonStorageController.saveUpdatedBy: "+jsonStorage.getUpdatedBy());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedJson);
    }

    @GetMapping("/{mode}/{id}")
    public ResponseEntity<JsonStorageDTO> getJsonById(@PathVariable("id") Long id) {
//        JsonStorage jsonStorage = jsonStorageService.getJsonById(id);
        JsonStorage jsonStorage = jsonStorageService.getJsonById(id);
        JsonStorageDTO jsonStorageDTO = new JsonStorageDTO(jsonStorage.getId(),jsonStorage.getJsonData(), jsonStorage.getMode());
        return ResponseEntity.ok(jsonStorageDTO);
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
        System.out.println("JsonStorageController.CreatedInstant: "+jsonStorage.getCreatedOn());
        System.out.println("JsonStorageController.UpdatedInstant: "+jsonStorage.getLastUpdatedOn());
        System.out.println("JsonStorageController.CreatedBy: "+jsonStorage.getCreatedBy());
        System.out.println("JsonStorageController.UpdatedBy: "+jsonStorage.getUpdatedBy());
        String updatedJson = jsonStorageService.updateJson(id, jsonStorage);
        return ResponseEntity.ok(updatedJson);
    }
    // Add other controller methods as needed
}
