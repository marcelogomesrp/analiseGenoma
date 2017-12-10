package org.analiseGenoma.managedbean;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "administradorMB")
@RequestScoped
public class AdministradorMB implements Serializable {
    
    @Inject
    private ThemeSwitcherMB themeSwitcherMB;
    
    public String login(){
        themeSwitcherMB.changeAdministrador();
        return "administrador/index.xhtml";
    }
    
}
