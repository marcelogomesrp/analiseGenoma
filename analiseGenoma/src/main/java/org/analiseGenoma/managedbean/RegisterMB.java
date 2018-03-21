package org.analiseGenoma.managedbean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.model.User;
import org.analiseGenoma.service.UserService;

@Named(value = "registerMB")
@RequestScoped
public class RegisterMB implements Serializable {

    @Inject
    private FacesContext context;
    @Inject
    private UserService userService;
    //private UsuarioService usuarioService;
    private User user;
    private String confirmPassword;

    @PostConstruct
    public void init() {
        this.clean();
    }
    
    private void addMessage(String message) {
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(message));
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void clean() {
        user = new User();
        confirmPassword = "";
    }

    public String add() {
        try {
            userService.persiste(user, confirmPassword); 
            this.clean();
            context.getExternalContext()
                    .getFlash().setKeepMessages(true);
            context.addMessage(null, new FacesMessage("Registered successfully"));

        } catch (Exception ex) {
            context.getExternalContext()
                    .getFlash().setKeepMessages(true);
            context.addMessage(null, new FacesMessage("An error has occurred:" + ex.getMessage()));
        }
        return "register.xhtml?faces-redirect=true";

    }

    public void validateName(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        FacesMessage message;
        String name = (String) o;
        if (name == null) {
            message = new FacesMessage("Name can't be null ");
        } else {
            if (name.length() < 3) {
                message = new FacesMessage("Name must be longer than 3 characters");
            } else {
                return;
            }
        }

        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(message);
    }

    public void validateEmail(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        FacesMessage message;
        String email = (String) o;
        if (email == null) {
            message = new FacesMessage("Email can't be null ");
        } else {
            if (!email.contains("@")) {
                message = new FacesMessage("Email isn't valid");
            } else {
                return;
            }
        }

        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(message);
    }

    public void validatePhone(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        FacesMessage message;
        String phone = (String) o;
        if (phone == null) {
            message = new FacesMessage("Phone can't be null ");
        } else {
            if("".equals(phone)){
                message = new FacesMessage("Phone can't be null ");
            }else{
                return;
            }
        }
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(message);
    }
    
    public void validatePassword(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        FacesMessage message;
        String password = (String) o;
        if (password == null) {
            message = new FacesMessage("Password can't be null ");
        } else {
            if("".equals(password)){
                message = new FacesMessage("Password can't be null ");
            }else{
                return;
            }
        }
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(message);
    }
}


