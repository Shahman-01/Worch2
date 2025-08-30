package com.worch.model.enums.converter;

import com.worch.model.enums.ChoiceStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ChoiceStatusConverter implements AttributeConverter<ChoiceStatus, String> {

  @Override
  public String convertToDatabaseColumn(ChoiceStatus choiceStatus) {
    return (choiceStatus == null) ? null : choiceStatus.name();
  }

  @Override
  public ChoiceStatus convertToEntityAttribute(String dbData) {
    return (dbData == null) ? null : ChoiceStatus.fromString(dbData);
  }
}
