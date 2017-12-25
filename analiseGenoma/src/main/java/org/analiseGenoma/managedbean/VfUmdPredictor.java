package org.analiseGenoma.managedbean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import static jdk.nashorn.internal.objects.NativeRegExp.source;
import org.analiseGenoma.managedbean.util.FacesUtil;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.Filtro;
import org.analiseGenoma.model.VcfMetadata;
import org.analiseGenoma.service.AnaliseService;
import org.analiseGenoma.service.FiltroService;
import org.analiseGenoma.service.VcfMetadataService;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;

@Named(value = "VfUmdPredictor")
@RequestScoped
public class VfUmdPredictor {
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
    
    @PostConstruct
    public void init() {
        idAnalise = (Long) FacesUtil.getSessionMapValue("id");
        if (idAnalise != null) {
                Analise analise = analiseService.buscarPorId(idAnalise);
                filtro = filtroService.buscarPorAnalise(analise.getId());   
                vcfMetadata = vcfMetadataService.findByVcfId(analise.getVcf().getId());              
                //List<String> target = filtro.getUmdPredictors().stream().map(u -> u.getName()).collect(Collectors.toList());
                List<String> target = new ArrayList<>();
                List<String> source = new ArrayList<>();
                list = new DualListModel<>(source, target );                    
        }
    }

//    
//                List<String> genesTarget = filtro.getGenes().stream().map(g -> g.getSimbolo()).collect(Collectors.toList());
//                List<String> genesSource = vcfMetadata.getGenes()
//                        .stream()
//                        .map(g -> g.getSimbolo())
//                        .filter(g -> !genesTarget.contains(g))
//                        .collect(Collectors.toList());
    
    public void closeView(){
//        this.updateFiltro();
//        filtroService.atualizar(filtro);
//        variantes = vcfService.findVariante(analise, filtro);
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
    
    
}
