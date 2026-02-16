package com.backend.java.microservice.repository;

import com.backend.java.microservice.model.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {
}

