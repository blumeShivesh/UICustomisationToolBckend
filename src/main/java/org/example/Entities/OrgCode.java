package org.example.OrgCode;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Getter
@Setter

public class OrgCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,columnDefinition = "TEXT")
    private String orgCode;
    @Column(nullable = false,columnDefinition = "TEXT")
    private String value;
}
