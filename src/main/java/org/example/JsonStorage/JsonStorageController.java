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
// return id,orgcode,mode,templatename
    @GetMapping
    public ResponseEntity<List<JsonStorageCrux>> getJson() {

        List<JsonStorageCrux> jsonStorageCruxList = jsonStorageService.getAllJson();

        return ResponseEntity.ok(jsonStorageCruxList);
    }
    @PostMapping
    public ResponseEntity<String> saveJson(@RequestBody JsonStorage jsonStorage) {
        System.out.println("JsonStorageController.saveJson: " + jsonStorage.getJsonData());
        String savedJson;
//        System.out.println("JsonStorageController.saveJson: " + jsonStorage.getJsonData());
        savedJson = jsonStorageService.saveJson(jsonStorage);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedJson);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JsonStorageDTO> getJsonById(@PathVariable("id") Long id) {
        System.out.println("JsonStorageController.getJsonById: " + id);
//        JsonStorage jsonStorage = jsonStorageService.getJsonById(id);
        JsonStorage jsonStorage = jsonStorageService.getJsonById(id);
        JsonStorageDTO jsonStorageDTO = new JsonStorageDTO(jsonStorage.getId(),jsonStorage.getJsonData(), jsonStorage.getMode(),jsonStorage.getTemplateName(), jsonStorage.getOrgCode());
        System.out.println("JsonStorageController.getJsonById: " + jsonStorageDTO.getJsonData());
        return ResponseEntity.ok(jsonStorageDTO);
    }
    // u will have so many users with different mode, and for each user u can fetch data using
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJson(@PathVariable("id") Long id) {
        jsonStorageService.deleteJson(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAllJson() {
        System.out.println("JsonStorageController.deleteAllJson: ");
        jsonStorageService.deleteAllJson();
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateJson(@PathVariable("id") Long id, @RequestBody JsonStorage jsonStorage) {
        System.out.println("JsonStorageController.updateJson: " + id);
        System.out.println("JsonStorageController.updateJson: " + jsonStorage.getJsonData());
        String updatedJson = jsonStorageService.updateJson(id, jsonStorage);
        return ResponseEntity.ok(updatedJson);
    }

    @GetMapping("/getDefaultTemplate")
    public ResponseEntity<JsonStorageDTO> getDefaultTemplate() {
        System.out.println("JsonStorageController.getDefaultTemplate: ");
        JsonStorage jsonStorage = jsonStorageService.getDefaultTemplate();
        System.out.println("JsonStorageController.getDefaultTemplate: " + jsonStorage.getJsonData());
        JsonStorageDTO jsonStorageDTO = new JsonStorageDTO(jsonStorage.getId(),jsonStorage.getJsonData(), jsonStorage.getMode(),jsonStorage.getTemplateName(), jsonStorage.getOrgCode());
        System.out.println("JsonStorageController.getDefaultTemplate: " + jsonStorageDTO.getJsonData());
        return ResponseEntity.ok(jsonStorageDTO);
    }
    @GetMapping("/getShipmentTemplate/{mode}")
    public ResponseEntity<JsonStorageDTO> getShipmentTemplate(@PathVariable("mode") String mode) {
        System.out.println("JsonStorageController.getShipmentTemplate: ");
        JsonStorage jsonStorage = jsonStorageService.getShipmentTemplate(mode);
        JsonStorageDTO jsonStorageDTO = new JsonStorageDTO(jsonStorage.getId(),jsonStorage.getJsonData(), jsonStorage.getMode(),jsonStorage.getTemplateName(), jsonStorage.getOrgCode());
        System.out.println("JsonStorageController.getShipmentTemplate: " + jsonStorage.getJsonData());
        return ResponseEntity.ok(jsonStorageDTO);
    }
    // Add other controller methods as needed
}

