package com.vitor.restaurante_project.controller;

import com.vitor.restaurante_project.database.model.PedidoStatus;
import com.vitor.restaurante_project.dto.PedidoDTO;
import com.vitor.restaurante_project.exception.BadRequestException;
import com.vitor.restaurante_project.exception.NotFoundException;
import com.vitor.restaurante_project.service.PedidoService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    private final PedidoService pedidoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarPedido(){
        pedidoService.criarPedido();
    }

    @PostMapping("/{pedidoId}/itens")
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO adicionarItem(@PathVariable @Positive Long pedidoId, @RequestParam @NotNull Long pratoId, @RequestParam @Positive Integer quantity) throws NotFoundException, BadRequestException {
        return pedidoService.adicionarItem(pedidoId, pratoId, quantity);
    }

    @PutMapping("/{pedidoId}/status")
    @ResponseStatus(HttpStatus.OK)
    public PedidoDTO atualizarStatusPedido(@PathVariable @Positive Long pedidoId, @RequestParam PedidoStatus novoStatus) throws NotFoundException, BadRequestException {
        return pedidoService.atualizarStatusPedido(pedidoId, novoStatus);
    }

    @PutMapping("/{pedidoId}/finalizar")
    @ResponseStatus(HttpStatus.OK)
    public PedidoDTO finalizarPedido(@PathVariable @Positive Long pedidoId) throws NotFoundException, BadRequestException {
        return pedidoService.finalizarPedido(pedidoId);
    }

    @PutMapping("/{pedidoId}/cancelar")
    @ResponseStatus(HttpStatus.OK)
    public PedidoDTO cancelarPedido(@PathVariable @Positive Long pedidoId) throws NotFoundException, BadRequestException {
        return pedidoService.cancelarPedido(pedidoId);
    }
}
