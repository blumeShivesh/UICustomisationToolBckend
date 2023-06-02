package org.example.JsonStorage;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface JsonStorageRepository extends JpaRepository<JsonStorage, Long> {
    // Add custom query methods if needed
}
