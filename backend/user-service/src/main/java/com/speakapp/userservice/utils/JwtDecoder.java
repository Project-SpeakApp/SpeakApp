package com.speakapp.userservice.utils;

import com.speakapp.userservice.exceptions.InvalidJWTFormatException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.UUID;

@Component
public class JwtDecoder {

    public UUID extractUserIdFromJwt(String encodedJwt) {
        if (encodedJwt == null) {
            throw new IllegalArgumentException("JWT token is null");
        }

        String[] chunks = encodedJwt.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        try {

            String payload = new String(decoder.decode(chunks[1]));
            JSONObject payloadJson = new JSONObject(payload);
            String userId = payloadJson.getString("sub");
            return UUID.fromString(userId);
        } catch (Exception e) {
            throw new InvalidJWTFormatException("Invalid JWT token format. Failed to collect claims.");
        }
    }
}
