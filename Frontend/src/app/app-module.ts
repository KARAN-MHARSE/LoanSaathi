import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { Header } from './layout/header/header';
import { Sidebar } from './layout/sidebar/sidebar';
import { Footer } from './layout/footer/footer';
import { Home } from './home/home';
import { AboutUs } from './pages/about-us/about-us';
import { NotFound } from './pages/not-found/not-found';
import { ContactUs } from './pages/contact-us/contact-us';
@NgModule({
  declarations: [
    App,
    Header,
    Sidebar,
    Footer,
    Home,
    AboutUs,
    NotFound,
    ContactUs
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [
    provideBrowserGlobalErrorListeners()
  ],
  bootstrap: [App]
})
export class AppModule { }
