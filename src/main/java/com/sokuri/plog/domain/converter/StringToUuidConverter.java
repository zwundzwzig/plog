package com.sokuri.plog.domain.converter;

import java.util.UUID;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StringToUuidConverter implements AttributeConverter<UUID, String> {
  @Override
  public String convertToDatabaseColumn(UUID uuid) {
    return uuid != null ? uuid.toString().replace("-", "") : null;
  }

  @Override
  public UUID convertToEntityAttribute(String uuid) {
    return uuid != null ? UUID.fromString(uuid) : null;
  }
}
