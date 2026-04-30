package com.vitor.restaurante_project.controller;

import com.vitor.restaurante_project.database.model.PratoEntity;
import com.vitor.restaurante_project.dto.PratoDTO;
import com.vitor.restaurante_project.exception.NotFoundException;
import com.vitor.restaurante_project.service.PratoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/pratos")
@RequiredArgsConstructor
public class PratoController {

    private final PratoService pratoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarPrato(@Valid @RequestBody PratoDTO pratoDTO) {
        pratoService.criarPrato(pratoDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PratoDTO> listarTodos() {
        return pratoService.listarPratos();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PratoDTO procurarPorId(@PathVariable @NotNull Long id) throws NotFoundException {
        return pratoService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PratoDTO atualizarPrato(
            @PathVariable @NotNull Long id,
            @Valid @RequestBody PratoDTO pratoDTO
    ) throws NotFoundException {
        return pratoService.atualizarPrato(id, pratoDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarPrato(@PathVariable @NotNull Long id) throws NotFoundException {
        pratoService.deletarPrato(id);
    }
}