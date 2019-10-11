package com.beehyv.case_study.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter
public class AddressFormatter implements AttributeConverter<Address, String> {
    @Override
    public String convertToDatabaseColumn(Address attribute) {
        try {
            return new ObjectMapper().writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Address convertToEntityAttribute(String dbData) {
        try {
            return ObjectMapperImpl.getObjectFromJson(dbData, Address.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
