package com.ms.cursos.services;

import com.ms.commons.service.services.CommonServiceImp;
import com.ms.cursos.clients.AlumnoFeignClient;
import com.ms.cursos.clients.RespuestaFeignClient;
import com.ms.cursos.dto.AlumnoDto;
import com.ms.cursos.entity.Curso;
import com.ms.cursos.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CursoServiceImpl extends CommonServiceImp<Curso, CursoRepository> implements CursoService {

    @Autowired
    private AlumnoFeignClient alumnoFeignClient;
    @Autowired
    private RespuestaFeignClient respuestaFeignClient;

    @Override
    @Transactional(readOnly = true)
    public Curso findCursoByAlumnoId(Long id) {
        return this.repository.findCursoByAlumnoId(id);
    }


    @Override
    public Iterable<Long> obtenerExamenesIdsConRespuestasAlumno(Long alumnoId) {
        return respuestaFeignClient.obtenerExamenesIdsConRespuestasAlumno(alumnoId);
    }

    @Override
    public Iterable<AlumnoDto> obtenerAlumnosPorCurso(List<Long> ids) {
        return this.alumnoFeignClient.obtenerAlumnosPorCurso(ids);
    }

    @Override
    @Transactional
    public void eliminarCursoAlumnoPorId(Long id) {
        this.repository.eliminarCursoAlumnoPorId(id);
    }

}
