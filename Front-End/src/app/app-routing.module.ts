import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AlumnosComponent } from './components/alumnos/alumnos.component';
import { CursosComponent } from './components/cursos/cursos.component';
import { ExamenesComponent } from './components/examenes/examenes.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { AlumnosFormComponent } from './components/alumnos/alumnos-form.component';
import { CursosFormComponent } from './components/cursos/cursos-form.component';
import { ExamenesFormComponent } from './components/examenes/examenes-form.component';


const routes: Routes = [
  { path: 'alumnos', component: AlumnosComponent },
  { path: 'alumnos/form', component: AlumnosFormComponent },
  { path: 'alumnos/form/:id', component: AlumnosFormComponent },
  
  { path: 'cursos', component: CursosComponent },
  { path: 'cursos/form', component: CursosFormComponent },
  { path: 'cursos/form/:id', component: CursosFormComponent },

  { path: 'examenes', component: ExamenesComponent },
  { path: 'examenes/form', component: ExamenesFormComponent },
  { path: 'examenes/form/:id', component: ExamenesFormComponent },

  { path: '', pathMatch: 'full', redirectTo: 'cursos' },
  { path: '**', component: PageNotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
