import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../modules/auth/services/auth.service';

@Component({
  selector: 'app-header',
  standalone: false,
  templateUrl: './header.html',
  styleUrl: './header.css'
})
export class Header implements OnInit{
  constructor(private authService:AuthService){}

  isAuthenticated = false;

  ngOnInit(): void {
    if(this.authService.isAuthenticated()){
      this.isAuthenticated= true
    }
  }
  menuOpen = false;

  toggleMenu() {
    this.menuOpen = !this.menuOpen;
  }

  closeMenu() {
    this.menuOpen = false;
  }
}
