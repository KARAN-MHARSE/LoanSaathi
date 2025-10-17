import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-forgot-password',
  standalone: false,
  templateUrl: './forgot-password.html',
  styleUrl: './forgot-password.css'
})
export class ForgotPassword {
  forgotForm: FormGroup;
  isOtpSent:boolean= false
  error:string=''
  loading = false

  constructor(private fb: FormBuilder, private authService:AuthService, private router:Router) {
    this.forgotForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      otp: [''],
      newPassword: [''],
      confirmPassword: [''],
    });
  }

  sendOtp() {
    this.error=''
    if (this.forgotForm.get('email')?.valid) {
          this.loading = true
      this.authService.sendForgotPasswordOtp(this.forgotForm.get("email")).subscribe({
        next:(res)=>{
          console.log(res)
          this.loading=false;
          this.isOtpSent=true;
        },
        error:(res)=>{
          this.error = res
                    this.loading=false;
        }
      })
      console.log('OTP sent to:', this.forgotForm.value.email);
    }else{
      this.error = "Invalid email id"
    }
  }

  onSubmit() {
    if (this.forgotForm.value.newPassword === this.forgotForm.value.confirmPassword) {
          this.loading = true
      this.authService.validateOtpAndResetPassword(this.forgotForm.value).subscribe({
        next:(res)=>{
          if(res){
            alert("Password successfully changed")
            this.router.navigate(['auth/login'])
            this.loading = false
            this.error=""
          }else{
            this.error="Wrong otp password"
            this.loading = false
          }
        },
        error:(res)=>{
          this.error ="Something went wrong"
          console.log(res)
          this.loading = false
        }
      })
      console.log('Password reset successfully!');
      // call backend API here
    } else {
      alert('Passwords do not match');
    }
  }
}


