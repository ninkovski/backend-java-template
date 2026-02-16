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
public class ExperienceDTO {
    private String company;
    private String position;
    private String startDate; // kept as String to match cv-ejemplo.json (e.g., "Noviembre 2024", "2019")
    private String endDate;   // kept as String (e.g., "Presente")
    private List<String> highlights;
}
