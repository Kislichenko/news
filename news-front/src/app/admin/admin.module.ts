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

@NgModule({
  declarations: [
    AdminLayoutComponent,
    LoginPageComponent,
    AdvertiserDashboardComponent,
    CreateRequestComponent,
    EditRequestComponent,
    ReportDashboardComponent,
    CreateReportComponent,
    EditReportComponent
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
          {path: 'login', component: LoginPageComponent},
          {path: 'dashboard', component: AdvertiserDashboardComponent},
          {path: 'create', component: CreateRequestComponent},
          {path: 'request/:id/edit', component:EditRequestComponent},
          {path: 'report/:id/edit', component:EditReportComponent}
        ]
      }
    ])
  ],
  exports: [RouterModule],
  providers: [AuthService]
})
export class AdminModule {

}
