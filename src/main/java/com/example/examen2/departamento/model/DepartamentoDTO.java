package com.example.examen2.departamento.model;

import java.util.Set;

public class DepartamentoDTO {
    private Long id;
    private String nombre;
    private Set<Long> docenteIds;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Long> getDocenteIds() {
        return docenteIds;
    }

    public void setDocenteIds(Set<Long> docenteIds) {
        this.docenteIds = docenteIds;
    }
}
