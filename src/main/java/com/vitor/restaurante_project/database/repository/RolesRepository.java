package com.vitor.restaurante_project.database.repository;

import com.vitor.restaurante_project.database.model.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<RolesEntity, Long> {
    Optional<RolesEntity> findByNome(String nome);
}
