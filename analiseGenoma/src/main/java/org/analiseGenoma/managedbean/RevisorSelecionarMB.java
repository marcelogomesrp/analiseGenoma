package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.managedbean.util.RequestParam;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.User;
import org.analiseGenoma.service.AnaliseService;
import org.analiseGenoma.service.UsuarioService;

@Named(value = "revSelecionarMB")
@ViewScoped
//@RequestScoped
public class RevisorSelecionarMB implements Serializable {

    @Inject
    private FacesContext context;

    @Inject
    @RequestParam
    private String analiseId;
    private Analise analise;
    @Inject
    private AnaliseService analiseService;
    @Inject
    private UsuarioService usuarioService;
    private List<User> revisores;
    private Set<User> revisoresSelecionados = new LinkedHashSet<>();

    @PostConstruct
    public void init() {
        if (analiseId == null) {
            System.out.println("ops nao tem analise");
        } else {
            System.out.println("Opa analise recebidoa " + analiseId);
            analise = analiseService.buscarPorId(Long.valueOf(analiseId));
        }

        revisores = usuarioService.buscarRevisores();

    }

    public String getAnaliseId() {
        return analiseId;
    }

    public void setAnaliseId(String analiseId) {
        this.analiseId = analiseId;
    }

    public Analise getAnalise() {
        return analise;
    }

    public void setAnalise(Analise analise) {
        this.analise = analise;
    }

    public List<User> getRevisores() {
        return revisores;
    }

    public void setRevisores(List<User> revisores) {
        this.revisores = revisores;
    }

    public void addRevisor() {
        System.out.println("opa-----------------> ");
    }

    public void addRevisor(User revisor) {
        System.out.println("opa2-----------------> " + revisor.toString());
        this.revisoresSelecionados.add(revisor);
    }

    public void removeRevisor(User revisor) {
        revisoresSelecionados.remove(revisor);
    }

    public boolean isSelecionado(User revisor) {
        return revisoresSelecionados.contains(revisor);
    }

    public String submeter(ActionEvent actionEvent) {
        System.out.println("Rodando...");
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Analise enviada aos revisores com sucesso"));
        return "index.xhtml?faces-redirect=true";
    }

    public String showPage() {
        System.out.println("\n\n\n\nopa!!!!\n\n\n\n");
        System.out.println("Selecionados: ");
        for(User rev : revisoresSelecionados){
            System.out.println("Selecionado o tio: " + rev.toString());
        }
        analise.setRevisores(revisoresSelecionados);
        analiseService.atualizar(analise);
        System.out.println("Estes tios vao analisar a analise: " + analise.toString());
        
        //algumService.finizaliarAnalise(variantes,revisores)
        
        
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Analise enviada aos revisores com sucesso"));
        return "index.xhtml?faces-redirect=true";
    }
    

}
