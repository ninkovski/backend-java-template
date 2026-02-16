package com.backend.java.microservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "experience_highlights")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExperienceHighlightEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "experience_id")
    private ExperienceEntity experience;

    @Column(name = "highlight_text", columnDefinition = "CLOB")
    private String highlightText;
}

