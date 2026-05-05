package com.vitor.restaurante_project.service;

import com.vitor.restaurante_project.database.model.ItemPedidoEntity;
import com.vitor.restaurante_project.database.model.PedidoEntity;
import com.vitor.restaurante_project.database.model.PedidoStatus;
import com.vitor.restaurante_project.database.model.PratoEntity;
import com.vitor.restaurante_project.database.repository.PedidoRepository;
import com.vitor.restaurante_project.database.repository.PratoRepository;
import com.vitor.restaurante_project.dto.ItemPedidoDTO;
import com.vitor.restaurante_project.dto.PedidoDTO;
import com.vitor.restaurante_project.dto.PratoDTO;
import com.vitor.restaurante_project.exception.BadRequestException;
import com.vitor.restaurante_project.exception.NotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final PratoRepository pratoRepository;

    public void criarPedido(){
        PedidoEntity pedido = PedidoEntity.builder()
                .pedidoStatus(PedidoStatus.CRIADO)
                .build();
        pedidoRepository.save(pedido);
    }

    public PedidoDTO adicionarItem(@NotNull Long pedidoId, @NotNull Long pratoId, @Positive Integer quantity) throws NotFoundException, BadRequestException {
        PedidoEntity pedidoEntity = pedidoRepository.findById(pedidoId).orElseThrow(() -> new NotFoundException("Pedido não encontrado!"));
        PratoEntity pratoEntity = pratoRepository.findById(pratoId).orElseThrow(() -> new NotFoundException("Prato não encontrado!"));

        if (pedidoEntity.getItens() == null) {
            pedidoEntity.setItens(new ArrayList<>());
        }

        if (pedidoEntity.getPedidoStatus() != PedidoStatus.CRIADO) {
            throw new BadRequestException("Pedido não pode receber itens nesse status");
        }

            ItemPedidoEntity itemPedidoEntity = ItemPedidoEntity.builder()
                    .pedido(pedidoEntity)
                    .quantity(quantity)
                    .prato(pratoEntity)
                    .build();

        pedidoEntity.getItens().add(itemPedidoEntity);
        PedidoEntity save = pedidoRepository.save(pedidoEntity);

        return toDTO(save);
    }

    private PedidoDTO toDTO(PedidoEntity entity) {
        return PedidoDTO.builder()
                .id(entity.getId())
                .pedidoStatus(entity.getPedidoStatus())
                .itens(entity.getItens() == null
                        ? new ArrayList<>()
                        : entity.getItens().stream()
                        .map(this::toItemDTO)
                        .toList())
                .build();
    }

    private ItemPedidoDTO toItemDTO(ItemPedidoEntity entity) {
        return ItemPedidoDTO.builder()
                .id(entity.getId())
                .quantity(entity.getQuantity())
                .prato(
                        PratoDTO.builder()
                                .id(entity.getPrato().getId())
                                .name(entity.getPrato().getName())
                                .price(entity.getPrato().getPrice())
                                .build()
                )
                .build();
    }
}
