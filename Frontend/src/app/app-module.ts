import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { App } from './app';
import { AppRoutingModule } from './app-routing-module';
import { Footer } from './layout/footer/footer';
import { Header } from './layout/header/header';
import { Sidebar } from './layout/sidebar/sidebar';
import { AboutUs } from './pages/about-us/about-us';
import { ContactUs } from './pages/contact-us/contact-us';
import { Home } from './pages/home/home';
import { LoanSchems } from './pages/loan-schems/loan-schems';
import { NotFound } from './pages/not-found/not-found';
import { Unauthorized } from './pages/unauthorized/unauthorized';
import { AuthInterceptor } from './shared/interceptor/auth.interceptor-interceptor';
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
    LoanSchems,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule
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
