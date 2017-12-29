package org.analiseGenoma.model.validator.impl;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.analiseGenoma.model.validator.PatientUniqueName;
import javax.inject.Inject;

//import javax.validation.ValidatorFactory;
//import org.hibernate.validator.cdi.HibernateValidator
import org.analiseGenoma.model.Paciente;
import org.analiseGenoma.service.PacienteService;

//http://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#section-declaring-bean-constraints
//http://www.springfuse.com/2012/09/27/jsf2-jpa2-unique-validator.html
@ApplicationScoped
public class PatientUniqueNameValidator implements ConstraintValidator<PatientUniqueName, String> {

    //@Inject
    //private ValidatorFactory validatorFactory;
        
    @Inject
    private PacienteService pacienteService;

    @Override
    public void initialize(PatientUniqueName a) {
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext cvc) {
        System.out.println("validando...");

        try {
            //return pacienteService.buscarNome(name).isEmpty();
            //return false;
            List<Paciente> list = pacienteService.buscarNome(name);
            if (list == null) {
                return true;
            }
            return list.isEmpty();
            //return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
