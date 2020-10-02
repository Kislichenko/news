import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import { AdminLayoutComponent } from './shared/components/admin-layout/admin-layout.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { AdvertiserDashboardComponent } from './advertiser-dashboard/advertiser-dashboard.component';
import { CreateRequestComponent } from './create-request/create-request.component';
import { EditRequestComponent } from './edit-request/edit-request.component';

@NgModule({
  declarations: [
    AdminLayoutComponent,
    LoginPageComponent,
    AdvertiserDashboardComponent,
    CreateRequestComponent,
    EditRequestComponent
  ],
  imports: [
    CommonModule,
    //т.к. мы используем не корневой модуль, а мы используем дочерний модуль, к которому
    //в последствии будем применять lazy load.
    RouterModule.forChild([
      {
        path: '', component: AdminLayoutComponent, children: [
          {path: '', redirectTo: '/admin/login', pathMatch: 'full'},
          {path: 'login', component: LoginPageComponent},
          {path: 'dashboard', component: AdvertiserDashboardComponent},
          {path: 'create', component: CreateRequestComponent},
          {path: 'request/:id/edit', component:EditRequestComponent}

        ]
      }
    ])
  ],
  exports: [RouterModule],
})
export class AdminModule {

}
