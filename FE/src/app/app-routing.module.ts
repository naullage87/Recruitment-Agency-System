import { NgModule } from '@angular/core';
import {PreloadAllModules, RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {CvListComponent} from "./cv-list/cv-list.component";
import {CreateCvComponent} from "./create-cv/create-cv.component";

const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'create'},
  {path: 'login', pathMatch: 'full', component: LoginComponent},
  {path: 'home', pathMatch: 'full', component: CvListComponent},
  {path: 'create', pathMatch: 'full', component: CreateCvComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    preloadingStrategy: PreloadAllModules,
    scrollPositionRestoration: 'enabled',
    enableTracing: false
  })],

  exports: [RouterModule]
})
export class AppRoutingModule {
}
