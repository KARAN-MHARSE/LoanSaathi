import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// --- Imports ---
// 1. Only import components that are directly routed here.
//    Imports like NewLoanApplication, MyLoans etc., belong in their own
//    lazy-loaded routing modules (e.g., loan-application-routing.module.ts).
import { CustomerLayout } from './customer-layout/customer-layout';
import { Dashboard } from './pages/dashboard/dashboard';
import { Profile } from './pages/profile/profile';

const routes: Routes = [
  {
    path: "",
    component: CustomerLayout,
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },

      {
        path: "dashboard",
        component: Dashboard
      },
      {
        path: "profile",
        component: Profile
      },
      {
        path: "loans",
        loadChildren: () => import('./modules/current-loans/current-loans-module').then(m => m.CurrentLoansModule)
      },
      {
        path: "applications",
        loadChildren: () => import('./modules/loan-application/loan-application-module').then(m => m.LoanApplicationModule)
      },
      {
        path: "payments",
        loadChildren: () => import('./modules/payment/payment-module').then(m => m.PaymentModule)
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomerRoutingModule { }