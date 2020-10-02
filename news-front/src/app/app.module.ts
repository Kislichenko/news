import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainLayoutComponent } from './shared/components/main-layout/main-layout.component';
import { HomeAdvertiserComponent } from './home-advertiser/home-advertiser.component';
import { RequestPageComponent } from './request-page/request-page.component';

@NgModule({
  declarations: [
    AppComponent,
    MainLayoutComponent,
    HomeAdvertiserComponent,
    RequestPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
