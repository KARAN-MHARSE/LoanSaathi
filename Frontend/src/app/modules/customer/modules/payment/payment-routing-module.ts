import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MyPayments } from './my-payments/my-payments';
import { MakePayment } from './make-payment/make-payment';
import { PaymentHistory } from './payment-history/payment-history';
import { Penalties } from './penalties/penalties';

const routes: Routes = [
  {
    path: "",
    component: MyPayments
  },
  {
    path: "pay",
    component: MakePayment
  },
  {
    path: "history",
    component: PaymentHistory
  },
  {
    path: "penalties",
    component: Penalties
  }
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PaymentRoutingModule { }
