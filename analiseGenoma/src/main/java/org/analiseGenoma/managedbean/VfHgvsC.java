package org.analiseGenoma.managedbean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
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
import org.analiseGenoma.dao.DAO;
import org.analiseGenoma.managedbean.util.FacesUtil;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.Filtro;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.VcfMetadata;
import org.analiseGenoma.service.AnaliseService;
import org.analiseGenoma.service.CromossomoService;
import org.analiseGenoma.service.FiltroService;
import org.analiseGenoma.service.VcfMetadataService;
import org.analiseGenoma.sessionbean.AnaliseSB;
import org.analiseGenoma.sessionbean.FilterSB;
import org.analiseGenoma.sessionbean.VcfMetadataSB;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;

@Named(value = "VfHgvsC")
@RequestScoped
public class VfHgvsC {
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
    @Inject
    private VcfMetadataSB vcfMetadataSB;
    
    
    
    
    private List<String> selecteds;

    public List<String> getSelecteds() {
        return selecteds;
    }

    public void setSelecteds(List<String> selecteds) {
        this.selecteds = selecteds;
    }
    
    public List<String> complete(String query) {
        System.out.println("total: " + vcfMetadata.getHgvsCs().size());
        List<String> v =  vcfMetadata.getHgvsCs()
                .stream()
                .filter(h -> h.startsWith(query.toUpperCase()))
                .limit(5)
                .collect(Collectors.toList());
        
        return v;
        
//        vcfMetadata.getHgvsCs().stream().filter(predicate)
                
//                 vcfMetadataService.findLikeName("hgvsCs", query).stream().map(v -> v.getHgvsCs())
//                .map(v -> v.getWholeVarintFreq() )
//                .distinct()
//                .filter(v -> Objects.nonNull(v))
//                .collect(Collectors.toSet());
        //return vcfMetadataService.findHgvsC(query);
        //List retorno = new ArrayList<>(vcfMetadata.getHgvsCs());
        //return retorno;
        //Set<String> retorno = vcfMetadataService.findLikeName("hgvsCs", query).stream().map(v -> v.getHgvsCs())
        //        .collect(Collectors.toList()).get(0);
        //return new ArrayList<>(retorno);
    }
    
    @PostConstruct
    public void init() {
            try{
                Analise analise = analiseSB.getAnalise();
                filtro = filterSB.getFilter();
                selecteds = new ArrayList<String>(filtro.getHgvscs());
                vcfMetadata = vcfMetadataSB.getVcfMetadata();
                                        
            }catch(Exception ex){
                System.out.println("-------------->5");
                System.out.println("VfRef Erro: " + ex);
            }
        //}
    }
    
    public void closeView(){
        filtro.setHgvscs(new HashSet<>(selecteds));
        //this.updateFiltro();
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
        Set<String> newList = new HashSet<>();
        for(String valor: list.getTarget()){
            newList.add(valor);
        }
        filtro.setHgvscs(newList);  
    }
}
