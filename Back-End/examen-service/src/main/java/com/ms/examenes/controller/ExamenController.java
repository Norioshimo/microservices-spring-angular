package com.ms.examenes.controller;

import com.ms.commons.examen.entity.Examen;
import com.ms.commons.examen.entity.Pregunta;
import com.ms.commons.service.controller.CommonController;
import com.ms.examenes.services.ExamenService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/examen")
@Slf4j
public class ExamenController extends CommonController<Examen, ExamenService> {

    @GetMapping("/respondidos-por-preguntas")
    public ResponseEntity<?>obtnerExamenesIdsPReguntasIdRespondidas(@RequestParam List<Long> preguntaIdS){
        return ResponseEntity.ok().body(this.service.findExamenesIdsRespuestasByPreguntaIds(preguntaIdS));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Examen examen, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()) {
            return this.validar(result);
        }

        Optional<Examen> o = this.service.findById(id);
        if (!o.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Examen examenDb = o.get();
        examenDb.setNombre(examen.getNombre());

        List<Pregunta> listaEliminado = examenDb.getPreguntas().stream()
                .filter(pdb -> !examen.getPreguntas().contains(pdb))// Filtrar las pregutnas que no está en la lista recibido
                .collect(Collectors.toList());
        //.forEach(examenDb::removePregunta);// Remover de la lista para luego eliminar de la base de datos.

        examenDb.removePreguntas(listaEliminado);

        examenDb.setPreguntas(examen.getPreguntas());
        examenDb.setAsignaturaHija(examen.getAsignaturaHija());
        examenDb.setAsignaturaPadre(examen.getAsignaturaPadre());

        log.info("Examen: " + examenDb.toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(examenDb));
    }

    @GetMapping("/filtrar/{term}")
    public ResponseEntity<?> filtrar(@PathVariable String term) {
        return ResponseEntity.ok(this.service.findByNombre(term));
    }


    @GetMapping("/asignaturas")
    public ResponseEntity<?> listarAsignaturas() {
        return ResponseEntity.ok(this.service.findAllAsignaturas());
    }

}
