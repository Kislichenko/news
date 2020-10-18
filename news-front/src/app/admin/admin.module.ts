import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import { AdminLayoutComponent } from './shared/components/admin-layout/admin-layout.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { AdvertiserDashboardComponent } from './advertiser-dashboard/advertiser-dashboard.component';
import { CreateRequestComponent } from './create-request/create-request.component';
import { EditRequestComponent } from './edit-request/edit-request.component';
import { ReportDashboardComponent } from './report-dashboard/report-dashboard.component';
import { CreateReportComponent } from './create-report/create-report.component';
import { EditReportComponent } from './edit-report/edit-report.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AuthService} from './shared/services/auth.service';
import {SharedModule} from '../shared/shared.module';
import {AuthGuard} from './shared/services/auth.guard';
import { RegPageComponent } from './reg-page/reg-page.component';

@NgModule({
  declarations: [
    AdminLayoutComponent,
    LoginPageComponent,
    AdvertiserDashboardComponent,
    CreateRequestComponent,
    EditRequestComponent,
    ReportDashboardComponent,
    CreateReportComponent,
    EditReportComponent,
    RegPageComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    //т.к. мы используем не корневой модуль, а мы используем дочерний модуль, к которому
    //в последствии будем применять lazy load.
    RouterModule.forChild([
      {
        path: '', component: AdminLayoutComponent, children: [
          {path: '', redirectTo: '/admin/login', pathMatch: 'full'},
          {path: 'sign-up', component: RegPageComponent},
          {path: 'login', component: LoginPageComponent},
          {path: 'dashboard', component: AdvertiserDashboardComponent, canActivate: [AuthGuard], data: {role:'ROLE_USER'}},
          {path: 'create', component: CreateRequestComponent, canActivate: [AuthGuard], data: {role:'ROLE_AD_MANAGER'}},
          {path: 'request/:id/edit', component:EditRequestComponent, canActivate: [AuthGuard]},
          {path: 'report/:id/edit', component:EditReportComponent, canActivate: [AuthGuard]}
        ]
      }
    ])
  ],
  exports: [RouterModule],
  providers: [AuthService, AuthGuard]
})
export class AdminModule {

}
