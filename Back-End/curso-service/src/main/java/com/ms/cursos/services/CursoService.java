package com.ms.cursos.services;

import com.ms.commons.service.services.CommonService;
import com.ms.cursos.dto.AlumnoDto;
import com.ms.cursos.entity.Curso;

import java.util.List;


public interface CursoService extends CommonService<Curso> {

    public Curso findCursoByAlumnoId(Long id);

    public Iterable<Long> obtenerExamenesIdsConRespuestasAlumno(Long alumnoId);

    public Iterable<AlumnoDto> obtenerAlumnosPorCurso(List<Long> ids);

    public void eliminarCursoAlumnoPorId(Long id);
}
