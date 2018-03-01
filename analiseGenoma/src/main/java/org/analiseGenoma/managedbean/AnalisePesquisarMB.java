package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.managedbean.util.FacesUtil;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.AnaliseLaudo;
import org.analiseGenoma.service.AnaliseLaudoService;
import org.analiseGenoma.service.AnaliseService;

@Named(value = "analisePesquisarMB")
//@RequestScoped
@ViewScoped
public class AnalisePesquisarMB implements Serializable {

    @Inject
    private FacesContext context;
    @Inject
    private AnaliseService analiseService;
    private List<Analise> analises;
    private Analise analise;
    @Inject
    private AnaliseLaudoService analiseLaudoService;

    @PostConstruct
    public void init() {
        analise = new Analise();
        analises = analiseService.buscar();
    }

    public List<Analise> getAnalises() {
        return analises;
    }

    public void setAnalises(List<Analise> analises) {
        this.analises = analises;
    }

    public String selecionarVariante(Analise selected) {
        analise = selected;
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("vai la"));
        FacesUtil.setSessionMapValue("id", analise.getId());
        return "analise_selecionar_variantes.xhtml?faces-redirect=true";
    }

    //temp
    public String viewLaudar(Analise selected) {
        Analise analise1 = selected;
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        FacesUtil.setSessionMapValue("idAnalise", analise.getId());
        return "analise_laudar.xhtml?faces-redirect=true";
    }

    public String duplicate(Analise analise) {
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        FacesUtil.setSessionMapValue("idAnalise", analise.getId());
        return "analise_nova.xhtml?faces-redirect=true";
    }

}
