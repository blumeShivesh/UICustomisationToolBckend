package org.example.JsonStorage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor // creates constructor on its own, no need to write an explicit constructor.
public class JsonStorageDTO {
    private long id;
    private String jsonData;
    private String mode;
    private String templateName;
}
