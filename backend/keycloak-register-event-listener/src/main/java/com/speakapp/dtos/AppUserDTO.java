package com.speakapp.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.keycloak.models.UserModel;

import java.time.LocalDate;
import java.util.Date;

@Value
@Builder
@Jacksonized
public class AppUserDTO {

    String firstName;
    String lastName;
    String email;
    LocalDate dateOfBirth;

    public static AppUserDTO fromUserModel(UserModel userModel) {

        String date = userModel.getFirstAttribute("dateOfBirth");

        return new AppUserDTO(
                userModel.getFirstName(),
                userModel.getLastName(),
                userModel.getEmail(),
                LocalDate.parse(date)
        );
    }
}
