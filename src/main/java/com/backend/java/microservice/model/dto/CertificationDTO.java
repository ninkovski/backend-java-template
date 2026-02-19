package com.backend.java.microservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificationDTO {
    private String name;
    private String provider;
    private String credentialUrl;
}
