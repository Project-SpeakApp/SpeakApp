package com.speakapp.userservice.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.speakapp.userservice.dtos.AppUserCreateDTO;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class AppUserCreateDTOSerializer implements Serializer<AppUserCreateDTO>, Deserializer<AppUserCreateDTO> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override
    public byte[] serialize(String topic, AppUserCreateDTO data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing AppUserCreateDTO", e);
        }
    }

    @Override
    public AppUserCreateDTO deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, AppUserCreateDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing AppUserCreateDTO", e);
        }
    }

    @Override
    public void close() {}
}

