package com.vitor.restaurante_project.database.repository;

import com.vitor.restaurante_project.database.model.PratoEntity;
import com.vitor.restaurante_project.dto.PratoDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PratoRepository extends JpaRepository<PratoEntity, Long> {
}
