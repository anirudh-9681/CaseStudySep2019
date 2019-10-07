package com.beehyv.case_study.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

//@Component
public class ObjectMapperImpl {
    public static <T> T getObjectFromJson (String json, Class<T> className) throws IOException {
        return new ObjectMapper().readValue(json,className);
    }
}
