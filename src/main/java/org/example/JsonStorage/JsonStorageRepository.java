package org.example.JsonStorage;

import org.example.models.JwtUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface JsonStorageRepository extends JpaRepository<JsonStorage, Long> {

    @Query(value = "SELECT * FROM json_storage WHERE template_name = 'DefaultBlumeTemplate'", nativeQuery = true)
    JsonStorage findByOrgCodeDefault();

    List<JsonStorage> findByOrgCode(String orgCode);
    @Query(value = "SELECT * FROM json_storage WHERE org_code = :orgCode And mode=:mode", nativeQuery = true)
    JsonStorage findByOrgCodeAndMode(@Param("orgCode") String orgCode, @Param("mode")  String mode);
}