package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.model.BancoBiologico;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.service.BancoBiologicoService;

@Named(value = "bdBioAdicionarMB")
@RequestScoped
public class bdBioAdicionarMB implements Serializable {

    @Inject
    private BancoBiologicoService bdBioService;
    private BancoBiologico bdBio;
    @Inject
    private FacesContext context;

    @PostConstruct
    public void init() {
        bdBio = new BancoBiologico();
    }

    public void adicionar() {
        bdBioService.adicionar(bdBio);
        bdBio = new BancoBiologico();
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Cadastrado com sucesso"));
    }

    public BancoBiologico getBdBio() {
        return bdBio;
    }

    public void setBdBio(BancoBiologico bdBio) {
        this.bdBio = bdBio;
    }

    
}
