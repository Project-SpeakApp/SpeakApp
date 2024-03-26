package com.speakapp.userservice.configurations;

import org.apache.kafka.common.serialization.Serdes;
import com.speakapp.userservice.dtos.AppUserCreateDTO;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class AppUserCreateDTOSerde extends Serdes.WrapperSerde<AppUserCreateDTO> {

    public AppUserCreateDTOSerde() {
        super(new JsonSerializer<>(), new JsonDeserializer<>(AppUserCreateDTO.class));
    }
}
