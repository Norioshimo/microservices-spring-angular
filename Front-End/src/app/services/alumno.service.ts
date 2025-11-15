
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AlumnoService {

  private baseEndpoint = `http://localhost:8080/api/alumnos`;
  constructor(private http: HttpClient) { }
}
