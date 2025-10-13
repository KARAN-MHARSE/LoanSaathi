import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { LoginResponse } from '../../model/LoginResponse.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {
  loginForm!:FormGroup

  constructor(private authService:AuthService,private router:Router){
    this.loginForm = new FormGroup({
      email: new FormControl('',[Validators.required, Validators.minLength(5)]),
      password: new FormControl('', [Validators.required, Validators.minLength(6)])
    })
  }

  login(){
    this.authService.login(this.loginForm.value).subscribe({
      next:(res:LoginResponse)=>{
       this.authService.saveToken(res);

        if(res.role==="ROLE_CUSTOMER"){
          this.router.navigate(['/customer'])
        }else if(res.role==="ROLE_ADMIN"){
          this.router.navigate(['/admin'])
        }else if(res.role==="ROLE_OFFICER"){
          this.router.navigate(['/officer'])
        }
      },
      error:(res)=>{
        console.log(res)
      }
    })
  }
}
