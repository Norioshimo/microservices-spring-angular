package com.ms.alumno.repository;

import com.ms.alumno.entity.Alumno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno,Long> {

    @Query("select a from Alumno a where upper(a.nombre) like upper(concat('%', ?1, '%')) or upper(a.apellido) like upper(concat('%', ?1, '%'))")
    public List<Alumno>findByNombreOrApellido(String term);

    public Iterable<Alumno>findAllByOrderByIdAsc();

    public Page<Alumno> findAllByOrderByIdAsc(Pageable pageable);
}
