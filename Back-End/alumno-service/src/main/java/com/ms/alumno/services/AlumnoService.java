package com.ms.alumno.services;


import com.ms.alumno.entity.Alumno;
import com.ms.commons.service.services.CommonService;

import java.util.List;

public interface AlumnoService extends CommonService<Alumno> {

    public List<Alumno> findByNombreOrApellido(String term);

    public Iterable<Alumno> findAllById(Iterable<Long> ids);

    public void eliminarCursoAlumnoPorId(Long id);
}
