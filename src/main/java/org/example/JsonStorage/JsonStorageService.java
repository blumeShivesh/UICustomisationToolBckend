package org.example.JsonStorage;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.exception.NotFoundException;

import org.example.models.JwtUser;
import org.example.models.JwtUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.example.models.JwtUser;
import org.example.models.JwtUserRepository;
import java.util.List;

@Service
public class JsonStorageService {
    private final JsonStorageRepository jsonStorageRepository;
    @Autowired
    JwtUserRepository jwtUserRepository;

    @Autowired
    public JsonStorageService(JsonStorageRepository jsonStorageRepository) {
        this.jsonStorageRepository = jsonStorageRepository;
    }

    public String saveJson(JsonStorage jsonStorage) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        jsonStorage.setCreatedBy(email);
        jsonStorage.setUpdatedBy(email);
        System.out.println("JsonStorageService.saveJson: " + email);
        System.out.println("JsonStorageService.saveJson: " + jwtUserRepository.findUserByEmail(email));
        JwtUser user = jwtUserRepository.findUserByEmail(email);
        jsonStorage.setOrgCode(user.getOrgCode());

        System.out.println("JsonStorageService.saveJson: " + jsonStorage.getOrgCode());
        JsonStorage savedJson = jsonStorageRepository.save(jsonStorage);
        System.out.println("JsonStorageService.saveJson: " + savedJson.getJsonData());
        return savedJson.getJsonData();
    }


public JsonStorage getJsonById(Long id) {
    JsonStorage jsonStorage = jsonStorageRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("JsonStorage not found with id: " + id));
    if(jsonStorage.getOrgCode() != null && !jsonStorage.getOrgCode().isEmpty() ) {
        return jsonStorage;
    }
    String jsondata = jsonStorage.getJsonData();
    // method to convert the string to json and remove necessary things
    ObjectMapper mapper = new ObjectMapper();

    try {
        ArrayNode arrayNode = (ArrayNode) mapper.readTree(jsondata);

        for (JsonNode rootNode : arrayNode) {
            ((ObjectNode) rootNode).remove("isDragEnabled");
            ((ObjectNode) rootNode).remove("isDropEnabledAllSections");
            ((ObjectNode) rootNode).remove("dropEnabledSections");
            ((ObjectNode) rootNode).remove("itemsDropEnabledSections");
            ((ObjectNode) rootNode).remove("isDropEnable");
            ((ObjectNode) rootNode).remove("isitemDragEnabled");
            ((ObjectNode) rootNode).remove("isitemDropEnabled");
        }

        String modifiedJson = mapper.writeValueAsString(arrayNode);
        jsonStorage.setJsonData(modifiedJson);

        return jsonStorage;
    } catch (JsonProcessingException e) {
        throw new RuntimeException("JsonStorage not found with id: " + id);
    }
}

    public List<JsonStorage> getAllJson() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        JwtUser user = jwtUserRepository.findUserByEmail(email);

         List<JsonStorage> jsonStorageList =     jsonStorageRepository.findByOrgCode(user.getOrgCode());
        JsonStorage defaultJson = jsonStorageRepository.findByOrgCodeDefault();
         System.out.println("JsonStorageService.getAllJson: " + defaultJson);
         if (defaultJson != null) {
            jsonStorageList.add(defaultJson);
        }

        return jsonStorageList;
    }

    public void deleteJson(Long id) {
        jsonStorageRepository.deleteById(id);
    }

    public void deleteAllJson() {
        jsonStorageRepository.deleteAll();
    }

    public String updateJson(Long id, JsonStorage jsonStorage) {
        JsonStorage existingJson = jsonStorageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("JsonStorage not found with id: " + id));
        existingJson.setJsonData(jsonStorage.getJsonData());
        existingJson.setMode(jsonStorage.getMode());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Set the created by and updated by fields
        jsonStorage.setCreatedBy(username);
        jsonStorage.setUpdatedBy(username);
        return jsonStorageRepository.save(existingJson).getJsonData();
    }

    public JsonStorage getDefaultTemplate() {
        JsonStorage defaultjson= jsonStorageRepository.findByOrgCodeDefault();
        System.out.println("JsonStorageService.getDefaultTemplate: " + defaultjson);
        return defaultjson;
    }
    // Add other service methods as needed
}
