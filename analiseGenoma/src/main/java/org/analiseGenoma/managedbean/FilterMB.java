package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.service.FiltroService;
import org.analiseGenoma.service.GeneService;
import org.analiseGenoma.sessionbean.FilterSB;
import org.primefaces.model.DualListModel;

@Named(value = "filterMB")
@ViewScoped
public class FilterMB implements Serializable {

    @Inject
    private FacesContext context;
    private CrudMode crudMode;
    @Inject
    private FilterSB filterSB;
    @Inject
    private FiltroService filterService;
    @Inject
    private GeneService geneService;

    
    private boolean byGene;
    private List<Gene> selectedGenes;
    
    @PostConstruct
    public void init() {
        this.reset();
        
    }

    private void reset() {
        filterSB.reset();
        this.crudMode = CrudMode.Read;
    }

    public void add() {
        defCrudModeUpdate();
    }

    public void cancel() {
        this.reset();
    }

    public void save() {
        filterService.persiste(filterSB.getFilter());
        if(byGene){
            filterSB.getFilter().setGenes((Set<Gene>) selectedGenes);
        }
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("it successfully saved"));
        this.reset();
    }

    public boolean getCrudModeRead() {
        return crudMode == CrudMode.Read;
    }

    public void defCrudModeRead() {
        crudMode = CrudMode.Read;
    }

    public boolean getCrudModeFind() {
        return crudMode == CrudMode.Find;
    }

    public void defCrudModeFind() {
        crudMode = CrudMode.Find;
    }

    public boolean getCrudModeUpdate() {
        return crudMode == CrudMode.Update;
    }

    public void defCrudModeUpdate() {
        crudMode = CrudMode.Update;
    }

    public boolean getShowFilds() {
        return getCrudModeUpdate() || getCrudModeFind();
    }
    
    
    
    public void loadGenes(){
        selectedGenes = new ArrayList<>();
    }

    public boolean isByGene() {
        return byGene;
    }

    public void setByGene(boolean byGene) {
        this.byGene = byGene;
    }

    public List<Gene> getSelectedGenes() {
        return selectedGenes;
    }

    public void setSelectedGenes(List<Gene> selectedGenes) {
        this.selectedGenes = selectedGenes;
    }
    
    public List<Gene> completeGene(String query) { 
        return geneService.findLikeName(query).subList(0, 10);
//        Gene g = new Gene();
//        g.setSymbol(query + "%");
//        try {
//            List<Gene> gs = geneService.findByExample(g);
//            return gs;
//        } catch (Exception ex) {
//            return new ArrayList<>();
//        }
    }   

}
