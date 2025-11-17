package com.ms.commons.examen.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "examenes")
@Getter
@Setter
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id"})
public class Examen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 4, max = 30)
    private String nombre;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_at")
    private Date createAt;

    @JsonIgnoreProperties(value = {"examen"}, allowSetters = true)
    @OneToMany(mappedBy = "examen", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pregunta> preguntas;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Asignatura asignaturaPadre;


    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Asignatura asignaturaHija;

    @Transient
    private boolean respondido;

    public Examen() {
        this.preguntas = new ArrayList<>();
    }


    @PrePersist
    public void prePersist() {
        this.createAt = new Date();
    }


    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas.clear();
        preguntas.forEach(this::addPregunta);
    }

    public boolean addPregunta(Pregunta pregunta) {
        pregunta.setExamen(this);
        return this.preguntas.add(pregunta);
    }

    public boolean removePregunta(Pregunta pregunta) {
        pregunta.setExamen(null);
        return this.preguntas.remove(pregunta);
    }

    public void removePreguntas(List<Pregunta> preguntas) {
        if (preguntas != null) {
            preguntas.forEach(this::removePregunta);
        }
    }


    @Override
    public String toString() {
        return "Examen{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", createAt=" + createAt +
                ", preguntas=" + preguntas +
                '}';
    }
}
