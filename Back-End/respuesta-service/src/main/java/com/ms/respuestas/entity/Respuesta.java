package com.ms.respuestas.entity;

import com.ms.commons.examen.entity.Pregunta;
import com.ms.respuestas.dto.AlumnoDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "respuestas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id"})
public class Respuesta {

    @Id
    private String id;

    private String texto;

    private AlumnoDto alumno;

    private Long alumnoId;

    private Pregunta pregunta;

    private Long preguntaId;
}
