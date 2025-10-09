package com.aurionpro.loanapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aurionpro.loanapp.dto.payment.PaymentRequestDto;
import com.aurionpro.loanapp.dto.payment.PaymentResponseDto;
import com.aurionpro.loanapp.service.IPaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements IPaymentService {
	
	@Value("${stripe.secret.key}")
    private String secretKey;
	
	@PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }
	

	@Override
	public PaymentResponseDto createPaymentIntent(PaymentRequestDto paymentRequest) throws StripeException {
		PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
				.setAmount(paymentRequest.getAmount() * 100L)
				.setCurrency(paymentRequest.getCurrency())
				.addAllPaymentMethodType(List.of("card"))
				.build();
		
		PaymentIntent paymentIntent = PaymentIntent.create(params);
		
		String secretKey = paymentIntent.getClientSecret();
		
		PaymentResponseDto paymentResponseDto = new PaymentResponseDto(secretKey);
		
		
		return paymentResponseDto;
	}

}
