package com.speakapp;

import org.keycloak.provider.ConfiguredProvider;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.validate.ValidationContext;
import org.keycloak.validate.ValidationError;
import org.keycloak.validate.ValidatorConfig;
import org.keycloak.validate.validators.LocalDateValidator;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class DateOfBirthAttributeValidatorProvider extends LocalDateValidator implements ConfiguredProvider {

    public static final String ID = "date-of-birth-attribute";
    private static final List<ProviderConfigProperty> configProperties = new ArrayList<>();

    @Override
    public String getHelpText() {
        return "Validates that the user's date of birth attribute falls within a specified range.";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configProperties;
    }

    @Override
    protected void doValidate(String userDateOfBirth, String input, ValidationContext validationContext, ValidatorConfig validatorConfig) {
        LocalDate dateOfBirth = LocalDate.parse(userDateOfBirth);
        LocalDate now = LocalDate.now();

        int age = Period.between(dateOfBirth, now).getYears();

        if (age < 13) {
            validationContext.addError(new ValidationError(
                    ID, input, "User has to be older than 13 years old."));
        }
        if(age > 100) {
            validationContext.addError(new ValidationError(
                    ID, input, "User cannot be older than 100 years old."));
        }
    }

    @Override
    public String getId() {
        return ID;
    }
}
