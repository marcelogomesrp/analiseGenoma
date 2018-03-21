package org.analiseGenoma.managedbean;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.analiseGenoma.managedbean.util.FacesUtil;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.User;
import org.analiseGenoma.model.Variante;
import org.analiseGenoma.model.VarianteRevisada;
import org.analiseGenoma.model.Vcf;
import org.analiseGenoma.service.AnaliseService;
import org.analiseGenoma.service.VarianteRevisadaService;
import org.analiseGenoma.service.VarianteService;
import org.primefaces.context.RequestContext;

@Named(value = "ViewOpinarMB")
@RequestScoped
public class ViewOpinarMB {
    @Inject
    private VarianteService varianteService;
    @Inject
    private AnaliseService analiseService;
    @Inject
    private UsuarioMB usuarioMB;
    @Inject
    private VarianteRevisadaService varianteRevisadaService;
    
    private Long idVariante;
    private Long idAnalise;
    private Variante variante;
    private Vcf vcf;
    private Analise analise;
    private User revisor;
    private VarianteRevisada varianteRevisada;

    public Long getIdVariante() {
        return idVariante;
    }

    public void setIdVariante(Long idVariante) {
        this.idVariante = idVariante;
    }

    public Variante getVariante() {
        return variante;
    }

    public void setVariante(Variante variante) {
        this.variante = variante;
    }

    public Vcf getVcf() {
        return vcf;
    }

    public void setVcf(Vcf vcf) {
        this.vcf = vcf;
    }

    public Analise getAnalise() {
        return analise;
    }

    public void setAnalise(Analise analise) {
        this.analise = analise;
    }

    public User getRevisor() {
        return revisor;
    }

    public void setRevisor(User revisor) {
        this.revisor = revisor;
    }

    public VarianteRevisada getVarianteRevisada() {
        return varianteRevisada;
    }

    public void setVarianteRevisada(VarianteRevisada varianteRevisada) {
        this.varianteRevisada = varianteRevisada;
    }
    
    
    
    
    
    @PostConstruct
    public void init() {
        idVariante = (Long) FacesUtil.getSessionMapValue("idVariante");
        idAnalise = (Long) FacesUtil.getSessionMapValue("idAnalise");
        if(idVariante != null){
            variante = varianteService.findById(idVariante);
            vcf = variante.getVcf();
            
        }
        if(idAnalise != null){
            analise = analiseService.buscarPorId(idAnalise);
        }
        revisor = usuarioMB.getUsuario();
        
        //List<VarianteRevisada> list = varianteRevisadaService.findByAnaliseRevisor(vcf, revisor);
        List<VarianteRevisada> list = varianteRevisadaService.findByVarianteRevisor(variante, revisor);
        if(list.size() == 1){
            varianteRevisada = list.get(0);
        }else{
            varianteRevisada = new VarianteRevisada();
            varianteRevisada.setRevisor(revisor);
            varianteRevisada.setVariant(variante);
        }
//        idAnalise = (Long) FacesUtil.getSessionMapValue("id");
//        if (idAnalise != null) {
//                Analise analise = analiseService.buscarPorId(idAnalise);
//                filtro = filtroService.buscarPorAnalise(analise.getId());   
//                vcfMetadata = vcfMetadataService.findByVcfId(analise.getVcf().getId());       
//                List<String> target = filtro.getCromossomos().stream().map(u -> u.getNome()).collect(Collectors.toList());
//                List<String> source = vcfMetadata.getCromossomos().stream().map(u -> u.getNome()).filter(u -> !target.contains(u)).collect(Collectors.toList());
//                list = new DualListModel<>(source, target );  
//        }
    }
    
    public void closeView(){
        varianteRevisadaService.merge(varianteRevisada);
        //this.updateFiltro();
        //filtroService.atualizar(filtro);
        RequestContext.getCurrentInstance().closeDialog("Filtro aplicado com sucesso");  
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException ex) {
            Logger.getLogger(AnaliseSelecionarVarianteMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    private void updateFiltro() {
//        Set<Cromossomo> listFilter = new HashSet<>();
//        for(String name: list.getTarget()){
//            List<Cromossomo> find;
//            try {
//                find = cromossomoService.findByName(name);
//                if(find.size() == 1)
//                listFilter.add(find.get(0));
//                
//            } catch (Exception ex) {
//                Logger.getLogger(ViewOpinarMB.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        filtro.setCromossomos(listFilter);        
//    }
}
