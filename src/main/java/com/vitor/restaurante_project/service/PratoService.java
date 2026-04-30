package com.vitor.restaurante_project.service;

import com.vitor.restaurante_project.database.model.PratoEntity;
import com.vitor.restaurante_project.database.repository.PratoRepository;
import com.vitor.restaurante_project.dto.PratoDTO;
import com.vitor.restaurante_project.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PratoService {

    private final PratoRepository pratoRepository;

    public PratoDTO criarPrato(PratoDTO pratoDTO) {
        PratoEntity entity = PratoEntity.builder()
                .name(pratoDTO.getName())
                .price(pratoDTO.getPrice())
                .build();

        PratoEntity saved = pratoRepository.save(entity);

        pratoDTO.setId(saved.getId());
        return pratoDTO;
    }

    public List<PratoDTO> listarPratos() {
        return pratoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public PratoDTO buscarPorId(Long id) throws NotFoundException {
        PratoEntity entity = pratoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Prato não encontrado"));

        return toDTO(entity);
    }

    public PratoDTO atualizarPrato(Long id, PratoDTO pratoDTO) throws NotFoundException {
        PratoEntity entity = pratoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Prato não encontrado"));

        entity.setName(pratoDTO.getName());
        entity.setPrice(pratoDTO.getPrice());

        PratoEntity updated = pratoRepository.save(entity);

        return toDTO(updated);
    }

    public void deletarPrato(Long id) throws NotFoundException {
        PratoEntity entity = pratoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Prato não encontrado"));

        pratoRepository.delete(entity);
    }

    private PratoDTO toDTO(PratoEntity entity) {
        return PratoDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .build();
    }
}