package com.ms.alumno.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "curso-service")
public interface CursoFeignClient {

    @DeleteMapping("api/curso/eliminar-alumno/{id}")
    public void eliminarCursoAlumnoPorId(@PathVariable Long id);
}
