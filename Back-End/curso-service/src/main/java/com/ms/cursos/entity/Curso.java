package com.ms.cursos.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ms.commons.examen.entity.Examen;
import com.ms.cursos.dto.AlumnoDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cursos")
@Getter
@Setter
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nombre;

    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Examen> examenes;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"curso"})
    private List<CursoAlumno>cursoAlumnos;

    @Transient
    private List<AlumnoDto> alumnos;

    @PrePersist
    public void prePersist() {
        this.createAt = new Date();
    }

    public Curso() {
        this.alumnos = new ArrayList<>();
        this.examenes = new ArrayList<>();
        this.cursoAlumnos = new ArrayList<>();
    }

    public boolean addAlumno(AlumnoDto alumno) {
        return this.alumnos.add(alumno);
    }

    public boolean removeAlumno(AlumnoDto alumno) {
        return this.alumnos.remove(alumno);
    }

    public boolean addExamen(Examen examen){
        return this.examenes.add(examen);
    }

    public boolean removeExamen(Examen examen){
        return this.examenes.remove(examen);
    }

    public boolean addCursoAlumno(CursoAlumno cursoAlumno){
        return this.cursoAlumnos.add(cursoAlumno);
    }

    public boolean removeCursoAlumno(CursoAlumno cursoAlumno){
        return this.cursoAlumnos.remove(cursoAlumno);
    }
}
