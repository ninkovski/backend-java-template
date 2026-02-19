package com.backend.java.microservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
    private String fullName;
    private String role;
    private String summary;
    private String linkedinUrl;
    private String githubUrl;
    private List<ExperienceDTO> experiences;
    private List<SkillDTO> skills;
    private List<CertificationDTO> certifications;
}
