package org.example.JsonStorage;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.models.JwtUser;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.mapping.ToOne;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name="json_storage")
public class JsonStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String jsonData;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mode;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String createdBy;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String updatedBy;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String orgCode;
    @CreationTimestamp
    private Instant createdOn;

    @UpdateTimestamp
    private Instant lastUpdatedOn;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name="jwt_user_id")
//    private JwtUser jwtuser;

    @PrePersist
    // we might have a millisecond time diff between updated time and created time during the first creation of time stamp , so we use these methods
    protected void onCreate(){
        lastUpdatedOn = createdOn;
    }
    @PreUpdate
    protected void onUpdate(){
        lastUpdatedOn  = Instant.now();
    }


    // Getters and setters
}