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

import java.util.List;

@Service
public class JsonStorageService {
    private final JsonStorageRepository jsonStorageRepository;

    @Autowired
    public JsonStorageService(JsonStorageRepository jsonStorageRepository) {
        this.jsonStorageRepository = jsonStorageRepository;
    }
    @Autowired
    JwtUserRepository jwtUserRepository ;

    public String saveJson(JsonStorage jsonStorage) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // Set the created by and updated by fields
        jsonStorage.setCreatedBy(username);
        jsonStorage.setUpdatedBy(username);
        if(jwtUserRepository.findUserByUserName(username)!=null){
            JwtUser jwtUser = jwtUserRepository.findUserByUserName(jsonStorage.getCreatedBy());
            String orgCode = jwtUser.getOrgCode();
            jsonStorage.setOrgCode(orgCode);
        }
        //        JwtUser jwtUser = jwtUserRepository.findUserByUserName(username);
        //        jsonStorage.setJwtuser(jwtUser);
        //        jsonStorage.setOrgCode(jwtUser.getOrgCode());
        System.out.println("JsonStorageService.saveJson: " + jsonStorage.getJsonData());
        JsonStorage savedJson = jsonStorageRepository.save(jsonStorage);
        System.out.println("JsonStorageService.saveJson: " + savedJson.getJsonData());
        return savedJson.getJsonData();
    }


public JsonStorage getJsonById(Long id) {
    JsonStorage jsonStorage = jsonStorageRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("JsonStorage not found with id: " + id));
    if(jsonStorage.getOrgCode()=="admin") return jsonStorage;
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

         List<JsonStorage> jsonStorageList =     jsonStorageRepository.findAll();
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
    // Add other service methods as needed
}
