package org.analiseGenoma.managedbean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import org.analiseGenoma.model.User;

@Named(value = "usuarioMB")
//@RequestScoped
@SessionScoped
public class UsuarioMB implements Serializable {

    private User usuario;

    @PostConstruct
    public void init() {
        usuario = new User();
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

//    public void emailValidate(FacesContext context,
//            UIComponent componentToValidate,
//            Object value)
//            throws ValidatorException {
//
//        String email = value.toString();
//
//        //if (!Validator.isEmailAddress(email))
//        //{
//        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "email", "Please enter valid email address");
//        throw new ValidatorException(message);
//        
//        //context.addMessage("formulario:nome", new FacesMessage(FacesMessage.SEVERITY_ERROR, "error message", "Nome do paciente ja cadastrado"));
//        //}
//
//    }

}
