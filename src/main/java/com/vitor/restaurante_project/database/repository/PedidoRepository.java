package com.vitor.restaurante_project.database.repository;

import com.vitor.restaurante_project.database.model.PedidoEntity;
import com.vitor.restaurante_project.dto.PedidoDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {
}
