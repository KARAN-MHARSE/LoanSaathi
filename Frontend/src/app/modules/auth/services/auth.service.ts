import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OtpModel } from '../../../core/model/Otp.model';
import { LoginResponse } from '../model/LoginResponse.model';
import { ForgotPasswordRequestModel } from '../model/ForgotPasswordRequest.model';
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  apiPath = "http://localhost:5000/api/v1/auth/";

  constructor(private http: HttpClient) { }

  sendValiateEmailOtp(email: string): Observable<any> {
    console.log(email)
    console.log(this.apiPath + "send-otp")
    const params = new HttpParams().set('email', email);
    console.log(params)
    return this.http.get(this.apiPath + "send-otp", { params })
  }

  validateEmailOtp(otpModel: OtpModel) {
    return this.http.post(this.apiPath + "validate-otp", otpModel);
  }

  register(form: any) {
    return this.http.post(this.apiPath + "register", form)
  }

  login(loginForm: any): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.apiPath + "login", loginForm)
  }

  sendForgotPasswordOtp(email:any){
    console.log(email.value)
    return this.http.post(
      this.apiPath+"forgot-password/send-otp",
      email.value,
      {
        headers:{'Content-Type':'text/plain'},
        responseType: 'text' as 'json'
      })
  }

  validateOtpAndResetPassword(data:ForgotPasswordRequestModel):Observable<boolean>{
    return this.http.post<boolean>(
      this.apiPath+"forgot-password/reset",
      data);
  }

  saveToken(res: LoginResponse) {
    if (res.token) {
      localStorage.setItem("token", res.token)

      const dataPayload = res.token?.split('.')[1]
      const data = JSON.parse(atob(dataPayload))
      console.log(data)

      let role = data.roles[0].authority;
      console.log(role)

      localStorage.setItem("role", role)
      localStorage.setItem("email", data.sub)
    }

  }
  getToken() {
    return localStorage.getItem("token")
  }

  isAuthenticated() {
    if(localStorage.getItem("role")) return true;
    return false;
  }

  getUserRole() {
    return localStorage.getItem("role")
  }
}


