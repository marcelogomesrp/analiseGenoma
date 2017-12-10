package org.analiseGenoma.managedbean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class ThemeSwitcherMB implements Serializable{
    private String theme;

    @PostConstruct
    public void init() {
        theme = "bootstrap"; 
        
    }
    
    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
    
    public void changeLogoff(){
        this.theme = "bootstrap";
    }
    
    public void changeGestor(){
        //this.theme = "le-frog";
        this.theme = "south-street";
    }
    
    public void changeRevisor(){
        theme = "blitzer";
    }
    
    public void changeAdministrador(){
        theme = "afternoon";
    }    
    
}
