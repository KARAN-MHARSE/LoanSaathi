import { Component, OnInit } from '@angular/core';
import { CustomerService } from '../../services/customer.service';
import { CustomerDashboard } from '../../models/CustomerDashboard.mode';

@Component({
  selector: 'app-dashboard',
  standalone: false,
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class Dashboard implements OnInit{
  customerDetails!:CustomerDashboard;

  constructor(private customerService:CustomerService){};

  ngOnInit(): void {
    this.customerService.getCustomerDashboard().subscribe({
      next:(res:CustomerDashboard)=>{
        this.customerDetails = res;
        console.log(res)
      },
      error:(res)=>{
        console.log(res)
      }
    })
  }

}
