package com.romander.tradingauction.repository;

import com.romander.tradingauction.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findBySessionId(String sessionId);

    Optional<Payment> findByProductIdAndStatusIn(Long productId, List<Payment.Status> statuses);
}
