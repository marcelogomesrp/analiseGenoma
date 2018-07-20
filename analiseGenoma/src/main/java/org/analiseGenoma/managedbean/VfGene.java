package org.analiseGenoma.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
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
import org.analiseGenoma.model.Filtro;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.service.FiltroService;
import org.analiseGenoma.service.GeneService;
import org.analiseGenoma.sessionbean.FilterSB;
import org.primefaces.context.RequestContext;

@Named(value = "VfGene")
@RequestScoped
public class VfGene implements Serializable {

    @Inject
    private GeneService geneService;
    private List<Gene> selecteds;
    @Inject
    private FilterSB filterSB;
    @Inject
    private FiltroService filtroService;

    @PostConstruct
    public void init() {
        selecteds = new ArrayList<>(filterSB.getFilter().getGenes());
        selecteds.sort((g1, g2) -> g1.getSymbol().compareTo(g2.getSymbol()));
    }

    public void closeView() {
        Filtro filtro = filterSB.getFilter();
        filtro.setGenes(new HashSet<>(selecteds));
        filtroService.merge(filtro);
        RequestContext.getCurrentInstance().closeDialog("It's done");
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException ex) {
            Logger.getLogger(AnaliseSelecionarVarianteMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Gene> complete(String query) {
        return geneService.findLikeName(query);
    }

    public List<Gene> getSelecteds() {
        return selecteds;
    }

    public void setSelecteds(List<Gene> selecteds) {
        this.selecteds = selecteds;
    }

}
