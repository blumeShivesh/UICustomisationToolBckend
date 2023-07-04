package org.example.JsonStorage;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exception.NotFoundException;

import org.example.models.JwtUser;
import org.example.models.JwtUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
// should understand get all Json.

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
        JsonStorage existingJsonStorage = jsonStorageRepository.findByOrgCodeAndMode(jsonStorage.getOrgCode(),jsonStorage.getMode());
        if(existingJsonStorage==null) {
            existingJsonStorage = jsonStorage;
        }
        else existingJsonStorage.setJsonData(jsonStorage.getJsonData());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // returns the email
        existingJsonStorage.setCreatedBy(email);
        existingJsonStorage.setUpdatedBy(email);
        existingJsonStorage.setTemplateName(jsonStorage.getTemplateName());
        System.out.println("JsonStorageService.saveJson: " + email);
        System.out.println("JsonStorageService.saveJson: " + jwtUserRepository.findUserByEmail(email));
        JwtUser user = jwtUserRepository.findUserByEmail(email);
        System.out.println("JsonStorageService.saveJson: " + existingJsonStorage.getOrgCode());
        JsonStorage savedJson = jsonStorageRepository.save(existingJsonStorage);
        System.out.println("JsonStorageService.saveJson: " + existingJsonStorage.getJsonData());
        return savedJson.getJsonData();
    }


public JsonStorage getJsonById(Long id) {
    JsonStorage jsonStorage = jsonStorageRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("JsonStorage not found with id: " + id));
    if(jsonStorage.getOrgCode() !=null&&jsonStorage.getOrgCode()=="admin") {
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

    public List<JsonStorageCrux> getAllJson() { // bina jsondata
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();
//        JwtUser user = jwtUserRepository.findUserByEmail(email);
//
//         List<JsonStorage> jsonStorageList =     jsonStorageRepository.findByOrgCode(user.getOrgCode());
//        JsonStorage defaultJson = jsonStorageRepository.findByOrgCodeDefault();
//         System.out.println("JsonStorageService.getAllJson: " + defaultJson);
//         if (defaultJson != null) {
//            jsonStorageList.add(defaultJson);
//        }
//        List<JsonStorageCrux> jsonStorageCruxList = new ArrayList<>();
//        for(JsonStorage i: jsonStorageList){
//            JsonStorageCrux temp= new JsonStorageCrux(i.getOrgCode(),i.getTemplateName(),i.getMode(),i.getId());
//            jsonStorageCruxList.add(temp);
//        }
//        return jsonStorageCruxList;
        List <JsonStorage> jsonStorageList = jsonStorageRepository.findAll();
        List <JsonStorageCrux> jsonStorageCruxList =new ArrayList<>();;
        for(JsonStorage i: jsonStorageList){
            System.out.println(i.getTemplateName());
            if(i.getTemplateName().equals("defaultBlumeTemplate")){
                continue;
            }
            else {
                JsonStorageCrux jsonStorageCrux = new JsonStorageCrux(i.getOrgCode(), i.getTemplateName(), i.getMode(), i.getId());
                jsonStorageCruxList.add(jsonStorageCrux);
            }
//            JsonStorageCrux jsonStorageCrux = new JsonStorageCrux(i.getOrgCode(), i.getTemplateName(), i.getMode(), i.getId());
//            jsonStorageCruxList.add(jsonStorageCrux);
        }
        return jsonStorageCruxList;

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

    public JsonStorage getShipmentTemplate(String mode) {
        String email= SecurityContextHolder.getContext().getAuthentication().getName();
        JwtUser user= jwtUserRepository.findUserByEmail(email);


        JsonStorage jsonStorage = (JsonStorage) jsonStorageRepository.findByOrgCodeAndMode(user.getOrgCode(),mode);
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
            throw new RuntimeException("JsonStorage not found with id: " );
        }
    }
}
