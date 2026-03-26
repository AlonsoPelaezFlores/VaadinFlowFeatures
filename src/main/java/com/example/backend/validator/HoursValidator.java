package com.example.backend.validator;


import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;

public class HoursValidator implements Validator<Integer> {
    @Override
    public ValidationResult apply(Integer value, ValueContext valueContext) {
        if (value == null || value.toString().isBlank()){
            return ValidationResult.error("El número d’hores és obligatori per aquest concepte");
        }

        if (value < 0){
            return ValidationResult.error("El camp no pot ser negatiu");
        }
        return ValidationResult.ok();
    }
}
