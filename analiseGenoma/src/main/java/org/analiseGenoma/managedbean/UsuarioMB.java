package org.analiseGenoma.managedbean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import org.analiseGenoma.model.Usuario;

@Named(value = "usuarioMB")
@RequestScoped
public class UsuarioMB implements Serializable {

    private Usuario usuario;

    @PostConstruct
    public void init() {
        usuario = new Usuario();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
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
