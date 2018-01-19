package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.model.Age;
import org.analiseGenoma.model.InheritanceType;
import org.analiseGenoma.service.InheritanceTypeService;


@Named(value = "inheritanceMB")
@ViewScoped
public class InheritanceMB implements Serializable {
    @Inject private FacesContext context;
    @Inject private InheritanceTypeService inheritanceService;
    private InheritanceType inheritance;
    private List<InheritanceType> inheritances;

    public InheritanceType getInheritance() {
        return inheritance;
    }

    public void setInheritance(InheritanceType inheritance) {
        this.inheritance = inheritance;
    }

    public List<InheritanceType> getInheritances() {
        return inheritances;
    }

    public void setInheritances(List<InheritanceType> inheritances) {
        this.inheritances = inheritances;
    }
        
    @PostConstruct
    public void init() {
        inheritance = new InheritanceType();
        inheritances =  inheritanceService.buscar();
    }
    
    public void add(){
        inheritanceService.adicionar(inheritance);
        context.addMessage(null, new FacesMessage("It's done") );
        inheritance = new InheritanceType();
        inheritances =  inheritanceService.buscar();

    }
}