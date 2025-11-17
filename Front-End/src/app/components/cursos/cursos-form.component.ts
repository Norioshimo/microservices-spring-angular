import { Component } from '@angular/core';
import { CommonFormComponent } from '../common-form.component';
import { Curso } from 'src/app/models';
import { CursoService } from 'src/app/services/curso.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-curso-form',
  templateUrl: './cursos-form.component.html',
  styleUrls: ['./cursos-form.component.css']
})
export class CursosFormComponent extends CommonFormComponent<Curso, CursoService> {

  constructor(
    service: CursoService,
    router: Router,
    route: ActivatedRoute) {
    super(service, router, route);
    this.titulo = 'Crear Curso';
    this.model = new Curso();
    this.redirect = '/cursos';
    this.nombreModel = Curso.name;
  }

}
