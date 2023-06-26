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
import  org.example.util.JsonStoragePostRequest;

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
        System.out.println("JsonStorageController.saveJson: " + jsonStorage.getJsonData());
        String savedJson;
        System.out.println("JsonStorageController.saveJson: " + jsonStorage.getJsonData());
        savedJson = jsonStorageService.saveJson(jsonStorage);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedJson);
    }

    @GetMapping("/{mode}/{id}")
    public ResponseEntity<JsonStorageDTO> getJsonById(@PathVariable("id") Long id) {
        System.out.println("JsonStorageController.getJsonById: " + id);
//        JsonStorage jsonStorage = jsonStorageService.getJsonById(id);
        JsonStorage jsonStorage = jsonStorageService.getJsonById(id);
        JsonStorageDTO jsonStorageDTO = new JsonStorageDTO(jsonStorage.getId(),jsonStorage.getJsonData(), jsonStorage.getMode());
        System.out.println("JsonStorageController.getJsonById: " + jsonStorageDTO.getJsonData());
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

    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateJson(@PathVariable("id") Long id, @RequestBody JsonStorage jsonStorage) {
        String updatedJson = jsonStorageService.updateJson(id, jsonStorage);
        return ResponseEntity.ok(updatedJson);
    }

    @GetMapping("/getDefaultTemplate/")
    public ResponseEntity<JsonStorageDTO> getDefaultTemplate() {
        System.out.println("JsonStorageController.getDefaultTemplate: ");
        JsonStorage jsonStorage = jsonStorageService.getDefaultTemplate();
        System.out.println("JsonStorageController.getDefaultTemplate: " + jsonStorage.getJsonData());
        JsonStorageDTO jsonStorageDTO = new JsonStorageDTO(jsonStorage.getId(),jsonStorage.getJsonData(), jsonStorage.getMode());
        System.out.println("JsonStorageController.getDefaultTemplate: " + jsonStorageDTO.getJsonData());
        return ResponseEntity.ok(jsonStorageDTO);
    }
    // Add other controller methods as needed
}
