package com.ms.respuestas.services;

import com.ms.respuestas.clients.ExamenFeignClient;
import com.ms.respuestas.entity.Respuesta;
import com.ms.respuestas.repository.RespuestaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RespuestaServiceImpl implements RespuestaService {

    @Autowired
    private ExamenFeignClient examenFeignClient;

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Override
    public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {

        return this.respuestaRepository.saveAll(respuestas);
    }

    @Override
    public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId) {

        // Una forma de hacer. Recuperando por microservicio si es que no guarda en la base de mongo db
        //Examen examen = this.examenFeignClient.obtenerExamenPorId(examenId);// Buscar en el servicio de examen
        //List<Pregunta> preguntas = examen.getPreguntas();
        //List<Long> preguntasIds = preguntas.stream().map(p -> p.getId()).collect(Collectors.toList());
        //List<Respuesta> respuestas = (List<Respuesta>) this.respuestaRepository.findRespuestaByAlumnoByPreguntaIds(alumnoId, preguntasIds);// Buscar en la base de mongodb
        //respuestas = respuestas.stream().map(r -> {
        //    preguntas.forEach(p -> {
        //        if (p.getId() == r.getPreguntaId()) {
        //            r.setPregunta(p);
        //        }
        //     });
        //    return r;
        //}).collect(Collectors.toList());


        List<Respuesta> respuestas = (List<Respuesta>) this.respuestaRepository.findRespuestaByAlumnoByExamen(alumnoId, examenId);// Buscar en la base de mongodb
        return respuestas;
    }

    @Override
    public Iterable<Long> findExamenesIdsRespuestasByAlumno(Long alumnoId) {
        /*List<Respuesta> respuestasAlumno = (List<Respuesta>) this.respuestaRepository.findByAlumnoId(alumnoId);
        List<Long> examenIds = Collections.emptyList();
        if (respuestasAlumno.size() > 0) {
            List<Long> preguntaids = respuestasAlumno.stream().map(r -> r.getPreguntaId()).collect(Collectors.toList());
            examenIds = examenFeignClient.obtnerExamenesIdsPReguntasIdRespondidas(preguntaids);
        }*/
        log.info("Buscar findExamenesIdsRespuestasByAlumno");
        List<Respuesta> respuestasAlumno = (List<Respuesta>) this.respuestaRepository.findExamenesIdsRespuestasByAlumno(alumnoId);

        log.info("Buscar id de los examenes");
        List<Long> examenIds = respuestasAlumno.stream()
                .filter(r -> {
                    log.info(r.getPregunta().toString());
                    if (r.getPregunta().getExamen() != null) {
                        return true;
                    }
                    return false;
                })
                .map(r -> r.getPregunta().getExamen().getId()).distinct().collect(Collectors.toList());

        return examenIds;
    }

    @Override
    public Iterable<Respuesta> findByAlumnoId(Long alumnoId) {
        return this.respuestaRepository.findByAlumnoId(alumnoId);
    }
}
