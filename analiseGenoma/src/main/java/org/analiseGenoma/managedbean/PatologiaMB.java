package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.managedbean.util.RequestParam;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.Patologia;
import org.analiseGenoma.service.PatologiaService;

@Named(value = "patologiaMB")
@RequestScoped
public class PatologiaMB implements Serializable {
    
    @Inject
    private PatologiaService patologiaService;
    private Patologia patologia;
    @Inject
    private FacesContext context;
    @Inject
    @RequestParam
    private String idPatologia;
    private List<Patologia> patologias;
    private String nome;
    
  
    
    @PostConstruct
    public void init(){
        patologia = new Patologia();
    }
    
    public void adicionar(){
        patologiaService.adicionar(patologia);
        patologia = new Patologia();        
                context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Cadastrado com sucesso"));
        
    }

    public Patologia getPatologia() {
        return patologia;
    }

    public void setPatologia(Patologia patologia) {
        this.patologia = patologia;
    }
    

    
}
