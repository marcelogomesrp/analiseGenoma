package org.analiseGenoma.model.validator.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.analiseGenoma.model.validator.Obrigatorio;



public class ObrigatorioValidator implements ConstraintValidator<Obrigatorio, String> {


    @Override
    public void initialize(Obrigatorio o) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext cvc) {
        if(value == null)
            return false;
        if(value.isEmpty())
            return false;
        if("".equals(value))
            return false;
        return true;
    }

}
