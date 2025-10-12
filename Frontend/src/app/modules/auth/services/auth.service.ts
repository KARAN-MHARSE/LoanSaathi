import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OtpModel } from '../../../core/model/Otp.model';
import { LoginResponse } from '../model/LoginResponse.model';
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
    return this.http.post(this.apiPath + "validate-otp",otpModel);
  }

  register(form:any){
    return this.http.post(this.apiPath+"register",form)
  }

  login(loginForm:any):Observable<LoginResponse>{
    return this.http.post<LoginResponse>(this.apiPath+"login",loginForm)
  }
}


