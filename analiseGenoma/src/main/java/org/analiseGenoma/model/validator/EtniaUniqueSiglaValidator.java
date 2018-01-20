package org.analiseGenoma.model.validator;

import java.util.List;
import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.analiseGenoma.model.Population;
import org.analiseGenoma.service.PopulationService;

public class EtniaUniqueSiglaValidator {}

//public class EtniaUniqueSiglaValidator implements ConstraintValidator<EtniaUniqueSigla, String> {
//
//    
////    @Inject private PopulationService etniaService;
////    
////    @Override
////    public void initialize(EtniaUniqueSigla constraintAnnotation) {
////    }
////
////    @Override
////    public boolean isValid(String value,ConstraintValidatorContext context) {
////        Population e = new Population(); 
////        e.setSigla(value);
////        List<Population> l = etniaService.findByExample(e);
////        return l.isEmpty();
////  }
// 
//}
