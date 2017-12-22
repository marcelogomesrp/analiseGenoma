package org.analiseGenoma.model.validator;

import java.util.List;
import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.analiseGenoma.model.Etnia;
import org.analiseGenoma.service.EtniaService;

public class EtniaUniqueSiglaValidator implements ConstraintValidator<EtniaUniqueSigla, String> {

    
    @Inject private EtniaService etniaService;
    
    @Override
    public void initialize(EtniaUniqueSigla constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value,ConstraintValidatorContext context) {
        Etnia e = new Etnia(); 
        e.setSigla(value);
        List<Etnia> l = etniaService.findByExample(e);
        return l.isEmpty();
  }
 
}
