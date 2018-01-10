package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
//import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.managedbean.util.FacesUtil;
import org.analiseGenoma.managedbean.util.RequestParam;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.Filtro;
import org.analiseGenoma.model.Usuario;
import org.analiseGenoma.model.Variante;
import org.analiseGenoma.model.VarianteRevisada;
import org.analiseGenoma.service.AnaliseService;
import org.analiseGenoma.service.FiltroService;
import org.analiseGenoma.service.VarianteRevisadaService;
import org.analiseGenoma.service.VcfService;
import org.primefaces.context.RequestContext;

@Named(value = "revAnaliseRevisarMB")
//@RequestScoped
@ViewScoped
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
    private Filtro filtro;
    @Inject
    private FiltroService filtroService;
    private List<VarianteRevisada> variantesRev;
    @Inject
    private VarianteRevisadaService varianteRevisadaService;
    @Inject private UsuarioMB usuarioMB;
    private Usuario revisor;

    @PostConstruct
    public void init() {
        if (id != null) {
            revisor = usuarioMB.getUsuario();
            analise = analiseService.buscarPorId(Long.valueOf(id));
            filtro = filtroService.buscarPorAnalise(analise.getId());
            variantes = vcfService.findVariante(analise, filtro);
            variantesRev = varianteRevisadaService.findByAnaliseRevisor(analise.getVcf(), revisor);
            
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
        System.out.println("Finalizado rev");
        
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Analise enviar ao gestor com sucesso"));
        return "index.xhtml?faces-redirect=true";
    }
    
    public String observacao(Long variantId){
        
        return "ok" + variantId;
    }
    
    public void observacaoEdited(AjaxBehaviorEvent event){
        System.out.println("Edita :D---");
    }
    
    
    public void teste(ValueChangeEvent e){
        System.out.println("teste...");
        
    }
    public void viewOpinar(Long id){
        Map<String, Object> options = new HashMap<>();
        options.put("modal", true);
        options.put("width", 800);
        options.put("height", 600);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        options.put("resizable", false);

        Map<String, List<String>> params = new HashMap<>();
        List<String> values = new ArrayList<>();
        values.add(id.toString());
        params.put("id", values);
        FacesUtil.setSessionMapValue("idVariante", id);
        FacesUtil.setSessionMapValue("idAnalise", analise.getId());        
        
        RequestContext.getCurrentInstance().openDialog("viewopinar", options, params);
        variantes = vcfService.findVariante(analise, filtro);
    }
    
}
