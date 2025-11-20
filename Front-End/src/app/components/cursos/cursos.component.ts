import { Component } from '@angular/core';
import { CommonListarComponent } from '../common-listar.component';
import { Curso } from 'src/app/models';
import { CursoService } from 'src/app/services/curso.service';

@Component({
  selector: 'app-cursos',
  templateUrl: './cursos.component.html',
  styleUrls: ['./cursos.component.css']
})
export class CursosComponent extends CommonListarComponent<Curso, CursoService> {

  
  constructor(service: CursoService) {
    super(service);
    this.titulo = 'Listado de Cursos';
    this.nombreModel = Curso.name;
  }


}
