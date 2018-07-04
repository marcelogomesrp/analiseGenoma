package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.analiseGenoma.sessionbean.UserSB;

@Named(value = "loginMB")
@RequestScoped
public class LoginMB implements Serializable {

    private final String MANAGER_URL = "gestor/index.xhtml?faces-redirect=true";
    private final String REVISER_URL = "revisor/index.xhtml?faces-redirect=true";
    private final String ADMINISTRAOTR_URL = "administrador/index.xhtml?faces-redirect=true";

    @Inject
    private FacesContext context;
    @Inject
    private UserSB userSB;
    @Inject
    private UserService userService;

    @PostConstruct
    public void init() {

        this.clean();
    }

    public UserSB getUserSB() {
        return userSB;
    }

    public void setUserSB(UserSB userSB) {
        this.userSB = userSB;
    }

    public String managerLogin() {
        try {
            User userBd = userService.findManagerByEmailPassword(userSB.getUser());
            if (userBd == null) {
                context.getExternalContext()
                        .getFlash().setKeepMessages(true);
                context.addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR 
                                ,"Email or password isn't valid" 
                                ,"Email or password isn't valid")
                );
                return null;
            } else {
                this.userSB.setUser(userBd);
                return MANAGER_URL;
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String reviserLogin() {
        System.out.println("opa logando...");
//        return REVISER_URL;
        try {
            //User userBd = userService.findManagerByEmailPassword(userSB.getUser());
            User userBd = userService.findReviserByEmailPassword(userSB.getUser());
            if (userBd == null) {
                context.getExternalContext()
                        .getFlash().setKeepMessages(true);
                context.addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR 
                                ,"Email or password isn't valid" 
                                ,"Email or password isn't valid")
                );
                return null;
            } else {
                this.userSB.setUser(userBd);
                return REVISER_URL;
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
        
    }

    public String administratorLogin() {
        try {
            User userBd = userService.findAdministratorByEmailPassword(userSB.getUser());
            if (userBd == null) {
                context.getExternalContext()
                        .getFlash().setKeepMessages(true);
                context.addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR 
                                ,"Email or password isn't valid" 
                                ,"Email or password isn't valid")
                );
                return null;
            } else {
                this.userSB.setUser(userBd);
                return ADMINISTRAOTR_URL;
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }    
    
    
    public void clean() {
        userSB.reset();
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

    public void validatePassword(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        FacesMessage message;
        String password = (String) o;
        if (password == null) {
            message = new FacesMessage("Password can't be null ");
        } else {
            if ("".equals(password)) {
                message = new FacesMessage("Password can't be null ");
            } else {
                return;
            }
        }

        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(message);
    }
}
