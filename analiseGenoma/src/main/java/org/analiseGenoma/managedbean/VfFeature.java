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
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.Feature;
import org.analiseGenoma.model.Filtro;
import org.analiseGenoma.model.Lrt;
import org.analiseGenoma.model.VcfMetadata;
import org.analiseGenoma.service.FeatureService;
import org.analiseGenoma.service.FiltroService;
import org.analiseGenoma.service.LrtService;
import org.analiseGenoma.sessionbean.AnaliseSB;
import org.analiseGenoma.sessionbean.FilterSB;
import org.analiseGenoma.sessionbean.VcfMetadataSB;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;

@Named(value = "VfFeature")
@RequestScoped
public class VfFeature {

    private DualListModel<String> list;
    private Long idAnalise;
    private VcfMetadata vcfMetadata;
    private Filtro filtro;

    @Inject
    private FiltroService filtroService;
    @Inject
    private AnaliseSB analiseSB;
    @Inject
    private FilterSB filterSB;
    @Inject
    private VcfMetadataSB metadataSB;
    @Inject
    private FeatureService service;

    @PostConstruct
    public void init() {
        try {
            Analise analise = analiseSB.getAnalise();
            filtro = filterSB.getFilter();
            vcfMetadata = metadataSB.getVcfMetadata();
            List<String> target = filtro.getFeatures().stream().map(u -> u.getName()).sorted().collect(Collectors.toList());
            List<String> source = vcfMetadata.getFeatures().stream().map(u -> u.getName()).filter(u -> !target.contains(u)).sorted().collect(Collectors.toList());
            list = new DualListModel<>(source, target);
        } catch (Exception ex) {
            System.out.println("VfFeature.init Erro: " + ex);
        }
    }

    public void closeView() {
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
        Set<Feature> listRef = new HashSet<>();
        for (String ref : list.getTarget()) {
            List<Feature> tmp = service.findByName(ref);
            if (!tmp.isEmpty()) {
                listRef.add(tmp.get(0));
            }
        }
//        filtro.setByType(!listRef.isEmpty());
        filtro.setFeatures(listRef);
    }
}
