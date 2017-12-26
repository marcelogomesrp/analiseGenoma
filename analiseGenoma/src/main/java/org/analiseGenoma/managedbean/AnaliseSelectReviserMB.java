package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.analiseGenoma.managedbean.util.FacesUtil;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.Usuario;
import org.analiseGenoma.service.AnaliseService;
import org.analiseGenoma.service.UsuarioService;
import org.primefaces.model.DualListModel;

@Named(value = "aSelcReviserMB")
@RequestScoped
public class AnaliseSelectReviserMB implements Serializable {

    @Inject
    private FacesContext context;    
    @Inject
    private AnaliseService analiseService;
    @Inject
    private UsuarioService usuarioService;
    private Analise analise;
    private DualListModel<Usuario> revisers;
    
    @PostConstruct
    public void init() {
        try {
            Long value = (Long) FacesUtil.getSessionMapValue("id");
            analise = analiseService.buscarPorId(value);
            
            List<Usuario> source = new ArrayList<>();
            List<Usuario> target = new ArrayList<>();
//            Usuario u = new Usuario();
//            u.setNome("Teste");
//            source.add(u);
            source = usuarioService.buscarRevisores();
            revisers = new DualListModel<>(source, target);
            
        } catch (Exception ex) {
            System.out.println("Erro no init");
        }

    }
    
    public String openView(Analise analise){
        FacesUtil.setSessionMapValue("id", analise.getId());
        return "analise_select_reviser.xhtml?faces-redirect=true";
    }

    public Analise getAnalise() {
        return analise;
    }

    public void setAnalise(Analise analise) {
        this.analise = analise;
    }

    public DualListModel<Usuario> getRevisers() {
        return revisers;
    }

    public void setRevisers(DualListModel<Usuario> revisers) {
        this.revisers = revisers;
    }
    
    
    

}
