package org.analiseGenoma.managedbean;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "revisorMB")
@RequestScoped
public class RevisorMB implements Serializable {
    
    @Inject
    private ThemeSwitcherMB themeSwitcherMB;
    
    public String login(){
        themeSwitcherMB.changeRevisor();
        return "revisor/index.xhtml";
    }
    
}
