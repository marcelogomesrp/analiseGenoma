package org.analiseGenoma.managedbean;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "logoffMB")
@RequestScoped
public class LogoffMB implements Serializable {
    
    @Inject
    private ThemeSwitcherMB themeSwitcherMB;
    
    public String sairDoSistema(){
        themeSwitcherMB.changeLogoff();
        System.out.println("Fazendo logoff");
        return "/entrar.xhtml";
    }
    
}
