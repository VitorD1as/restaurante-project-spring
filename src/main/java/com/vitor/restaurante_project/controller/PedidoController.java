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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoDTO> criarPedido(){
        PedidoDTO pedido = pedidoService.criarPedido();
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    @PostMapping("/{pedidoId}/itens")
    public ResponseEntity<PedidoDTO> adicionarItem(@PathVariable @Positive Long pedidoId, @RequestParam @NotNull Long pratoId, @RequestParam @Positive Integer quantity) throws NotFoundException, BadRequestException {
        PedidoDTO pedido = pedidoService.adicionarItem(pedidoId, pratoId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    @GetMapping("/{pedidoId}")
    public ResponseEntity<PedidoDTO> buscarPorId(@PathVariable @Positive Long pedidoId) throws NotFoundException {
        PedidoDTO pedidoDTO = pedidoService.buscarPorId(pedidoId);
        return ResponseEntity.ok(pedidoDTO);
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listarPedidos() throws NotFoundException {
        List<PedidoDTO> pedidosDTO = pedidoService.listarPedidos();
        return ResponseEntity.ok(pedidosDTO);
    }

    @GetMapping("/status")
    public ResponseEntity<List<PedidoDTO>> buscarPorStatus(@RequestParam PedidoStatus pedidoStatus){
        List<PedidoDTO> pedidoDTO = pedidoService.buscarPorStatus(pedidoStatus);
        return ResponseEntity.ok(pedidoDTO);
    }

    @PutMapping("/{pedidoId}/status")
    public ResponseEntity<PedidoDTO> atualizarStatusPedido(@PathVariable @Positive Long pedidoId, @RequestParam PedidoStatus novoStatus) throws NotFoundException, BadRequestException {
        PedidoDTO pedido = pedidoService.atualizarStatusPedido(pedidoId, novoStatus);
        return ResponseEntity.status(HttpStatus.OK).body(pedido);
    }

    @PutMapping("/{pedidoId}/finalizar")
    public ResponseEntity<PedidoDTO> finalizarPedido(@PathVariable @Positive Long pedidoId) throws NotFoundException, BadRequestException {
        PedidoDTO pedido = pedidoService.finalizarPedido(pedidoId);
        return ResponseEntity.status(HttpStatus.OK).body(pedido);
    }

    @PutMapping("/{pedidoId}/cancelar")
    public ResponseEntity<PedidoDTO> cancelarPedido(@PathVariable @Positive Long pedidoId) throws NotFoundException, BadRequestException {
        PedidoDTO pedido = pedidoService.cancelarPedido(pedidoId);
        return ResponseEntity.status(HttpStatus.OK).body(pedido);
    }

    @DeleteMapping("/{pedidoId}")
    public ResponseEntity<Void> deletarPedido(@PathVariable @Positive Long pedidoId) throws NotFoundException, BadRequestException {
        pedidoService.deletarPedido(pedidoId);
        return ResponseEntity.noContent().build();
    }
}
