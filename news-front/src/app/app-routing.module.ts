import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {MainLayoutComponent} from './shared/components/main-layout/main-layout.component';
import {HomeAdvertiserComponent} from './home-advertiser/home-advertiser.component';
import {RequestPageComponent} from './request-page/request-page.component';


const routes: Routes = [
  {
    //сначала мы загружаем MainLayoutComponent. Чтобы загрузить HomeAdvertiserComponent,
    //делаем редирект. pathMatch - путь должен полностью совпадать с родительским.
    path: '', component: MainLayoutComponent, children: [
      {path: '', redirectTo: '/', pathMatch: 'full'},
      {path: '', component: HomeAdvertiserComponent},
      {path: 'request/:id', component: RequestPageComponent}

    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
