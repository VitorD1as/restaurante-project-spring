package com.vitor.restaurante_project.dto;

import com.vitor.restaurante_project.database.model.ItemPedidoEntity;
import com.vitor.restaurante_project.database.model.PedidoStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PedidoDTO {
    private Long id;

    private PedidoStatus pedidoStatus;

    private List<ItemPedidoDTO> itens;
}
