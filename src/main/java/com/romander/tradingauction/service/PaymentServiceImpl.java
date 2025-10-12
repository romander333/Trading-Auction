package com.romander.tradingauction.service;

import com.romander.tradingauction.dto.payment.PaymentResponseDto;
import com.romander.tradingauction.exception.EntityNotFoundException;
import com.romander.tradingauction.exception.ProductNotForSaleException;
import com.romander.tradingauction.mapper.PaymentMapper;
import com.romander.tradingauction.model.Payment;
import com.romander.tradingauction.model.Product;
import com.romander.tradingauction.repository.PaymentRepository;
import com.romander.tradingauction.repository.ProductRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final ProductRepository productRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    @Transactional
    public PaymentResponseDto pay(Long id) throws StripeException {

        Product product = getProduct(id);
        long amount = product.getPrice().multiply(BigDecimal.valueOf(100)).longValue();
        if (!product.getIsForSale()) {
            throw new ProductNotForSaleException("Product by id " + id + " is not for sale");
        }
        checkPaymentStatus(id);
        SessionCreateParams params = new SessionCreateParams.Builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/api/payments/success")
                .setCancelUrl("http://localhost:8080/api/payments/cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("usd")
                                                .setUnitAmount(amount)
                                                .setProductData(
                                                        SessionCreateParams.LineItem
                                                                .PriceData
                                                                .ProductData.builder()
                                                                .setName("Test Product")
                                                                .build()
                                                )
                                                .build()
                                )
                                .setQuantity(1L)
                                .build()
                )
                .build();

        Session session = Session.create(params);
        Payment payment = buildPayment(product, session, amount);
        paymentRepository.save(payment);
        return paymentMapper.toDto(payment);
    }

    @Override
    @Transactional
    public void handleSuccessfulPayment(Session session) {
        String sessionId = session.getId();

        Payment payment = paymentRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Payment not found by session id: " + sessionId));

        payment.setStatus(Payment.Status.PAID);
        paymentRepository.save(payment);
    }

//    public void notifyPaymentSuccess() {
//        notificationService.sendMessage("A customer has successfully completed a payment.");
//    }

//    public void notifyPaymentCancelled() {
//        notificationService.sendMessage(String.format("""
//            Payment was cancelled
//            Time: %s
//                """, LocalDateTime.now()));
//    }

    private Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proposal not found by id: " + id));
    }

    private Payment buildPayment(Product product, Session session, Long amount) {
        Payment payment = new Payment();
        payment.setProduct(product);
        payment.setAmount(BigDecimal.valueOf(amount));
        payment.setStatus(Payment.Status.PENDING);
        payment.setSessionId(session.getId());
        payment.setSessionUrl(session.getUrl());
        return payment;
    }

    private void checkPaymentStatus(Long id) {
        Optional<Payment> existingPayment = paymentRepository.findByProductIdAndStatusIn(
                id,
                List.of(Payment.Status.PENDING, Payment.Status.PAID)
        );

        if (existingPayment.isPresent()) {
            throw new IllegalStateException(
                    "Payment for product with ID: " + id + " is already active or completed."
            );
        }
    }
}
