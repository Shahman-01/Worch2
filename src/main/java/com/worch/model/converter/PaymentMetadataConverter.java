package com.worch.model.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.worch.model.dto.metadata.PaymentMetadata;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PaymentMetadataConverter implements AttributeConverter<PaymentMetadata, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(PaymentMetadata attribute) {
        try {
            if(attribute == null){
                return null;
            }else{
                return objectMapper.writeValueAsString(attribute);
            }
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error serializing metadata", e);
        }
    }

    @Override
    public PaymentMetadata convertToEntityAttribute(String dbData) {
        try {
            if(dbData == null){
                return null;
            }else{
                return objectMapper.readValue(dbData, PaymentMetadata.class);
            }
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error deserializing metadata", e);
        }
    }
}

