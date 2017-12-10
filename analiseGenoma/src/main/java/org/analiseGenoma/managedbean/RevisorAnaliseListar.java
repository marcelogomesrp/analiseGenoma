package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.Usuario;
import org.analiseGenoma.service.AnaliseService;

@Named(value = "revAnaliseListarMB")
@RequestScoped
public class RevisorAnaliseListar implements Serializable {

    private Usuario usuario;
    private List<Analise> analises;
    @Inject
    private AnaliseService analiseService;
    @Inject
    private FacesContext context;

    public List<Analise> getAnalises() {
        return analises;
    }

    public void setAnalises(List<Analise> analises) {
        this.analises = analises;
    }

    @PostConstruct
    public void init() {
        analises = analiseService.buscar();
    }



}
