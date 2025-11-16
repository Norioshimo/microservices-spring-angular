package com.ms.usuario.controller;

import com.ms.commons.alumno.entity.Alumno;
import com.ms.commons.service.controller.CommonController;
import com.ms.usuario.services.AlumnoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/usuario")
@Slf4j
public class AlumnoController extends CommonController<Alumno, AlumnoService> {


    @GetMapping("/uploads/img/{id}")
    public ResponseEntity<?> verFoto(@PathVariable Long id) {
        Optional<Alumno> o = this.service.findById(id);

        if (o.isEmpty() || o.get().getFoto() == null) {
            return ResponseEntity.notFound().build();
        }

        ByteArrayResource imagen = new ByteArrayResource(o.get().getFoto());

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);

    }

    @GetMapping("/alumnos-por-curso")
    public ResponseEntity<?> obtenerAlumnosPorCurso(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(this.service.findAllById(ids));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Alumno alumno, BindingResult result, @PathVariable(name = "id") Long id) {
        log.info("Buscar usuario con el id: " + id);
        log.info(alumno.toString());

        if (result.hasErrors()) {
            log.info("Datos de usuarios invalidos");
            return this.validar(result);
        }

        Optional<Alumno> optional = service.findById(id);

        if (optional.isEmpty()) {
            log.info("No existe el usuario con el id: " + id);
            return ResponseEntity.notFound().build();
        }

        Alumno alumnoDb = optional.get();

        alumnoDb.setNombre(alumno.getNombre());
        alumnoDb.setApellido(alumno.getApellido());
        alumnoDb.setEmail(alumno.getEmail());


        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(alumnoDb));
    }

    @GetMapping("/filtrar/{term}")
    public ResponseEntity<?> filtrar(@PathVariable(name = "term") String term) {
        return ResponseEntity.ok(this.service.findByNombreOrApellido(term));
    }


    @PostMapping("/crear-con-foto")
    public ResponseEntity<?> crearConFoto(@Valid Alumno alumno, BindingResult result, @RequestParam MultipartFile archivo) throws IOException {

        if (!archivo.isEmpty()) {
            alumno.setFoto(archivo.getBytes());
        }


        return super.crear(alumno, result);
    }

    @PutMapping("/editar-con-foto/{id}")
    public ResponseEntity<?> editarConFoto(@Valid Alumno alumno, BindingResult result, @PathVariable(name = "id") Long id
            , @RequestParam MultipartFile archivo) throws IOException {

        log.info("Buscar usuario con el id: " + id);

        if (result.hasErrors()) {
            return this.validar(result);
        }

        Optional<Alumno> optional = service.findById(id);

        if (optional.isEmpty()) {
            log.info("No existe el usuario con el id: " + id);
            return ResponseEntity.notFound().build();
        }

        Alumno alumnoDb = optional.get();

        alumnoDb.setNombre(alumno.getNombre());
        alumnoDb.setApellido(alumno.getApellido());
        alumnoDb.setEmail(alumno.getEmail());

        if (!archivo.isEmpty()) {
            alumnoDb.setFoto(archivo.getBytes());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(alumnoDb));
    }

}
