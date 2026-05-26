package com.example.botanix.repository;

import com.example.botanix.model.Plantas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantasRepository extends JpaRepository<Plantas, Long> {
}
