import { NgModule } from '@angular/core';
import {Routes, RouterModule, PreloadAllModules} from '@angular/router';
import {MainLayoutComponent} from './shared/components/main-layout/main-layout.component';
import {RequestPageComponent} from './request-page/request-page.component';
import {HomePageComponent} from './home-page/home-page.component';


const routes: Routes = [
  {
    //сначала мы загружаем MainLayoutComponent. Чтобы загрузить HomeAdvertiserComponent,
    //делаем редирект. pathMatch - путь должен полностью совпадать с родительским.
    path: '', component: MainLayoutComponent, children: [
      {path: '', redirectTo: '/', pathMatch: 'full'},
      {path: '', component: HomePageComponent},
      {path: 'request/:id', component: RequestPageComponent}
    ]
  },
  {
    //lazy loading для админского модуля
    path: 'cabinet',
    loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    preloadingStrategy: PreloadAllModules
  })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
