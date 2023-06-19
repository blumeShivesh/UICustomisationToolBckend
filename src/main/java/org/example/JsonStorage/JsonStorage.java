package org.example.JsonStorage;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class JsonStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String jsonData;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String mode;
    // Getters and setters

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getJsonData() {
//        return jsonData;
//    }
//
//    public void setJsonData(String data) {
//        this.jsonData = data;
//    }
}
