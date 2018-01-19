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
import org.analiseGenoma.model.BancoBiologico;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.Disease;
import org.analiseGenoma.service.BancoBiologicoService;
import org.analiseGenoma.service.PatologiaService;

@Named(value = "diseaseMB")
@RequestScoped
public class DiseaseMB implements Serializable {
    
    @Inject
    private PatologiaService diseaseService;
    private Disease disease;
    @Inject
    private FacesContext context;
    @Inject
    @RequestParam
    private String idPatologia;
    private List<Disease> diseases;
    private String nome;
    private Long idDbbio;
        @Inject
    private BancoBiologicoService bdService;
    
  
    
    @PostConstruct
    public void init(){
        disease = new Disease();
    }
    
    public void adicionar(){
        diseaseService.adicionar(disease);
        disease = new Disease();        
                context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Cadastrado com sucesso"));
        
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public Long getIdDbbio() {
        return idDbbio;
    }

    public void setIdDbbio(Long idDbbio) {
        this.idDbbio = idDbbio;
    }
    
    
    public List<SelectItem> getSelectDbbios() {
        List<SelectItem> select = new ArrayList<SelectItem>();
        for (BancoBiologico dbbio : bdService.buscar()) {
            select.add(new SelectItem(dbbio.getId(), dbbio.getNome()));
        }
        return select;
    }
    
}
