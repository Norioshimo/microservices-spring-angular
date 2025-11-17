package com.ms.commons.examen.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "asignaturas")
@Getter
@Setter
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id"})
public class Asignatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;


    @JsonIgnoreProperties(value = {"hijos", "handler", "hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    private Asignatura padre;

    @JsonIgnoreProperties(value = {"padre", "handler", "hibernateLazyInitializer"}, allowSetters = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "padre", cascade = CascadeType.ALL)
    private List<Asignatura> hijos;

    public Asignatura() {
        this.hijos = new ArrayList<>();
    }
}
