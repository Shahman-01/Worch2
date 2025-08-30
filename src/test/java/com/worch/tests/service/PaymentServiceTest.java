package com.worch.tests.service;

import com.worch.exceptions.PaymentNotFoundException;
import com.worch.model.dto.metadata.PaymentMetadata;
import com.worch.model.dto.request.CreatePaymentRequest;
import com.worch.model.entity.Payment;
import com.worch.model.enums.CurrencyEnum;
import com.worch.model.enums.PaymentStatusEnum;
import com.worch.repository.PaymentRepository;
import com.worch.service.PaymentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тест сервиса платежей")
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    private final UUID paymentId = UUID.randomUUID();

    private CreatePaymentRequest getSampleRequest() {
        return CreatePaymentRequest.builder()
                .amount(BigDecimal.valueOf(100.50))
                .currency(CurrencyEnum.RUB)
                .description("Test payment")
                .clientEmail("test@example.com")
                .metadata(new PaymentMetadata())
                .build();
    }

    private Payment getSamplePayment() {
        return Payment.builder()
                .id(paymentId)
                .amount(BigDecimal.valueOf(100.50))
                .currency(CurrencyEnum.RUB)
                .status(PaymentStatusEnum.PENDING)
                .description("Test payment")
                .clientEmail("test@example.com")
                .metadata(new PaymentMetadata())
                .createdAt(OffsetDateTime.now())
                .build();
    }

    @Test
    void createPayment_shouldSavePaymentCorrectly() {
        CreatePaymentRequest request = getSampleRequest();
        Payment expected = getSamplePayment();

        when(paymentRepository.save(any(Payment.class))).thenReturn(expected);

        Payment result = paymentService.createPayment(request);

        assertNotNull(result);
        assertEquals(expected.getAmount(), result.getAmount());
        assertEquals(PaymentStatusEnum.PENDING, result.getStatus());

        verify(paymentRepository).save(any(Payment.class));
    }

    @Test
    void updateStatus_shouldSetPaidAtIfStatusPaid() {
        Payment existing = getSamplePayment();
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(existing));
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment updated = paymentService.updateStatus(paymentId, PaymentStatusEnum.PAID);

        assertEquals(PaymentStatusEnum.PAID, updated.getStatus());
        assertNotNull(updated.getPaidAt());

        verify(paymentRepository).save(existing);
    }

    @Test
    void updateStatus_shouldSetCancelledAtIfStatusCancelled() {
        Payment existing = getSamplePayment();
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(existing));
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment updated = paymentService.updateStatus(paymentId, PaymentStatusEnum.CANCELLED);

        assertEquals(PaymentStatusEnum.CANCELLED, updated.getStatus());
        assertNotNull(updated.getCancelledAt());

        verify(paymentRepository).save(existing);
    }

    @Test
    void updateStatus_shouldThrowIfNotFound() {
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());

        assertThrows(PaymentNotFoundException.class, () -> paymentService.updateStatus(paymentId, PaymentStatusEnum.PAID));

        verify(paymentRepository, never()).save(any());
    }

    @Test
    void getById_shouldReturnPayment() {
        Payment existing = getSamplePayment();
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(existing));

        Payment result = paymentService.getById(paymentId);

        assertEquals(paymentId, result.getId());
    }

    @Test
    void getAllByStatus_shouldReturnList() {
        List<Payment> list = List.of(getSamplePayment());
        when(paymentRepository.findAllByStatus(PaymentStatusEnum.PENDING)).thenReturn(list);

        List<Payment> result = paymentService.getAllByStatus(PaymentStatusEnum.PENDING);

        assertEquals(1, result.size());
        assertEquals(PaymentStatusEnum.PENDING, result.get(0).getStatus());
    }
}

