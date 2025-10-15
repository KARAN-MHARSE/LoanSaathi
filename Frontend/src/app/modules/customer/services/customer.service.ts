import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CustomerDashboard } from '../models/CustomerDashboard.mode';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  baseUrl="http://localhost:5000/api/v1/customers"

  constructor(private http:HttpClient){}

  getCustomerDashboard():Observable<CustomerDashboard>{
    return this.http.get<CustomerDashboard>(this.baseUrl);
  }


  
}
