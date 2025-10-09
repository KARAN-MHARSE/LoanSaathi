package com.aurionpro.loanapp.service;

import java.util.Map;

import com.aurionpro.loanapp.dto.payment.PaymentRequestDto;
import com.aurionpro.loanapp.dto.payment.PaymentResponseDto;
import com.stripe.exception.StripeException;
import com.stripe.exception.StripeException;

public interface IPaymentService {
    PaymentResponseDto createPaymentIntent(PaymentRequestDto paymentRequest) throws StripeException;
}
