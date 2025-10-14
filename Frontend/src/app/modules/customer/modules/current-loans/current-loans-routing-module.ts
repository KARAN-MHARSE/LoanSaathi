import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MyLoans } from './pages/my-loans/my-loans';
import { LoanDetails } from './pages/loan-details/loan-details';
import { Payment } from './pages/payment/payment';

const routes: Routes = [
  {
      path:"",
      component:MyLoans
    },
     {
      path:"loans/:loanId",
      component:LoanDetails
    },
    {
    path:"loans/:loanId/pay",
    component:Payment
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CurrentLoansRoutingModule { }
