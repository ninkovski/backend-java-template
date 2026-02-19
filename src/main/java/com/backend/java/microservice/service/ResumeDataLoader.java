package com.backend.java.microservice.service;

import com.backend.java.microservice.model.entity.ExperienceEntity;
import com.backend.java.microservice.model.entity.ExperienceHighlightEntity;
import com.backend.java.microservice.model.entity.ProfileEntity;
import com.backend.java.microservice.model.entity.SkillEntity;
import com.backend.java.microservice.model.entity.CertificationEntity;
import com.backend.java.microservice.repository.ProfileRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

@Component
public class ResumeDataLoader {

    private final ProfileRepository profileRepository;
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    @Autowired
    public ResumeDataLoader(ProfileRepository profileRepository, ResourceLoader resourceLoader, ObjectMapper objectMapper) {
        this.profileRepository = profileRepository;
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {
        try {
            if (!profileRepository.existsById(1)) {
                Resource res = resourceLoader.getResource("classpath:cv-ejemplo.json");
                String fileJson = StreamUtils.copyToString(res.getInputStream(), StandardCharsets.UTF_8);
                JsonNode root = objectMapper.readTree(fileJson);
                JsonNode profileNode = root.path("profile");

                ProfileEntity profile = ProfileEntity.builder()
                        .id(1)
                        .fullName(profileNode.path("fullName").asText(null))
                        .role(profileNode.path("role").asText(null))
                        .summary(profileNode.path("summary").asText(null))
                        .linkedinUrl(profileNode.path("linkedinUrl").asText(null))
                        .githubUrl(profileNode.path("githubUrl").asText(null))
                        .build();

                // experiences
                JsonNode experiencesNode = profileNode.path("experiences");
                if (experiencesNode.isArray()) {
                    Iterator<JsonNode> it = experiencesNode.elements();
                    while (it.hasNext()) {
                        JsonNode exp = it.next();
                        ExperienceEntity expEntity = ExperienceEntity.builder()
                                .company(exp.path("company").asText(null))
                                .position(exp.path("position").asText(null))
                                .startDate(exp.path("startDate").asText(null))
                                .endDate(exp.path("endDate").asText(null))
                                .build();
                        expEntity.setProfile(profile);

                        JsonNode highlights = exp.path("highlights");
                        if (highlights.isArray()) {
                            Iterator<JsonNode> hit = highlights.elements();
                            while (hit.hasNext()) {
                                String text = hit.next().asText(null);
                                ExperienceHighlightEntity h = ExperienceHighlightEntity.builder()
                                        .highlightText(text)
                                        .experience(expEntity)
                                        .build();
                                expEntity.getHighlights().add(h);
                            }
                        }

                        profile.getExperiences().add(expEntity);
                    }
                }

                // skills
                JsonNode skillsNode = profileNode.path("skills");
                if (skillsNode.isArray()) {
                    Iterator<JsonNode> sit = skillsNode.elements();
                    while (sit.hasNext()) {
                        JsonNode sk = sit.next();
                        SkillEntity skill = SkillEntity.builder()
                                .name(sk.path("name").asText(null))
                                .level(sk.path("level").asText(null))
                                .years(sk.path("years").isInt() ? sk.path("years").asInt() : null)
                                .profile(profile)
                                .build();
                        profile.getSkills().add(skill);
                    }
                }

                // certifications
                JsonNode certificationsNode = profileNode.path("certifications");
                if (certificationsNode.isArray()) {
                    Iterator<JsonNode> cit = certificationsNode.elements();
                    while (cit.hasNext()) {
                        JsonNode cert = cit.next();
                        CertificationEntity certification = CertificationEntity.builder()
                                .name(cert.path("name").asText(null))
                                .provider(cert.path("provider").asText(null))
                                .credentialUrl(cert.path("credentialUrl").asText(null))
                                .profile(profile)
                                .build();
                        profile.getCertifications().add(certification);
                    }
                }

                profileRepository.save(profile);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error al precargar cv-ejemplo.json en H2 via JPA", ex);
        }
    }
}
