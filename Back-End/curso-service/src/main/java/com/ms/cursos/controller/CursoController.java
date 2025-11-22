package com.ms.cursos.controller;


import com.ms.commons.examen.entity.Examen;
import com.ms.commons.service.controller.CommonController;
import com.ms.cursos.dto.AlumnoDto;
import com.ms.cursos.entity.Curso;
import com.ms.cursos.entity.CursoAlumno;
import com.ms.cursos.services.CursoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/curso")
@Slf4j
public class CursoController extends CommonController<Curso, CursoService> {

    @Value("${config.balanceador.test}")
    private String balanceadorTest;

    @DeleteMapping("/eliminar-alumno/{id}")
    public ResponseEntity<?> eliminarCursoAlumnoPorId(@PathVariable Long id) {
        this.service.eliminarCursoAlumnoPorId(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Override
    public ResponseEntity<?> listar() {
        List<Curso> cursos = ((List<Curso>) this.service.findAll())
                .stream()
                .map(c -> {
                    c.getCursoAlumnos().forEach(ca -> {
                        AlumnoDto alumno = new AlumnoDto();
                        alumno.setId(ca.getAlumnoId());
                        c.addAlumno(alumno);
                    });
                    return c;
                }).collect(Collectors.toList());

        return ResponseEntity.ok().body(cursos);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<?> ver(@PathVariable Long id) {
        Optional<Curso> o = this.service.findById(id);
        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Curso curso = o.get();
        if (curso.getCursoAlumnos().isEmpty() == false) {
            List<Long> ids = curso.getCursoAlumnos().stream().map(ca -> ca.getAlumnoId()).collect(Collectors.toList());
            List<AlumnoDto> alumnos = (List<AlumnoDto>) this.service.obtenerAlumnosPorCurso(ids);

            curso.setAlumnos(alumnos);
        }

        return ResponseEntity.ok(curso);
    }


    @GetMapping("/pagina")
    @Override
    public ResponseEntity<?> listar(Pageable pageable) {

        Page<Curso> cursos = this.service.findAll(pageable).map(curso -> {
            curso.getCursoAlumnos().forEach(ca -> {
                curso.addAlumno(AlumnoDto.builder().id(ca.getId()).build());
            });
            return curso;
        });


        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/balanceador-test")
    public ResponseEntity<?> balanceadorTest() {

        Map<String, Object> response = new HashMap<>();
        response.put("balanceador", balanceadorTest);
        response.put("cursos", this.service.findAll());

        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable(name = "id") Long id) {
        if (result.hasErrors()) {
            return this.validar(result);
        }


        Optional<Curso> o = this.service.findById(id);
        if (!o.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Curso dbCurso = o.get();

        dbCurso.setNombre(curso.getNombre());
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
    }


    @PutMapping("/{id}/asignar-alumnos")
    public ResponseEntity<?> asignarAlumnos(@RequestBody List<AlumnoDto> alumnos, @PathVariable Long id) {
        log.info("Buscar curso con el id: " + id);
        Optional<Curso> optional = service.findById(id);

        if (optional.isEmpty()) {
            log.info("No existe el curso con el id: " + id);
            return ResponseEntity.notFound().build();
        }

        Curso dbCurso = optional.get();

        for (AlumnoDto alumno : alumnos) {

            CursoAlumno cursoAlumno = new CursoAlumno();
            cursoAlumno.setAlumnoId(alumno.getId());
            cursoAlumno.setCurso(dbCurso);

            dbCurso.addCursoAlumno(cursoAlumno);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));

    }

    @PutMapping("/{id}/eliminar-alumno")
    public ResponseEntity<?> eliminarAlumnos(@RequestBody AlumnoDto alumno, @PathVariable Long id) {
        log.info("Buscar curso con el id: " + id);
        Optional<Curso> optional = service.findById(id);

        if (optional.isEmpty()) {
            log.info("No existe el curso con el id: " + id);
            return ResponseEntity.notFound().build();
        }

        Curso dbCurso = optional.get();

        CursoAlumno cursoAlumno = new CursoAlumno();
        cursoAlumno.setAlumnoId(alumno.getId());

        log.info("alumno eliminado del curso: " + dbCurso.removeCursoAlumno(cursoAlumno));

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));

    }


    @GetMapping("/alumno/{id}")
    public ResponseEntity<?> buscarPorAlumnoId(@PathVariable(name = "id") Long id) {
        Curso curso = this.service.findCursoByAlumnoId(id);

        if (curso != null) {
            List<Long> examenesIds = (List<Long>) this.service.obtenerExamenesIdsConRespuestasAlumno(id);

            if (examenesIds != null && examenesIds.size() > 0) {

                List<Examen> examenes = curso.getExamenes().stream().map(examen -> {
                    if (examenesIds.contains(examen.getId())) {
                        examen.setRespondido(true);
                    }
                    return examen;
                }).collect(Collectors.toList());

                curso.setExamenes(examenes);
            }
        }


        return ResponseEntity.ok(curso);
    }

    @PutMapping("/{id}/asignar-examenes")
    public ResponseEntity<?> asignarExamenes(@RequestBody List<Examen> examenes, @PathVariable Long id) {
        log.info("Buscar curso con el id: " + id);
        Optional<Curso> optional = this.service.findById(id);

        if (optional.isEmpty()) {
            log.info("No existe el curso con el id: " + id);
            return ResponseEntity.notFound().build();
        }

        Curso dbCurso = optional.get();

        for (Examen examen : examenes) {
            dbCurso.addExamen(examen);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));

    }

    @PutMapping("/{id}/eliminar-examen")
    public ResponseEntity<?> eliminarExamen(@RequestBody Examen examen, @PathVariable Long id) {
        log.info("Buscar curso con el id: " + id);
        Optional<Curso> optional = this.service.findById(id);

        if (optional.isEmpty()) {
            log.info("No existe el curso con el id: " + id);
            return ResponseEntity.notFound().build();
        }

        Curso dbCurso = optional.get();

        log.info("Examen eliminado del curso: " + dbCurso.removeExamen(examen));

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));

    }
}
