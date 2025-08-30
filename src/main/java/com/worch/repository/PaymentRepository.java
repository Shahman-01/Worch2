package com.worch.repository;

import com.worch.model.entity.Payment;
import com.worch.model.enums.PaymentStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    List<Payment> findAllByStatus(PaymentStatusEnum status);
}
