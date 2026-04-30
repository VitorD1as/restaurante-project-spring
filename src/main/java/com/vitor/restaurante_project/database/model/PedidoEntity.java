package com.vitor.restaurante_project.database.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class PedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private PedidoStatus pedidoStatus = PedidoStatus.CRIADO;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedidoEntity> itens;
}
