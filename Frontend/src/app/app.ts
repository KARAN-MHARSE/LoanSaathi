import { Component, signal } from '@angular/core';
import {  Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  standalone: false,
  styleUrl: './app.css'
})
export class App {
  constructor(private router:Router){
    console.log(this.router.url)
  }
  protected readonly title = signal('Frontend');
}
