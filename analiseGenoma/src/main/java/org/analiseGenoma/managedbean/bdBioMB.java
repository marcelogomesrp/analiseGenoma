package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.model.BancoBiologico;
import org.analiseGenoma.service.BancoBiologicoService;

@Named(value = "bdBioMB")
@RequestScoped
public class bdBioMB implements Serializable {

    @Inject
    private BancoBiologicoService bdBioService;
    @Inject
    private FacesContext context;
    private List<BancoBiologico> bancos;

    @PostConstruct
    public void init() {
        bancos = bdBioService.buscar();
    }

    public List<BancoBiologico> getBancos() {
        return bancos;
    }

    public void setBancos(List<BancoBiologico> bancos) {
        this.bancos = bancos;
    }
    
    
    
    


    
}
