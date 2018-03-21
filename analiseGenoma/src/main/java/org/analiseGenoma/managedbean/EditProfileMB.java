package org.analiseGenoma.managedbean;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.service.UserService;
import org.analiseGenoma.sessionbean.UserSB;


@Named(value = "EditProfileMB")
@RequestScoped
public class EditProfileMB implements Serializable {
    @Inject
    private FacesContext context;
    @Inject
    private UserSB userSB;
    @Inject
    private UserService userService;

    private void addMessage(String message) {
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(message));
    }
    
    
    public UserSB getUserSB() {
        return userSB;
    }

    public void setUserSB(UserSB userSB) {
        this.userSB = userSB;
    }
    
    public void update(){
        try{
        userService.merge(userSB.getUser());
        this.addMessage("User updated successfully");
        }catch(Exception ex){
            this.addMessage("Error: " + ex.getMessage());
        }
    }
}
