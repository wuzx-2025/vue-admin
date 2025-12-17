package com.bl.ai.domain.menu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class RouteMetaConverter implements AttributeConverter<RouteMeta, String> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(RouteMeta attribute) {
        if (attribute == null) return null;
        try {
            return MAPPER.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert RouteMeta to JSON", e);
        }
    }

    @Override
    public RouteMeta convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) return null;
        try {
            return MAPPER.readValue(dbData, RouteMeta.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse RouteMeta JSON", e);
        }
    }
}
