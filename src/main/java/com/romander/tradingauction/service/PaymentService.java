package com.romander.tradingauction.service;

import com.romander.tradingauction.dto.payment.PaymentResponseDto;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

public interface PaymentService {

    PaymentResponseDto pay(Long id) throws StripeException;

    void handleSuccessfulPayment(Session session);

//    void notifyPaymentSuccess();
//
//    void notifyPaymentCancelled();
}
