package org.analiseGenoma.managedbean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.analiseGenoma.managedbean.util.FacesUtil;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.Filtro;
import org.analiseGenoma.model.VcfMetadata;
import org.analiseGenoma.service.AnaliseService;
import org.analiseGenoma.service.CromossomoService;
import org.analiseGenoma.service.FiltroService;
import org.analiseGenoma.service.VcfMetadataService;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;

@Named(value = "VfAllelic1")
@RequestScoped
public class VfAllelic1 {
    private List<String> allelics;
    
    private DualListModel<String> list;
    private Long idAnalise;
    private VcfMetadata vcfMetadata;
    private Filtro filtro;
    @Inject
    private AnaliseService analiseService;
    @Inject 
    private VcfMetadataService vcfMetadataService;
    @Inject
    private FiltroService filtroService;
    @Inject
    private CromossomoService cromossomoService;
    
    @PostConstruct
    public void init() {
        idAnalise = (Long) FacesUtil.getSessionMapValue("id");
        allelics = new ArrayList<>();
        if (idAnalise != null) {
            try{
                Analise analise = analiseService.buscarPorId(idAnalise);
                filtro = filtroService.buscarPorAnalise(analise.getId());   
                vcfMetadata = vcfMetadataService.findByVcfId(analise.getVcf().getId());       
                List<String> target = new ArrayList<String>(filtro.getChangeds());
                List<String> source = vcfMetadata.getAlterado().stream().filter(u -> !target.contains(u)).collect(Collectors.toList());
                list = new DualListModel<>(source, target ); 
                
                
                allelics = new ArrayList<>();
                for(Integer i : filtro.getAlleciDeph1s()){
                    allelics.add(i.toString());
                }
            }catch(Exception ex){
                System.out.println("VfChanged Erro: " + ex);
            }
        }
    }

    public List<String> getAllelics() {
        return allelics;
    }

    public void setAllelics(List<String> allelics) {
        this.allelics = allelics;
    }
    
    
    
    public void closeView(){
        this.updateFiltro();
        filtroService.merge(filtro);
        RequestContext.getCurrentInstance().closeDialog("Filtro aplicado com sucesso");  
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException ex) {
            Logger.getLogger(AnaliseSelecionarVarianteMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    public Long getValue() {
        return idAnalise;
    }

    public void setValue(Long value) {
        this.idAnalise = value;
    }

//    private void updateFiltro() {
//        Set<Integer> listl = new HashSet<>();
//        for(String s:allelics){
//            listl.add(Integer.valueOf(s));
//        }
//        filtro.setByAllelicDeph1(!listl.isEmpty());
//        filtro.setAlleciDeph1s(listl);
//            
//    }
    
    private void updateFiltro() {
        Set<Integer> listl = new HashSet<>();
        if (allelics != null) {
            for (String s : allelics) {
                listl.add(Integer.valueOf(s));
            }
            filtro.setByAllelicDeph1(!listl.isEmpty());
        } else {
            filtro.setByAllelicDeph1(false);
        }
        filtro.setAlleciDeph1s(listl);        
    }
}
