import {NgModule} from '@angular/core';
import {CommonModule, DatePipe} from '@angular/common';
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
import {SharedModule} from '../shared/shared.module';
import {AuthGuard} from './shared/services/auth.guard';
import { RegPageComponent } from './reg-page/reg-page.component';
import {DpDatePickerModule} from 'ng2-date-picker';
import {SearchPipe} from './shared/pipes/search.pipe';
import { AlertComponent } from './shared/components/alert/alert.component';
import {AlertService} from './shared/services/alert.service';
import { AdmanagerDashboardComponent } from './admanager-dashboard/admanager-dashboard.component';

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
    RegPageComponent,
    SearchPipe,
    AlertComponent,
    AdmanagerDashboardComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    DpDatePickerModule,
    //т.к. мы используем не корневой модуль, а мы используем дочерний модуль, к которому
    //в последствии будем применять lazy load.
    RouterModule.forChild([
      {
        path: '', component: AdminLayoutComponent, children: [
          {path: '', redirectTo: '/cabinet/login', pathMatch: 'full'},
          {path: 'sign-up', component: RegPageComponent},
          {path: 'login', component: LoginPageComponent},
          //{path: 'admin/dashboard', component:EditReportComponent, canActivate: [AuthGuard], data: {role:'ROLE_ADMIN'}}
          {path: 'user/dashboard', component: AdvertiserDashboardComponent, canActivate: [AuthGuard], data: {role:'ROLE_USER'}},
          {path: 'user/create', component: CreateRequestComponent, canActivate: [AuthGuard], data: {role:'ROLE_USER'}},
          {path: 'user/request/:id/edit', component: EditRequestComponent, canActivate: [AuthGuard], data: {role:'ROLE_USER'}},
          {path: 'admanager/dashboard', component: AdmanagerDashboardComponent, canActivate: [AuthGuard], data: {role:'ROLE_AD_MANAGER'}},
          {path: 'admanager/request/:id/edit', component: EditRequestComponent, canActivate: [AuthGuard], data: {role:'ROLE_AD_MANAGER'}},
          //{path: 'reporter/dashboard', component:EditRequestComponent, canActivate: [AuthGuard], data: {role:'ROLE_REPORTER'}},
          {path: 'reporter/report/:id/edit', component:EditReportComponent, canActivate: [AuthGuard], data: {role:'ROLE_REPORTER'}},
          //{path: 'infomanager/dashboard', component:EditRequestComponent, canActivate: [AuthGuard], data: {role:'ROLE_INFO_MANAGER'}},
          //{path: 'infomanager/create', component:EditReportComponent, canActivate: [AuthGuard], data: {role:'ROLE_INFO_MANAGER'}},
          {path: 'infomanager/report/:id/edit', component:EditReportComponent, canActivate: [AuthGuard], data: {role:'ROLE_INFO_MANAGER'}}

        ]},

    ])
  ],
  exports: [RouterModule],
  providers: [AuthGuard, DatePipe, AlertService]
})
export class AdminModule {

}
