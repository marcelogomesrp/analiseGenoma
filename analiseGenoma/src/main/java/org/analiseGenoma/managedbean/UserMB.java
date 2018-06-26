package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.model.User;
import org.analiseGenoma.service.UserService;

@Named(value = "userMB")
@ViewScoped
public class UserMB implements Serializable {
    @Inject private FacesContext context;
    private User user;
    private List<User> users;
    @Inject
    private UserService userService;
    @PostConstruct
    public void init() {
        System.out.println("Reload UserMB");
        user = new User();
        users = userService.find();
    }

    public List<User> getUsers() {
        return users;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
    
    public void edit(User user){
       this.user = user;
        System.out.println("Editing user: " + user.toString());
    }
    
    public void update(){
        userService.merge(user);
        user = new User();
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("User updated"));
    }

    public boolean isEditmode(){
        return user.getId() != null;
    }
    
    public void cancel(){
        user = new User();
    }
}
