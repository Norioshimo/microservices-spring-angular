package com.ms.respuestas.clients;


import com.ms.commons.examen.entity.Examen;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "examen-service")
public interface ExamenFeignClient {


    @GetMapping("/api/respuesta/{id}")
    public Examen obtenerExamenPorId(@PathVariable Long id);

    @GetMapping("/api/respuesta/respondidos-por-preguntas")
    public List<Long> obtnerExamenesIdsPReguntasIdRespondidas(List<Long> preguntaIdS);
}
