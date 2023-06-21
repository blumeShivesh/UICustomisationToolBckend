package org.example.JsonStorage;

import org.example.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    public String saveJson(JsonStorage jsonStorage) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // Set the created by and updated by fields
        jsonStorage.setCreatedBy(username);
        jsonStorage.setUpdatedBy(username);
        //        JwtUser jwtUser = jwtUserRepository.findUserByUserName(username);
        //        jsonStorage.setJwtuser(jwtUser);
        //        jsonStorage.setOrgCode(jwtUser.getOrgCode());
        System.out.println("JsonStorageService.saveJson: " + jsonStorage.getJsonData());
        JsonStorage savedJson = jsonStorageRepository.save(jsonStorage);
        System.out.println("JsonStorageService.saveJson: " + savedJson.getJsonData());
        return savedJson.getJsonData();
    }

    public JsonStorage getJsonById(Long id) {
        return jsonStorageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("JsonStorage not found with id: " + id));
    }

    public List<JsonStorage> getAllJson() {
        return jsonStorageRepository.findAll();
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
