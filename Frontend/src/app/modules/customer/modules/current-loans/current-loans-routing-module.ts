import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MyLoans } from './pages/my-loans/my-loans';
import { LoanDetails } from './pages/loan-details/loan-details';

const routes: Routes = [
  {
      path:"",
      component:MyLoans
    },
     {
      path:":loanId",
      component:LoanDetails
    }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CurrentLoansRoutingModule { }
