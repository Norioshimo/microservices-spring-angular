package com.ms.respuestas.controller;

import com.ms.respuestas.entity.Respuesta;
import com.ms.respuestas.services.RespuestaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/respuesta")
@Slf4j
public class RespuestaController {

    @Autowired
    private RespuestaService respuestaService;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Iterable<Respuesta> respuestas) {
        log.info("crear Respuesta");
        respuestas = ((List<Respuesta>) respuestas).stream().map(r -> {
            r.setAlumnoId(r.getAlumno().getId());
            r.setPreguntaId(r.getPregunta().getId());
            log.info("examen: "+r.getPregunta().getExamen());
            return r;
        }).collect(Collectors.toList());

        Iterable<Respuesta> respuestasDb = this.respuestaService.saveAll(respuestas);

        return ResponseEntity.status(HttpStatus.CREATED).body(respuestasDb);

    }

    @GetMapping("/alumno/{alumnoId}/examen/{examenId}")
    public ResponseEntity<?> obtenerRespuestasPorExamen(@PathVariable Long alumnoId, @PathVariable Long examenId) {
        log.info("/alumno/{alumnoId}/examen/{examenId}");
        Iterable<Respuesta> respuestas = this.respuestaService.findRespuestaByAlumnoByExamen(alumnoId, examenId);

        return ResponseEntity.ok(respuestas);
    }

    @GetMapping("/alumno/{alumnoId}/examenes-respondidos")
    public ResponseEntity<?> obtenerExamenesIdsConRespuestasAlumno(@PathVariable Long alumnoId) {
        Iterable<Long> examenIds = this.respuestaService.findExamenesIdsRespuestasByAlumno(alumnoId);


        return ResponseEntity.ok(examenIds);
    }
}
