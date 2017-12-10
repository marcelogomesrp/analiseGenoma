package org.analiseGenoma.managedbean;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "gestorMB")
@RequestScoped
public class GestorMB implements Serializable {
    
    @Inject
    private ThemeSwitcherMB themeSwitcherMB;
    
    public String login(){
        themeSwitcherMB.changeGestor();
        return "gestor/index.xhtml";
    }
    
}
