package org.analiseGenoma.sessionbean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.analiseGenoma.model.User;

@Named(value = "userSB")
@SessionScoped
public class UserSB implements Serializable{
    private User user;
    
   @PostConstruct
    public void init() {
        user = new User();
    }

    public User getUser() {
        return user;
    }
    
    public String getName(){
        return user.getName();
    }
    public void setName(String name){
        user.setName(name);
    }

    public String getEmail(){
        return user.getEmail();
    }
    public void setEmail(String email){
        user.setEmail(email);
    }


    public void setUser(User user) {
        this.user = user;
    }
    
    public void reset(){
        user = new User();
    }
       
}
