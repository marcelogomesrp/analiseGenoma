package org.analiseGenoma.managedbean;

import java.io.IOException;
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
import org.analiseGenoma.model.Filter;
import org.analiseGenoma.model.Filtro;
import org.analiseGenoma.model.VcfMetadata;
import org.analiseGenoma.service.AnaliseService;
import org.analiseGenoma.service.FilterService;
import org.analiseGenoma.service.FiltroService;
import org.analiseGenoma.service.VcfMetadataService;
import org.analiseGenoma.service.ZygosityService;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;

@Named(value = "VfFilter")
@RequestScoped
public class VfFilter {
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
    private FilterService filterService;
    
    @PostConstruct
    public void init() {
        idAnalise = (Long) FacesUtil.getSessionMapValue("id");
        if (idAnalise != null) {
                Analise analise = analiseService.buscarPorId(idAnalise);
                filtro = filtroService.buscarPorAnalise(analise.getId());   
                vcfMetadata = vcfMetadataService.findByVcfId(analise.getVcf().getId());              
                //List<String> target = filtro.getZygosities().stream().map(u -> u.getName()).collect(Collectors.toList());
                List<String> target = filtro.getFilters().stream().map(u -> u.getName()).collect(Collectors.toList());
                List<String> source = vcfMetadata.getFilters().stream().map(u -> u.getName()).filter(u -> !target.contains(u)).collect(Collectors.toList());
                list = new DualListModel<>(source, target );  
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
        Set<Filter> listFilter = new HashSet<>();
        for(String name: list.getTarget()){
            List<Filter> find =  filterService.findByName(name); //umdPredictorService.findByName(name);
            if(find.size() == 1)
            listFilter.add(find.get(0));
        }
        filtro.setFilters(listFilter);
    }
}
