package com.sokuri.plog.domain.utils;

import java.util.UUID;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StringToUuidConverter implements AttributeConverter<UUID, String> {

  @Override
  public String convertToDatabaseColumn(UUID uuid) {
    return uuid != null ? uuid.toString().replace("-", "") : null;
  }

  @Override
  public UUID convertToEntityAttribute(String uuid) {
    return uuid != null ? UUID.fromString(
            uuid.replaceAll("(.{8})(.{4})(.{4})(.{4})(.{12})", "$1-$2-$3-$4-$5")
    ) : null;
  }
}
