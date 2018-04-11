package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.model.Cromossomo;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.Patient;
import org.analiseGenoma.service.CromossomoService;
import org.analiseGenoma.service.FiltroService;
import org.analiseGenoma.service.GeneService;
import org.analiseGenoma.sessionbean.FilterSB;

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
    @Inject
    private CromossomoService cromossomoService;

    private boolean byGene;
    private List<Gene> selectedGenes;

    private boolean byChromosome;
    private List<Cromossomo> selectedChromosome;

    private boolean byPosition;
    private Long positionMin;
    private Long positionMax;
    private boolean disabledValidation;

    @PostConstruct
    public void init() {
        this.reset();

    }

    private void reset() {
        filterSB.reset();
        this.crudMode = CrudMode.Read;
        disabledValidation = true;
    }

    public void add() {
        defCrudModeUpdate();
    }

    public void cancel() {
        this.reset();
    }

    public void save() {

        if (byGene) {
            filterSB.getFilter().setGenes(new HashSet<>(selectedGenes));
        }
        if (byChromosome) {
            filterSB.getFilter().setByChromosome(true);
            filterSB.getFilter().setCromossomos(new HashSet<>(selectedChromosome));
        }
        if (byPosition) {
            filterSB.getFilter().setPositionMin(positionMin);
            filterSB.getFilter().setPositionMax(positionMax);
        }

        filterService.persiste(filterSB.getFilter());

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
        disabledValidation = false;
    }

    public boolean getShowFilds() {
        return getCrudModeUpdate() || getCrudModeFind();
    }

    public void loadGenes() {
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
        return geneService.findLikeName(query);
    }

    public boolean isByChromosome() {
        return byChromosome;
    }

    public void setByChromosome(boolean byChromosome) {
        this.byChromosome = byChromosome;
    }

    public List<Cromossomo> getSelectedChromosome() {
        return selectedChromosome;
    }

    public void setSelectedChromosome(List<Cromossomo> selectedChromosome) {
        this.selectedChromosome = selectedChromosome;
    }

    public void loadChromosome() {
        selectedChromosome = new ArrayList<>();
    }

    public List<Cromossomo> completeChromosome(String query) {
        try {
            List<Cromossomo> ret = cromossomoService.findByName(query);
            return ret;
        } catch (Exception ex) {
            Logger.getLogger(FilterMB.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }
    
    public List<String> completeReference(String query) {
        try {
            List<String> ret = new ArrayList<>(); //cromossomoService.findByName(query);
            ret.add("A");ret.add("C");ret.add("T");ret.add("G");
            return ret;
        } catch (Exception ex) {
            Logger.getLogger(FilterMB.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }

    public boolean isByPosition() {
        return byPosition;
    }

    public void setByPosition(boolean byPosition) {
        this.byPosition = byPosition;
    }

    public Long getPositionMin() {
        return positionMin;
    }

    public void setPositionMin(Long positionMin) {
        this.positionMin = positionMin;
    }

    public Long getPositionMax() {
        return positionMax;
    }

    public void setPositionMax(Long positionMax) {
        this.positionMax = positionMax;
    }

    public void validateName(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        if (disabledValidation) {
            return;
        }
        String nome = (String) o;
        if ((nome == null) || ("".equals(nome))) {
            FacesMessage message
                    = new FacesMessage("name can't be none");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
        //TODO: validar por nome unico

    }
    
    
    public void validateGenes(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        if (disabledValidation) {
            return;
        }
        List nomes =  (List) o;
        if(nomes == null){
            FacesMessage message
                    = new FacesMessage("genes can't be none");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
        //System.out.println("aqui");
//        if ((nome == null) || ("".equals(nome))) {
//            FacesMessage message
//                    = new FacesMessage("name can't be none");
//            message.setSeverity(FacesMessage.SEVERITY_ERROR);
//            throw new ValidatorException(message);
//        }
    }

}
