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
import org.analiseGenoma.model.Cromossomo;
import org.analiseGenoma.model.Filtro;
import org.analiseGenoma.model.VcfMetadata;
import org.analiseGenoma.service.AnaliseService;
import org.analiseGenoma.service.CromossomoService;
import org.analiseGenoma.service.FiltroService;
import org.analiseGenoma.service.VcfMetadataService;
import org.analiseGenoma.sessionbean.AnaliseSB;
import org.analiseGenoma.sessionbean.FilterSB;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;

@Named(value = "VfRef")
@RequestScoped
public class VfRef {
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
    @Inject
    private AnaliseSB analiseSB;
    @Inject
    private FilterSB filterSB;
    
    @PostConstruct
    public void init() {
        idAnalise = (Long) FacesUtil.getSessionMapValue("id");
        if (idAnalise != null) {
            try{
                Analise analise = analiseSB.getAnalise();
                filtro = filterSB.getFilter();
                vcfMetadata = vcfMetadataService.findByVcfId(analise.getVcf().getId());       
                //List<String> target = filtro.getCromossomos().stream().map(u -> u.getNome()).collect(Collectors.toList());
                List<String> target = new ArrayList<String>(filtro.getReferencias());
                //List<String> source = vcfMetadata.getCromossomos().stream().map(u -> u.getNome()).filter(u -> !target.contains(u)).collect(Collectors.toList());
                List<String> source = vcfMetadata.getReferencias().stream().filter(u -> !target.contains(u)).collect(Collectors.toList());
                list = new DualListModel<>(source, target );  
            }catch(Exception ex){
                System.out.println("VfRef Erro: " + ex);
            }
        }
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

    public DualListModel<String> getList() {
        return list;
    }

    public void setList(DualListModel<String> list) {
        this.list = list;
    }

    public Long getValue() {
        return idAnalise;
    }

    public void setValue(Long value) {
        this.idAnalise = value;
    }

    private void updateFiltro() {
        Set<String> listRef = new HashSet<>();
        for(String ref: list.getTarget()){
            listRef.add(ref);
        }
        //filtro.setByReference(!listRef.isEmpty());
        filtro.setReferencias(listRef);      
    }
}
