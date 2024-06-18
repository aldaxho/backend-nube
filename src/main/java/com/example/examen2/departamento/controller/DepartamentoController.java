package com.example.examen2.departamento.controller;

import com.example.examen2.departamento.model.DepartamentoDTO;
import com.example.examen2.departamento.service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departamentos/")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;

    @GetMapping
    public ResponseEntity<List<DepartamentoDTO>> getAllDepartamentos() {
        List<DepartamentoDTO> departamentos = departamentoService.findAll();
        return ResponseEntity.ok(departamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartamentoDTO> getDepartamentoById(@PathVariable Long id) {
        DepartamentoDTO departamento = departamentoService.findById(id);
        return ResponseEntity.ok(departamento);
    }

    @PostMapping
    public ResponseEntity<DepartamentoDTO> createDepartamento(@RequestBody DepartamentoDTO departamentoDTO) {
        DepartamentoDTO createdDepartamento = departamentoService.createDepartamento(departamentoDTO);
        return ResponseEntity.ok(createdDepartamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartamentoDTO> updateDepartamento(@PathVariable Long id, @RequestBody DepartamentoDTO departamentoDTO) {
        DepartamentoDTO updatedDepartamento = departamentoService.updateDepartamento(id, departamentoDTO);
        return ResponseEntity.ok(updatedDepartamento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartamento(@PathVariable Long id) {
        departamentoService.deleteDepartamento(id);
        return ResponseEntity.noContent().build();
    }
}
