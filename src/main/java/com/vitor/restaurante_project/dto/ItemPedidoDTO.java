package com.vitor.restaurante_project.dto;

import com.vitor.restaurante_project.database.model.PedidoEntity;
import com.vitor.restaurante_project.database.model.PratoEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class ItemPedidoDTO {
        private Long id;

        private PratoDTO prato;

        private Integer quantity;
}
