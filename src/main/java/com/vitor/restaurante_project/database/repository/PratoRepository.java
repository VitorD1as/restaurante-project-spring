package com.vitor.restaurante_project.database.repository;

import com.vitor.restaurante_project.database.model.PratoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PratoRepository extends JpaRepository<PratoEntity, Long> {

}
