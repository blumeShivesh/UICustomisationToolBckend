package org.example.JsonStorage;
import org.example.JsonStorage.JsonStorage;
import org.example.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JsonStorageService {
    @Autowired JsonStorageRepository jsonStorageRepository;


    public JsonStorageService(JsonStorageRepository jsonStorageRepository) {
        this.jsonStorageRepository = jsonStorageRepository;
    }

    public String saveJson(String data) {
        JsonStorage newJson= new JsonStorage();
        newJson.setJsonData(data);
        System.out.println("JsonStorageService.saveJson: " + newJson.getJsonData());
        JsonStorage savedJson=jsonStorageRepository.save(newJson);
        System.out.println("JsonStorageService.saveJson: " + savedJson.getJsonData());
        return savedJson.getJsonData();

    }

    public String getJsonById(Long id) {

        JsonStorage data = jsonStorageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("JsonStorage not found with id: " + id));

        return data.getJsonData();
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

    public String updateJson(Long id, String data) {
        JsonStorage existingJson = jsonStorageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("JsonStorage not found with id: " + id));
        existingJson.setJsonData(data);
        return jsonStorageRepository.save(existingJson).getJsonData();
    }
    // Add other service methods as needed
}
