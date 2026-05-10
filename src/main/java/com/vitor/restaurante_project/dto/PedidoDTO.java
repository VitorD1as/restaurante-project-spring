package com.vitor.restaurante_project.dto;

import com.vitor.restaurante_project.database.model.PedidoStatus;

import java.util.List;

public record PedidoDTO (Long id, PedidoStatus pedidoStatus, List<ItemPedidoDTO> itens){}
