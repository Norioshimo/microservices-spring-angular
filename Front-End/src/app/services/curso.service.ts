import { Injectable } from '@angular/core';
import { CommonService } from './common.service';
import { Alumno, Curso, Examen } from '../models';
import { HttpClient } from '@angular/common/http';
import { BASE_ENDPOINT } from '../config';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CursoService extends CommonService<Curso> {

  protected baseEndpoint = `${BASE_ENDPOINT}/curso`;

  constructor(http: HttpClient) {
    super(http);
  }

  asignarAlumnos(curso: Curso, alumnos: Alumno[]): Observable<Curso> {
    return this.http.put<Curso>(`${this.baseEndpoint}/${curso.id}/asignar-alumnos`, alumnos,
      {
        headers: this.cabeceras
      });
  }

  eliminarAlumno(curso: Curso, alumno: Alumno): Observable<Curso> {
    return this.http.put<Curso>(`${this.baseEndpoint}/${curso.id}/eliminar-alumno`,
      alumno,
      {
        headers: this.cabeceras
      }
    )
  }

  asignarExamenes(curso: Curso, examenes: Examen[]): Observable<Curso> {
    return this.http.put<Curso>(`${this.baseEndpoint}/${curso.id}/asignar-examenes`, examenes,
      { headers: this.cabeceras }
    );
  }

  eliminarExamen(curso: Curso, examen: Examen): Observable<Curso> {
    return this.http.put<Curso>(`${this.baseEndpoint}/${curso.id}/eliminar-examen`, examen,
      { headers: this.cabeceras }
    );
  }
}