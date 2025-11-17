import { Injectable } from '@angular/core';
import { CommonService } from './common.service';
import { Curso } from '../models';
import { HttpClient } from '@angular/common/http';
import { BASE_ENDPOINT } from '../config';

@Injectable({
  providedIn: 'root'
})
export class CursoService extends CommonService<Curso> {

  protected baseEndpoint = `${BASE_ENDPOINT}/curso`;

  constructor(http: HttpClient) {
    super(http);
  }

}