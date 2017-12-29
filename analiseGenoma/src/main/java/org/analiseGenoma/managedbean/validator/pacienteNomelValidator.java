package org.analiseGenoma.managedbean.validator;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.model.Paciente;
import org.analiseGenoma.service.PacienteService;

//@FacesValidator("pacienteNomeValidator")
@Named
@RequestScoped
public class pacienteNomelValidator implements Validator {

    @Inject
    PacienteService pacienteService;

    public pacienteNomelValidator() {
        
         //this.userService = CDI.current().select(UserService.class).get();
    }
    
        

    
    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        String nome = (String) o;
        List<Paciente> list = pacienteService.buscarNome(nome);
        if(list == null){
            return;
        }
        if(list.isEmpty()){
            return;
        }
                
        FacesMessage message
                = new FacesMessage("Paciente ja cadastrado!!! :D");
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(message);
    }

}
