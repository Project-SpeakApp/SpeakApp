package com.speakapp.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.keycloak.models.UserModel;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class AppUserDTO {

    UUID userId;
    String firstName;
    String lastName;
    String email;
    LocalDate dateOfBirth;

    public static AppUserDTO fromUserModel(UserModel userModel) {

        String date = userModel.getFirstAttribute("dateOfBirth");

        return new AppUserDTO(
                UUID.fromString(userModel.getId()),
                userModel.getFirstName(),
                userModel.getLastName(),
                userModel.getEmail(),
                LocalDate.parse(date)
        );
    }

    public static AppUserDTO fromAdminUserModel(UserModel userModel) {

        return new AppUserDTO(
                UUID.fromString(userModel.getId()),
                userModel.getFirstName(),
                userModel.getLastName(),
                userModel.getEmail(),
                LocalDate.now().minusYears(20L)
        );
    }
}
