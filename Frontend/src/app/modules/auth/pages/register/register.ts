import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { OtpModel } from '../../../../core/model/Otp.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.html',
  styleUrl: './register.css'
})
export class Register implements OnInit {
  registerForm!: FormGroup;
  emailOtpSent = false;
  emailOtpVerified = false;
  otpTimer = 0;
  timerSubscription: any;
  otpMessage = '';
  otpError = '';

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.registerForm = new FormGroup({
      firstName: new FormControl('', [Validators.required, Validators.minLength(2)]),
      lastName: new FormControl('', [Validators.required, Validators.minLength(2)]),
      email: new FormControl('', [Validators.required, Validators.email]),
      phoneNumber: new FormControl('', [Validators.required, Validators.pattern('^[0-9]{6,15}$')]),
      password: new FormControl('', [Validators.required, Validators.minLength(6)]),
      confirmPassword: new FormControl('', [Validators.required]),
      emailOtp: new FormControl("", [Validators.required])
    });
  }

  passwordMathValidator(form: FormGroup) {
    const password = form.get("password")?.value;
    const confirmPassword = form.get("confirmPassword")?.value;
    return password === confirmPassword ? null : { passwordMisMatch: true }
  }

  register() {
    this.authService.register(this.registerForm.value).subscribe({
      next: (res) => {
        console.log(res)
        alert("Registration successfull")
        this.router.navigate(["auth/login"])
      },
      error: (res) => {
        alert("Something went wrong during registration");
      }
    })
  }
  sendOtp() {
    this.authService.sendValiateEmailOtp(this.registerForm.value.email).subscribe({
      next: (res) => {
        this.emailOtpSent = true;
        this.emailOtpVerified = false
        this.otpError = ''
        this.startOtpTimer()
        alert("Otp successfully sent")
      },
      error: (res) => {
        alert("Something went wrong while sending otp")
      }
    })
  }


  verifyOtp() {
    const otpModel = new OtpModel(this.registerForm.value.emailOtp, this.registerForm.value.email);
    this.authService.validateEmailOtp(otpModel).subscribe({
      next: (res) => {
        if (res === true) {
          this.emailOtpVerified = true
          this.otpError = ''
          alert("OTP verified successfully!");
        } else {
          this.otpError = "Invalid OTP";
          this.emailOtpVerified = false;
        }

      },
      error: (res) => {
        this.otpError = "Invalid otp"
        this.emailOtpVerified = false;

      }
    })
  }

  startOtpTimer() {
    this.otpTimer = 60; // 1 minute
    const timer = setInterval(() => {
      this.otpTimer--;
      if (this.otpTimer <= 0) {
        clearInterval(timer);
        this.emailOtpSent = false;
        this.registerForm.controls['emailOtp'].reset();
        this.otpError = 'OTP expired. Please request a new one.';
      }
    }, 1000);
  }


  get f() { return this.registerForm.controls; }

}
