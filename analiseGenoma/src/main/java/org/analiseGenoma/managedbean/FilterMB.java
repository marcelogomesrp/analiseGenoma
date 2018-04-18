package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.model.Cromossomo;
import org.analiseGenoma.model.Filter;
import org.analiseGenoma.model.Filtro;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.Type;
import org.analiseGenoma.model.UmdPredictor;
import org.analiseGenoma.model.Zygosity;
import org.analiseGenoma.service.CromossomoService;
import org.analiseGenoma.service.FilterService;
import org.analiseGenoma.service.FiltroService;
import org.analiseGenoma.service.GeneService;
import org.analiseGenoma.service.TypeService;
import org.analiseGenoma.service.UmdPredictorService;
import org.analiseGenoma.service.ZygosityService;
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
    @Inject
    private UmdPredictorService umdPredictorService;
    @Inject
    private ZygosityService zygosityService;
    @Inject
    private FilterService filterfService;
    @Inject
    private TypeService typeService;

    private boolean byGene;
    private List<Gene> selectedGenes;
    private List<Type> selectedTypies;

    private boolean byChromosome;
    private List<Cromossomo> selectedChromosome;
    private List<UmdPredictor> selectedUmdPredictors;
    private List<Zygosity> selectedZygosity;
    private List<Filter> selectedFilter;
    

    private boolean byPosition;
    private Long positionMin;
    private Long positionMax;
    private boolean disabledValidation;
    private List<String> refs;
    private List<String> changeds;
    private List<String> allelics1;
    private List<String> allelics2;
    private List<String> selectedHgvsc;
    private List<String> selectedHgvsp;
    private List<String> selectedIdSNP;
    
    

    @PostConstruct
    public void init() {
        this.reset();

    }

    private void reset() {
        filterSB.reset();
        this.crudMode = CrudMode.Read;
        disabledValidation = true;
        refs = new ArrayList<>();
        changeds = new ArrayList<>();
        selectedUmdPredictors = new ArrayList<>();
        selectedZygosity = new ArrayList<>();
        allelics1 = new ArrayList<>();
        allelics2 = new ArrayList<>();
        selectedFilter = new ArrayList<>();
        selectedHgvsc = new ArrayList<>();
        selectedHgvsp = new ArrayList<>();
        selectedIdSNP = new ArrayList<>();
        selectedTypies = new ArrayList<>();
    }

    public void add() {
        defCrudModeUpdate();
    }

    public void cancel() {
        this.reset();
    }

    public void save() {
        Filtro filter = filterSB.getFilter();
        
        if(filter.isByType()){
            filter.setTypies(new HashSet<>(selectedTypies));
        }
        
        if(filter.isByHgvsp()){
            filter.setHgvsps(new HashSet<>(selectedHgvsp));
        }
        
        if(filter.isByHgvsc()){
            filter.setHgvscs(new HashSet<>(selectedHgvsc));
        }
        
        if(filter.isByFilter()){
            filter.setFilters(new HashSet<>(selectedFilter));
        }
        
        if(filter.isByZygocity()){
            filter.setZygosities( new HashSet<>(selectedZygosity));
        }
        
        if(filter.isByReference()){
            filter.setReferencias( refs.stream().map(String::toUpperCase).collect(Collectors.toSet()) );
        }
        if(filter.isByChanged()){
            filter.setChangeds( changeds.stream().map(String::toUpperCase).collect(Collectors.toSet()));
        }

        if (filter.isByGene()) {
            filter.setGenes(new HashSet<>(selectedGenes));
            System.out.println("ok");            
        }
        
        if(filter.isByAllelicDeph1()){
            filter.setAlleciDeph1s(new HashSet<>());
            for(String n: allelics1){
                filter.getAlleciDeph1s().add(Integer.valueOf(n));
            }
        }
        if(filter.isByAllelicDeph2()){
            filter.setAlleciDeph2s(new HashSet<>());
            for(String n: allelics1){
                filter.getAlleciDeph2s().add(Integer.valueOf(n));
            }
        }
        

        
        if (byChromosome) {
            filter.setByChromosome(true);
            filter.setCromossomos(new HashSet<>(selectedChromosome));
        }
        if (byPosition) {
            filter.setPositionMin(positionMin);
            filter.setPositionMax(positionMax);
        }
        
        if(filter.isByUmdPredictor()){
            filter.setUmdPredictors(new HashSet<>(selectedUmdPredictors));
        }
        
        if(filter.isByFilter()){
            filter.setFilters(new HashSet<>(selectedFilter));
        }
        

//        Gene brca1 = geneService.findBySymbol("BRCA1");
//        filter.setGenes(new HashSet<>());
//        filter.getGenes().add(brca1);
        filterService.persiste(filter);

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

    public List<Type> getSelectedTypies() {
        return selectedTypies;
    }

    public void setSelectedTypies(List<Type> selectedTypies) {
        this.selectedTypies = selectedTypies;
    }

    
    public List<Type> completeType(String query) {
        try {
            List<Type> ret = typeService.findByName(query.toUpperCase() + "%");
            return ret;
        } catch (Exception ex) {
            Logger.getLogger(FilterMB.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
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
    
    public List<UmdPredictor> completeUmdPedictor(String query) {
        try {
            List<UmdPredictor> ret = umdPredictorService.findByName(query.toUpperCase() + "%");// cromossomoService.findByName(query);
            return ret;
        } catch (Exception ex) {
            Logger.getLogger(FilterMB.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }
    
    public List<Zygosity> completeZygocityr(String query) {
        try {
            List<Zygosity> ret = zygosityService.findByName(query.toUpperCase() + "%");// cromossomoService.findByName(query);
            return ret;
        } catch (Exception ex) {
            Logger.getLogger(FilterMB.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }
    
    public List<Filter> completeFilter(String query) {
        try {
            List<Filter> ret = filterfService.findByName(query.toUpperCase() + "%");// cromossomoService.findByName(query);
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

    public List<String> getRefs() {
        return refs;
    }

    public void setRefs(List<String> refs) {
        this.refs = refs;
    }

    public List<String> getChangeds() {
        return changeds;
    }

    public void setChangeds(List<String> changeds) {
        this.changeds = changeds;
    }

    public List<UmdPredictor> getSelectedUmdPredictors() {
        return selectedUmdPredictors;
    }

    public void setSelectedUmdPredictors(List<UmdPredictor> selectedUmdPredictors) {
        this.selectedUmdPredictors = selectedUmdPredictors;
    }

    public List<Zygosity> getSelectedZygosity() {
        return selectedZygosity;
    }

    public void setSelectedZygosity(List<Zygosity> selectedZygosity) {
        this.selectedZygosity = selectedZygosity;
    }

    public List<String> getAllelics1() {
        return allelics1;
    }

    public void setAllelics1(List<String> allelics1) {
        this.allelics1 = allelics1;
    }

    public List<String> getAllelics2() {
        return allelics2;
    }

    public void setAllelics2(List<String> allelics2) {
        this.allelics2 = allelics2;
    }

    public List<Filter> getSelectedFilter() {
        return selectedFilter;
    }

    public void setSelectedFilter(List<Filter> selectedFilter) {
        this.selectedFilter = selectedFilter;
    }

    public List<String> getSelectedHgvsc() {
        return selectedHgvsc;
    }

    public void setSelectedHgvsc(List<String> selectedHgvsc) {
        this.selectedHgvsc = selectedHgvsc;
    }

    public List<String> getSelectedHgvsp() {
        return selectedHgvsp;
    }

    public void setSelectedHgvsp(List<String> selectedHgvsp) {
        this.selectedHgvsp = selectedHgvsp;
    }

    public List<String> getSelectedIdSNP() {
        return selectedIdSNP;
    }

    public void setSelectedIdSNP(List<String> selectedIdSNP) {
        this.selectedIdSNP = selectedIdSNP;
    }

    


    
    
    
}
