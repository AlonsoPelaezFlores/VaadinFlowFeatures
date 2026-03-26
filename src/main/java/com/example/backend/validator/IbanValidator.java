package com.example.backend.validator;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import lombok.Getter;
import org.apache.commons.validator.routines.IBANValidator;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;

import java.util.ArrayList;
import java.util.List;

public class IbanValidator implements Validator<String> {

    private final IBANValidator validator = IBANValidator.getInstance();
    private String countryCode;
    private static final Integer MAX_LENGTH = 24;
    private static final Integer MIN_LENGTH = 2;
    @Getter
    private List<String> errors;
    public IbanValidator(){
        this.countryCode = "AD";
    }
    @Override
    public ValidationResult apply(String value, ValueContext valueContext) {
        errors = new ArrayList<>();
        if (value == null || value.isBlank()) {
            errors.add("IBAN is required");
            return ValidationResult.error("IBAN is required");
        }
        if (value.length()< MAX_LENGTH){
            errors.add("IBAN is too short");
            return ValidationResult.error("La longitud del IBAN es muy corto");
        }
        if (!validator.isValid(value)){
            errors.add("IBAN is not valid");
            return ValidationResult.error("El formato del IBAN no es valido");
        }
        String codeCountry = value.substring(0,2).toUpperCase();
        if (!countryCode.equals(codeCountry)){
            errors.add("IBAN must be only from Andorra");
            return ValidationResult.error("IBAN solo debe ser de Andorra");
        }
        return ValidationResult.ok();
    }
}
