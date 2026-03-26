package com.example.backend.validator;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;

public class ImportValidator implements Validator<String> {
    private String type;
    public ImportValidator(String type){
        this.type = type;
    }
    @Override
    public ValidationResult apply(String value, ValueContext valueContext) {
        Notification.show("in apply " + type);
        if (value == null || value.isBlank()){
            return ValidationResult.ok();
        }
        if (!value.matches("[0-9]*[,]?[0-9]{0,2}")) {
            return ValidationResult.error("El camp ha de ser numèric i només pot contenir comes per decimals");
        }
        double numero = Double.parseDouble(value.trim().replace(",", "."));
        if (type== null && numero <= 0){
            return ValidationResult.error("Aquest camp no admet import negatiu o 0");
        }
        if (!type.equals("SBA") && numero == 0) {
            return ValidationResult.error("Aquest concepte no admet import a 0");
        }

        return ValidationResult.ok();
    }
}
