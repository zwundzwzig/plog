package com.sokuri.plog.domain.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.List;

@Converter
@Slf4j
public class StringListConverter implements AttributeConverter<List<String>, String> {

    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
            .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        return mapper.writeValueAsString(attribute);
    }

    @SneakyThrows
    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        try {
            return dbData != null ? mapper.readValue(dbData, new TypeReference<>() {}) : new ArrayList<>();
        } catch (Exception e) {
            log.error("Error while parsing JSON: {} :: {}", dbData, e);
            return new ArrayList<>();
        }
    }
}
