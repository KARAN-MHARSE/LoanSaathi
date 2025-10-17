import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PaymentRoutingModule } from './payment-routing-module';
import { MyPayments } from './my-payments/my-payments';
import { MakePayment } from './make-payment/make-payment';
import { PaymentHistory } from './payment-history/payment-history';
import { Penalties } from './penalties/penalties';


@NgModule({
  declarations: [
    MyPayments,
    MakePayment,
    PaymentHistory,
    Penalties
  ],
  imports: [
    CommonModule,
    PaymentRoutingModule
  ]
})
export class PaymentModule { }
