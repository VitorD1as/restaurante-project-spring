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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

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

        Optional<ItemPedidoEntity> itemExistente = pedidoEntity.getItens().stream()
                .filter(item -> item.getPrato().getId().equals(pratoEntity.getId()))
                .findFirst();

        if (itemExistente.isPresent()) {
            ItemPedidoEntity item = itemExistente.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            ItemPedidoEntity novoItem = ItemPedidoEntity.builder()
                    .pedido(pedidoEntity)
                    .prato(pratoEntity)
                    .quantity(quantity)
                    .build();

            pedidoEntity.getItens().add(novoItem);
        }

        PedidoEntity save = pedidoRepository.save(pedidoEntity);

        return toDTO(save);
    }

    public PedidoDTO atualizarStatusPedido(@NotNull Long pedidoId, PedidoStatus novoStatus) throws NotFoundException, BadRequestException {
        PedidoEntity pedido = pedidoRepository.findById(pedidoId).orElseThrow(() -> new NotFoundException("Pedido não encontrado!"));

        if(pedido.getPedidoStatus() == PedidoStatus.FINALIZADO ){
            throw new BadRequestException("Pedido já finalizado!");
        }

        if(novoStatus.getOrdem() < pedido.getPedidoStatus().getOrdem()){
            throw new BadRequestException("Não pode voltar o status do pedido");
        }

        if (novoStatus.getOrdem() - pedido.getPedidoStatus().getOrdem() > 1) {
            throw new BadRequestException("Não pode pular etapas do pedido");
        }
        pedido.setPedidoStatus(novoStatus);

        return toDTO(pedidoRepository.save(pedido));
    }

    public PedidoDTO finalizarPedido(@NotNull Long pedidoId) throws NotFoundException, BadRequestException {
        PedidoEntity pedido = pedidoRepository.findById(pedidoId).orElseThrow(() -> new NotFoundException("Pedido não encontrado!"));

        if(pedido.getPedidoStatus() == PedidoStatus.FINALIZADO){
            throw new BadRequestException("Pedido já está finalizado!");
        }

        if(pedido.getItens() == null || pedido.getItens().isEmpty()){
            throw new BadRequestException("Pedido não possui itens!");
        }

        pedido.setPedidoStatus(PedidoStatus.FINALIZADO);

        BigDecimal total = pedido.getItens().stream()
                .map(item -> item.getPrato().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        pedido.setTotal(total);
        return toDTO(pedidoRepository.save(pedido));
    }

    public PedidoDTO cancelarPedido(@NotNull Long pedidoId) throws NotFoundException, BadRequestException {
        PedidoEntity pedido = pedidoRepository.findById(pedidoId).orElseThrow(() -> new NotFoundException("Pedido não encontrado!"));

        if(pedido.getPedidoStatus() == PedidoStatus.FINALIZADO){
            throw new BadRequestException("Você não pode cancelar um pedido já finalizado!");
        }

        if(pedido.getPedidoStatus() == PedidoStatus.CANCELADO){
            throw new BadRequestException("Você não pode cancelar um pedido já cancelado!");
        }

        pedido.setPedidoStatus(PedidoStatus.CANCELADO);

        return toDTO(pedidoRepository.save(pedido));
    }


    // métodos auxiliares

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
