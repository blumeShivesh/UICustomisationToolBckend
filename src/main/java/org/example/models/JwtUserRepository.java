package org.example.models;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JwtUserRepository extends JpaRepository<JwtUser, Long> {
    // Add custom query methods if needed
    @Query(nativeQuery = true,
            value = "SELECT * FROM jwt_user WHERE email = :email"
    )
    JwtUser findUserByEmail(@Param("email") String email);
}