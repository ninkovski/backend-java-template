package com.backend.java.microservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "certifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;

    private String name;
    private String provider;

    @Column(name = "credential_url")
    private String credentialUrl;
}
