package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
    private DualListModel<String> revisers;
    
    @PostConstruct
    public void init() {
        try {
            Long value = (Long) FacesUtil.getSessionMapValue("id");
            analise = analiseService.buscarPorId(value);
            
            //List<String> source = new ArrayList<>();
            //List<String> target = new ArrayList<>();
//            Usuario u = new Usuario();
//            u.setNome("Teste");
//            source.add(u);
                List<String> target = analise.getRevisores().stream().map(r -> r.getNome()).collect(Collectors.toList());            
                List<String> source = usuarioService.buscarRevisores()
                        .stream()
                        .map(r -> r.getNome())
                        .filter(g -> !target.contains(g))
                        .collect(Collectors.toList());           
                
                 //vcfMetadata.getUmdPredictors().stream().map(u -> u.getName()).filter(u -> !target.contains(u)).collect(Collectors.toList());
            //filtro.getGenes().stream().map(g -> g.getSimbolo()).collect(Collectors.toList());
            revisers = new DualListModel<>(source, target);
            
        } catch (Exception ex) {
            System.out.println("Erro no init" + ex.getMessage());
        }

    }
    
    public String openView(Analise analise){
        analise.setEstado("varianteSelecionada");
        analiseService.atualizar(analise);
        FacesUtil.setSessionMapValue("id", analise.getId());
        return "analise_select_reviser.xhtml?faces-redirect=true";
    }
    
    public String submit(){
       // List<Usuario> listUser = revisers.getTarget();
        Set<Usuario> rev = new HashSet<>();
        for(String name : revisers.getTarget()){
            rev.add(usuarioService.findRevisoresByName(name));
        }
        analise.setRevisores(rev);
        analiseService.atualizar(analise);
        return "analise_select_reviser.xhtml?faces-redirect=true";
    }

    public Analise getAnalise() {
        return analise;
    }

    public void setAnalise(Analise analise) {
        this.analise = analise;
    }

    public DualListModel<String> getRevisers() {
        if(revisers == null){
            List<String> source = new ArrayList<>();
            List<String> target = new ArrayList<>();
            revisers = new DualListModel<>(source, target);
        }
        return revisers;
    }

    public void setRevisers(DualListModel<String> revisers) {
        this.revisers = revisers;
    }
    
    
    

}
