import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { Header } from './layout/header/header';
import { Sidebar } from './layout/sidebar/sidebar';
import { Footer } from './layout/footer/footer';
import { AboutUs } from './pages/about-us/about-us';
import { NotFound } from './pages/not-found/not-found';
import { ContactUs } from './pages/contact-us/contact-us';
import { RouterModule } from '@angular/router';
import { Home } from './pages/home/home';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AuthInterceptor } from './shared/interceptor/auth.interceptor-interceptor';
import { Unauthorized } from './pages/unauthorized/unauthorized';
import { LoanSchems } from './pages/loan-schems/loan-schems';
@NgModule({
  declarations: [
    App,
    Header,
    Sidebar,
    Footer,
    Home,
    AboutUs,
    NotFound,
    ContactUs,
    Unauthorized,
    LoanSchems
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    RouterModule
  ],
  providers: [
    provideBrowserGlobalErrorListeners(),
    {
      provide:HTTP_INTERCEPTORS,
      useClass:AuthInterceptor,
      multi:true
    }
  ],
  bootstrap: [App]
})
export class AppModule { }
