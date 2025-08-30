package com.worch.service;

import com.worch.exceptions.PaymentNotFoundException;
import com.worch.model.dto.request.CreatePaymentRequest;
import com.worch.model.entity.Payment;
import com.worch.model.enums.PaymentStatusEnum;
import com.worch.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public Payment createPayment(CreatePaymentRequest request) {
        Payment payment = Payment.builder()
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .status(PaymentStatusEnum.PENDING)
                .description(request.getDescription())
                .clientEmail(request.getClientEmail())
                .metadata(request.getMetadata())
                .build();

        return paymentRepository.save(payment);
    }

    public Payment updateStatus(UUID paymentId, PaymentStatusEnum status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("No such Payment with id: " + paymentId));

        payment.setStatus(status);
        if (status == PaymentStatusEnum.PAID) {
            payment.setPaidAt(OffsetDateTime.now());
        } else if (status == PaymentStatusEnum.CANCELLED) {
            payment.setCancelledAt(OffsetDateTime.now());
        }

        return paymentRepository.save(payment);
    }

    public Payment getById(UUID id) {
        return paymentRepository.findById(id).orElseThrow(() -> new PaymentNotFoundException("No such Payment with id: " + id));
    }

    public List<Payment> getAllByStatus(PaymentStatusEnum status) {
        return paymentRepository.findAllByStatus(status);
    }

}
