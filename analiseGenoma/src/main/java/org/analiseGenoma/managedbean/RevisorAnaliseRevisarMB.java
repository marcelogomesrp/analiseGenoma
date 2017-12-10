package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.managedbean.util.RequestParam;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.Usuario;
import org.analiseGenoma.model.Variante;
import org.analiseGenoma.service.AnaliseService;
import org.analiseGenoma.service.VcfService;

@Named(value = "revAnaliseRevisarMB")
@RequestScoped
public class RevisorAnaliseRevisarMB implements Serializable {

    private Usuario usuario;
    private Analise analise;
    private List<Variante> variantes;
    @Inject
    @RequestParam
    private String id;
    @Inject
    private AnaliseService analiseService;
    @Inject
    private VcfService vcfService;
    @Inject
    private FacesContext context;

    @PostConstruct
    public void init() {
        if (id != null) {
            analise = analiseService.buscarPorId(Long.valueOf(id));
            variantes = vcfService.buscarVariante(analise.getVcf().getId());
        } else {
            analise = new Analise();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Analise getAnalise() {
        return analise;
    }

    public void setAnalise(Analise analise) {
        this.analise = analise;
    }

    public List<Variante> getVariantes() {
        return variantes;
    }

    public void setVariantes(List<Variante> variantes) {
        this.variantes = variantes;
    }

    public String finalizar() {
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Analise enviar ao gestor com sucesso"));
        return "index.xhtml?faces-redirect=true";
    }
}
