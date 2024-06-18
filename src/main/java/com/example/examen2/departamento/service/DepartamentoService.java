package com.example.examen2.departamento.service;

import com.example.examen2.departamento.model.Departamento;
import com.example.examen2.departamento.model.DepartamentoDTO;
import com.example.examen2.departamento.repository.DepartamentoRepository;
import com.example.examen2.docente.repository.DocenteRepository;
import com.example.examen2.docente.model.Docente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    public List<DepartamentoDTO> findAll() {
        return departamentoRepository.findAll().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public DepartamentoDTO findById(Long id) {
        Departamento departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Departamento not found with id: " + id));
        return mapEntityToDto(departamento);
    }

    @Transactional
    public DepartamentoDTO createDepartamento(DepartamentoDTO departamentoDTO) {
        Departamento departamento = mapDtoToEntity(departamentoDTO);
        return mapEntityToDto(departamentoRepository.save(departamento));
    }

    @Transactional
    public DepartamentoDTO updateDepartamento(Long id, DepartamentoDTO departamentoDTO) {
        Departamento existingDepartamento = departamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Departamento not found with id: " + id));
        updateEntityFromDto(existingDepartamento, departamentoDTO);
        return mapEntityToDto(departamentoRepository.save(existingDepartamento));
    }

    @Transactional
    public void deleteDepartamento(Long id) {
        Departamento departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Departamento not found with id: " + id));
        departamentoRepository.delete(departamento);
    }

    private Departamento mapDtoToEntity(DepartamentoDTO dto) {
        Departamento departamento = new Departamento();
        departamento.setNombre(dto.getNombre());
        if (dto.getDocenteIds() != null) {
            departamento.setDocentes(dto.getDocenteIds().stream()
                    .map(id -> docenteRepository.findById(id)
                            .orElseThrow(() -> new IllegalArgumentException("Invalid docenteId: " + id)))
                    .collect(Collectors.toSet()));
        }
        return departamento;
    }

    private void updateEntityFromDto(Departamento departamento, DepartamentoDTO dto) {
        departamento.setNombre(dto.getNombre());
        if (dto.getDocenteIds() != null) {
            departamento.setDocentes(dto.getDocenteIds().stream()
                    .map(id -> docenteRepository.findById(id)
                            .orElseThrow(() -> new IllegalArgumentException("Invalid docenteId: " + id)))
                    .collect(Collectors.toSet()));
        }
    }

    private DepartamentoDTO mapEntityToDto(Departamento departamento) {
        DepartamentoDTO dto = new DepartamentoDTO();
        dto.setId(departamento.getId());
        dto.setNombre(departamento.getNombre());
        dto.setDocenteIds(departamento.getDocentes().stream()
                .map(Docente::getId)
                .collect(Collectors.toSet()));
        return dto;
    }
}
