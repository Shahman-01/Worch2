package com.worch.model.dto.request;

import com.worch.model.dto.metadata.PaymentMetadata;
import com.worch.model.enums.CurrencyEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentRequest {

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    private CurrencyEnum currency;

    private String description;

    private String clientEmail;

    private PaymentMetadata metadata;
}
