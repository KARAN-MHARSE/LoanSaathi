import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'customer-sidebar',
  standalone: false,
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css'
})
export class Sidebar {
constructor(private router: Router) {}


  logout() {
    localStorage.clear();
    this.router.navigate(['/auth/login']);
  }
}
