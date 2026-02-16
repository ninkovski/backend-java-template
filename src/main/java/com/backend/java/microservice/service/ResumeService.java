package com.backend.java.microservice.service;

import com.backend.java.microservice.model.dto.ExperienceDTO;
import com.backend.java.microservice.model.dto.ProfileDTO;
import com.backend.java.microservice.model.dto.SkillDTO;
import com.backend.java.microservice.model.entity.ExperienceEntity;
import com.backend.java.microservice.model.entity.ProfileEntity;
import com.backend.java.microservice.model.entity.SkillEntity;
import com.backend.java.microservice.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResumeService {

    private final ProfileRepository profileRepository;

    @Autowired
    public ResumeService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public ProfileDTO getProfile() {
        return profileRepository.findById(1).map(this::mapToDto).orElse(null);
    }

    private ProfileDTO mapToDto(ProfileEntity profile) {
        ProfileDTO dto = ProfileDTO.builder()
                .fullName(profile.getFullName())
                .role(profile.getRole())
                .summary(profile.getSummary())
                .linkedinUrl(profile.getLinkedinUrl())
                .githubUrl(profile.getGithubUrl())
                .build();

        List<ExperienceDTO> experiences = profile.getExperiences().stream().map(this::mapExperience).collect(Collectors.toList());
        List<SkillDTO> skills = profile.getSkills().stream().map(this::mapSkill).collect(Collectors.toList());

        dto.setExperiences(experiences);
        dto.setSkills(skills);
        return dto;
    }

    private ExperienceDTO mapExperience(ExperienceEntity e) {
        List<String> highlights = e.getHighlights().stream().map(h -> h.getHighlightText()).collect(Collectors.toList());
        return ExperienceDTO.builder()
                .company(e.getCompany())
                .position(e.getPosition())
                .startDate(e.getStartDate())
                .endDate(e.getEndDate())
                .highlights(highlights)
                .build();
    }

    private SkillDTO mapSkill(SkillEntity s) {
        return SkillDTO.builder()
                .name(s.getName())
                .level(s.getLevel())
                .years(s.getYears())
                .build();
    }
}
