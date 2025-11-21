import { Injectable } from '@angular/core';
import { BASE_ENDPOINT } from '../config';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Alumno, Examen, Respuesta } from '../models';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RespuestaService {

  private baseEndpoint = `${BASE_ENDPOINT}/respuesta`;

  private cabeceras: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient) { }

  crear(respuestas: Respuesta[]): Observable<Respuesta[]> {
    return this.http.post<Respuesta[]>(`${this.baseEndpoint}`, respuestas, { headers: this.cabeceras });
  }

  obtenerRespuestasPorAlumnoPorExamen(alumno: Alumno, examen: Examen): Observable<Respuesta[]> {
    return this.http.get<Respuesta[]>(`${this.baseEndpoint}/alumno/${alumno.id}/examen/${examen.id}`);
  }
}
