package com.example.examen2.programacionacademica.model;

import com.example.examen2.docente.model.Docente;
import com.example.examen2.materia.model.Materia;
import com.example.examen2.modulo.model.Aula;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProgramacionAcademica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "materia_id")
    private Materia materia;

    @ManyToOne
    @JoinColumn(name = "aula_id")
    private Aula aula;

    @ManyToMany
    @JoinTable(
            name = "programacion_academica_sesion_clase",
            joinColumns = @JoinColumn(name = "programacion_academica_id"),
            inverseJoinColumns = @JoinColumn(name = "sesion_clase_id")
    )
    private Set<SesionClase> sesiones = new HashSet<>();

    @ManyToMany(mappedBy = "programacionesAcademicas")
    @JsonIgnore
    private Set<Docente> docentes = new HashSet<>();

    private String grupo; // Nuevo atributo

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public Set<SesionClase> getSesiones() {
        return sesiones;
    }

    public void setSesiones(Set<SesionClase> sesiones) {
        this.sesiones = sesiones;
    }

    public Set<Docente> getDocentes() {
        return docentes;
    }

    public void setDocentes(Set<Docente> docentes) {
        this.docentes = docentes;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }
}
