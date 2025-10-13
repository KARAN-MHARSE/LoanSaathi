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
import { LoanProduct } from './pages/loan-product/loan-product';
import { AuthInterceptor } from './shared/interceptor/auth.interceptor-interceptor';
import { Unauthorized } from './pages/unauthorized/unauthorized';
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
    LoanProduct,
    Unauthorized
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
