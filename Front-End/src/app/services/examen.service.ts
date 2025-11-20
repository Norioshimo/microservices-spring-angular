import { Injectable } from '@angular/core';
import { CommonService } from './common.service';
import { Asignatura, Examen } from '../models';
import { HttpClient } from '@angular/common/http';
import { BASE_ENDPOINT } from '../config';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ExamenService extends CommonService<Examen> {

  protected baseEndpoint = `${BASE_ENDPOINT}/examen`;

  constructor(http: HttpClient) {
    super(http);
  }

  public findAllAsignatura(): Observable<Asignatura[]> {
    return this.http.get<Asignatura[]>(`${this.baseEndpoint}/asignaturas`);
  }

  public filtrarPorNombre(nombre: string): Observable<Examen[]> {
    return this.http.get<Examen[]>(`${this.baseEndpoint}/filtrar/${nombre}`);
  }
}
