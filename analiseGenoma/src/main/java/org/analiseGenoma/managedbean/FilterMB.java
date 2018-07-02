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
import org.analiseGenoma.model.ClinvarAccession;
import org.analiseGenoma.model.ClinvarAlleleOrigin;
import org.analiseGenoma.model.ClinvarAlleleType;
import org.analiseGenoma.model.ClinvarDisease;
import org.analiseGenoma.model.ClinvarSignificance;
import org.analiseGenoma.model.Cromossomo;
import org.analiseGenoma.model.Effect;
import org.analiseGenoma.model.Ensembl;
import org.analiseGenoma.model.Feature;
import org.analiseGenoma.model.Filter;
import org.analiseGenoma.model.Filtro;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.GenoType;
import org.analiseGenoma.model.Impact;
import org.analiseGenoma.model.InterproDomain;
import org.analiseGenoma.model.Lrt;
import org.analiseGenoma.model.MutationTaster;
import org.analiseGenoma.model.PolyphenHdiv;
import org.analiseGenoma.model.PolyphenHvar;
import org.analiseGenoma.model.Sift;
import org.analiseGenoma.model.Type;
import org.analiseGenoma.model.UmdPredictor;
import org.analiseGenoma.model.VariantStatus;
import org.analiseGenoma.model.Zygosity;
import org.analiseGenoma.service.ClinvarAccessionService;
import org.analiseGenoma.service.ClinvarAlleleOriginService;
import org.analiseGenoma.service.ClinvarAlleleTypeService;
import org.analiseGenoma.service.ClinvarDiseaseService;
import org.analiseGenoma.service.ClinvarSignificanceService;
import org.analiseGenoma.service.CromossomoService;
import org.analiseGenoma.service.EffectService;
import org.analiseGenoma.service.EnsemblService;
import org.analiseGenoma.service.FeatureService;
import org.analiseGenoma.service.FilterService;
import org.analiseGenoma.service.FiltroService;
import org.analiseGenoma.service.GeneService;
import org.analiseGenoma.service.GenoTypeService;
import org.analiseGenoma.service.ImpactService;
import org.analiseGenoma.service.InterproDomainService;
import org.analiseGenoma.service.LrtService;
import org.analiseGenoma.service.MutationTasterService;
import org.analiseGenoma.service.PolyphenHdivService;
import org.analiseGenoma.service.PolyphenHvarService;
import org.analiseGenoma.service.SiftService;
import org.analiseGenoma.service.TypeService;
import org.analiseGenoma.service.UmdPredictorService;
import org.analiseGenoma.service.VariantStatusService;
import org.analiseGenoma.service.VcfService;
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

    //novo import
    @Inject
    private ImpactService impactService;
    @Inject
    private VcfService vcfService;
    @Inject
    private EffectService effectService;
    @Inject
    private ClinvarSignificanceService clinvarSignificanceService;
    @Inject
    private ClinvarDiseaseService clinvarDiseaseService;
    @Inject
    private ClinvarAccessionService clinvarAccessionService;
    @Inject
    private ClinvarAlleleTypeService clinvarAlleleTypeService;
    @Inject
    private ClinvarAlleleOriginService clinvarAlleleOriginService;
    @Inject
    private SiftService siftService;
    @Inject
    private PolyphenHdivService polyphenHdivService;
    @Inject
    private PolyphenHvarService polyphenHvarService;
    @Inject
    private MutationTasterService mutationTasterService;
    @Inject
    private LrtService lrtService;

    @Inject
    private FeatureService featureService;
    @Inject
    private EnsemblService ensemblService;
    @Inject
    private InterproDomainService interproDomainService;
    @Inject
    private VariantStatusService variantStatusService;
    @Inject
    private GenoTypeService genoTypeService;

    //novo import fim
    private boolean byGene;
    private List<Gene> selectedGenes;
    private List<Type> selectedTypies;

    private boolean byChromosome;
    private List<Cromossomo> selectedChromosome;
    private List<UmdPredictor> selectedUmdPredictors;
    private List<Zygosity> selectedZygosity;
    private List<Filter> selectedFilter;
    //novos   
    private List<Effect> selectedEffects;
    private List<ClinvarSignificance> selectedClinvarSignificances;
    private List<ClinvarDisease> selectedClinvarDiseases;
    private List<ClinvarAccession> selectedClinvarAccessions;
    private List<ClinvarAlleleType> selectedClinvarAlleleTypes;
    private List<ClinvarAlleleOrigin> selectedClinvarAlleleOrigin;
    private List<Sift> selectedSifts;
    private List<PolyphenHdiv> selectedPolyphenHdivs;
    private List<PolyphenHvar> selectedPolyphenHvars;
    private List<MutationTaster> selectedMutationTasters;
    private List<Lrt> selectedLrts;
    private List<Feature> selectedFeatures;
    private List<Ensembl> selectedEnsembls;
    private List<InterproDomain> selectedInterproDomains;
    private List<VariantStatus> selectedVariantStatuses;
    private List<GenoType> selectedGenoTypes;
    

    private boolean byPosition;
    private Long positionMin;
    private Long positionMax;
    private boolean disabledValidation;
    private List<String> refs;
//    private List<String> readDepths;
//    private List<String> varintTypies;
    
    private List<String> changeds;
    private List<String> allelics1;
    private List<String> allelics2;
    private List<String> selectedHgvsc;
    private List<String> selectedHgvsp;
    private List<String> selectedIdSNP;

    //novos   
//    private List<String> gerpRsScores;
//    private List<String> gerpNeutralRate;
    private List<String> vertebrateGenomesConservationScores;
    private List<String> readDepths;
//    private List<String> alleleMutFractions;
//    private List<String> menaBaseQualities;
    private List<String> variantTypies;
    private List<String> validates;
    private List<String> donorSpliceSites;
    private List<String> mutations;
    
    private List<Filtro> filters;
    private boolean showFilters;

    //novos fim
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

        selectedEffects = new ArrayList<>();
        selectedClinvarSignificances = new ArrayList<>();
        selectedClinvarDiseases = new ArrayList<>();
        selectedClinvarAccessions = new ArrayList<>();
        selectedClinvarAlleleTypes = new ArrayList<>();
        selectedClinvarAlleleOrigin = new ArrayList<>();
        selectedSifts = new ArrayList<>();
        selectedPolyphenHdivs = new ArrayList<>();
        selectedPolyphenHvars = new ArrayList<>();
        selectedMutationTasters = new ArrayList<>();
        selectedLrts = new ArrayList<>();
        selectedFeatures = new ArrayList<>();
        selectedEnsembls = new ArrayList<>();
        selectedInterproDomains = new ArrayList<>();
        selectedVariantStatuses = new ArrayList<>();
        selectedGenoTypes = new ArrayList<>();
        filters = new ArrayList<>();
        showFilters = false;
    }

    public void add() {
        defCrudModeUpdate();
    }

    public void cancel() {
        this.reset();
    }

    public void save() {
        Filtro filter = filterSB.getFilter();
        
        
        if(filter.isByReadDepth()){
            filter.setReadDepths(new HashSet<>(readDepths));
        }
        if(filter.isByVarintType()){
            filter.setVarntTypies(new HashSet<>(variantTypies));
        }

        if (filter.isByType()) {
            filter.setTypies(new HashSet<>(selectedTypies));
        }

        if (filter.isByHgvsp()) {
            filter.setHgvsps(new HashSet<>(selectedHgvsp));
        }

        if (filter.isByHgvsc()) {
            filter.setHgvscs(new HashSet<>(selectedHgvsc));
        }

        if (filter.isByFilter()) {
            filter.setFilters(new HashSet<>(selectedFilter));
        }

        if (filter.isByZygocity()) {
            filter.setZygosities(new HashSet<>(selectedZygosity));
        }

        if (filter.isByReference()) {
            filter.setReferencias(refs.stream().map(String::toUpperCase).collect(Collectors.toSet()));
        }
        if (filter.isByChanged()) {
            filter.setChangeds(changeds.stream().map(String::toUpperCase).collect(Collectors.toSet()));
        }

        if (filter.isByGene()) {
            filter.setGenes(new HashSet<>(selectedGenes));
            System.out.println("ok");
        }

        if (filter.isByAllelicDeph1()) {
            filter.setAlleciDeph1s(new HashSet<>());
            for (String n : allelics1) {
                filter.getAlleciDeph1s().add(Integer.valueOf(n));
            }
        }
        if (filter.isByAllelicDeph2()) {
            filter.setAlleciDeph2s(new HashSet<>());
            for (String n : allelics1) {
                filter.getAlleciDeph2s().add(Integer.valueOf(n));
            }
        }

        if (filter.isByClinvarDisease()) {
            filter.setClinvardiseases(new HashSet<>(selectedClinvarDiseases));
        }
        if (filter.isByClinvarAccession()) {
            filter.setClinvarAccessions(new HashSet<>(selectedClinvarAccessions));
        }
        if (filter.isByClinvarAlleleOrigin()) {
            filter.setClinvarAlleleOrigins(new HashSet<>(selectedClinvarAlleleOrigin));
        }
        if (filter.isBySift()) {
            filter.setSifts(new HashSet<>(selectedSifts));
        }
        if (filter.isByPolyphenhdiv()) {
            filter.setPolyphenHdivs(new HashSet<>(selectedPolyphenHdivs));
        }
        if (filter.isByPolypheHvar()) {
            filter.setPolyphenHvars(new HashSet<>(selectedPolyphenHvars));
        }
        if (filter.isByMutationTaster()) {
            filter.setMutationTasters(new HashSet<>(selectedMutationTasters));
        }
        if (filter.isByFeature()) {
            filter.setFeatures(new HashSet<>(selectedFeatures));
        }
        if (filter.isByEnsembl()) {
            filter.setEnsembls(new HashSet<>(selectedEnsembls));
        }
        if (filter.isByInterproDomain()) {
            filter.setInterproDomains(new HashSet<>(selectedInterproDomains));
        }
        if (filter.isByVariantStatus()) {
            filter.setVariantStatuses(new HashSet<>(selectedVariantStatuses));
        }
        if (filter.isByGenoType()) {
            filter.setGenoTypes(new HashSet<>(selectedGenoTypes));
        }
        if (filter.isByEffect()) {
            filter.setEffects(new HashSet<>(selectedEffects));
        }

        if (byChromosome) {
            filter.setByChromosome(true);
            filter.setCromossomos(new HashSet<>(selectedChromosome));
        }
        if (byPosition) {
            filter.setPositionMin(positionMin);
            filter.setPositionMax(positionMax);
        }

        if (filter.isByUmdPredictor()) {
            filter.setUmdPredictors(new HashSet<>(selectedUmdPredictors));
        }

        if (filter.isByFilter()) {
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

    public void list() {
        filterSB.setFilter(new Filtro());
        filters = filterService.find();        
        showFilters = true;
        System.out.println("List: total " + filters.size());
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
        filterSB.setFilter(new Filtro());
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
            ret.add("A");
            ret.add("C");
            ret.add("T");
            ret.add("G");
            return ret;
        } catch (Exception ex) {
            Logger.getLogger(FilterMB.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }

    //novos complete
    public List<Impact> completeImpact(String query) {
        try {
            List<Impact> ret = impactService.findByName(query.toUpperCase() + "%");
            return ret;
        } catch (Exception ex) {
            Logger.getLogger(FilterMB.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }

    public List<ClinvarSignificance> completeClinvarSignificance(String query) {
        try {
            List<ClinvarSignificance> ret = clinvarSignificanceService.findByName(query.toUpperCase() + "%");
            return ret;
        } catch (Exception ex) {
            Logger.getLogger(FilterMB.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }

    public List<Effect> completeEffect(String query) {
        try {
            List<Effect> ret = effectService.findByName(query.toUpperCase() + "%");
            return ret;
        } catch (Exception ex) {
            Logger.getLogger(FilterMB.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }

    public List<ClinvarDisease> completeClinvarDisease(String query) {
        try {
            List<ClinvarDisease> ret = clinvarDiseaseService.findByName(query.toUpperCase() + "%");
            return ret;
        } catch (Exception ex) {
            Logger.getLogger(FilterMB.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }

    public List<ClinvarAccession> completeClinvarAccession(String query) {
        try {
            List<ClinvarAccession> ret = clinvarAccessionService.findByName(query.toUpperCase() + "%");
            return ret;
        } catch (Exception ex) {
            Logger.getLogger(FilterMB.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }

    public List<ClinvarAlleleType> completeClinvarAlleleType(String query) {
        try {
            List<ClinvarAlleleType> ret = clinvarAlleleTypeService.findByName(query.toUpperCase() + "%");
            return ret;
        } catch (Exception ex) {
            Logger.getLogger(FilterMB.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }
    
    public List<ClinvarAlleleOrigin> completeClinvarAlleleOrigin(String query) {
        try {
            List<ClinvarAlleleOrigin> ret = clinvarAlleleOriginService.findByName(query.toUpperCase() + "%");
            return ret;
        } catch (Exception ex) {
            Logger.getLogger(FilterMB.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }

    public List<Sift> completeSift(String query) {
        try {
            List<Sift> ret = siftService.findByName(query.toUpperCase() + "%");
            return ret;
        } catch (Exception ex) {
            Logger.getLogger(FilterMB.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }

    public List<PolyphenHdiv> completePolyphenHdiv(String query) {
        try {
            List<PolyphenHdiv> ret = polyphenHdivService.findByName(query.toUpperCase() + "%");
            return ret;
        } catch (Exception ex) {
            Logger.getLogger(FilterMB.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }

    public List<PolyphenHvar> completePolyphenHvar(String query) {
        try {
            List<PolyphenHvar> ret = polyphenHvarService.findByName(query.toUpperCase() + "%");
            return ret;
        } catch (Exception ex) {
            Logger.getLogger(FilterMB.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }

    public List<MutationTaster> completeMutationTaster(String query) {
        try {
            List<MutationTaster> ret = mutationTasterService.findByName(query.toUpperCase() + "%");
            return ret;
        } catch (Exception ex) {
            Logger.getLogger(FilterMB.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }

    public List<Lrt> completeLrt(String query) {
        try {
            List<Lrt> ret = lrtService.findByName(query.toUpperCase() + "%");
            return ret;
        } catch (Exception ex) {
            Logger.getLogger(FilterMB.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }

    public List<Ensembl> completeEnsembl(String query) {
        try {
            List<Ensembl> ret = ensemblService.findByName(query.toUpperCase() + "%");
            return ret;
        } catch (Exception ex) {
            Logger.getLogger(FilterMB.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }

    public List<InterproDomain> completeInterproDomain(String query) {
        try {
            List<InterproDomain> ret = interproDomainService.findByName(query.toUpperCase() + "%");
            return ret;
        } catch (Exception ex) {
            Logger.getLogger(FilterMB.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }

    public List<VariantStatus> completeVariantStatus(String query) {
        try {
            List<VariantStatus> ret = variantStatusService.findByName(query.toUpperCase() + "%");
            return ret;
        } catch (Exception ex) {
            Logger.getLogger(FilterMB.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }

    public List<GenoType> completeGenoType(String query) {
        try {
            List<GenoType> ret = genoTypeService.findByName(query.toUpperCase() + "%");
            return ret;
        } catch (Exception ex) {
            Logger.getLogger(FilterMB.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }

    //novos complet fim
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
        List nomes = (List) o;
        if (nomes == null) {
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

    public List<Effect> getSelectedEffects() {
        return selectedEffects;
    }

    public void setSelectedEffects(List<Effect> selectedEffects) {
        this.selectedEffects = selectedEffects;
    }

    public List<ClinvarSignificance> getSelectedClinvarSignificances() {
        return selectedClinvarSignificances;
    }

    public void setSelectedClinvarSignificances(List<ClinvarSignificance> selectedClinvarSignificances) {
        this.selectedClinvarSignificances = selectedClinvarSignificances;
    }

    public List<ClinvarDisease> getSelectedClinvarDiseases() {
        return selectedClinvarDiseases;
    }

    public void setSelectedClinvarDiseases(List<ClinvarDisease> selectedClinvarDiseases) {
        this.selectedClinvarDiseases = selectedClinvarDiseases;
    }

    public List<ClinvarAccession> getSelectedClinvarAccessions() {
        return selectedClinvarAccessions;
    }

    public void setSelectedClinvarAccessions(List<ClinvarAccession> selectedClinvarAccessions) {
        this.selectedClinvarAccessions = selectedClinvarAccessions;
    }

    public List<ClinvarAlleleType> getSelectedClinvarAlleleTypes() {
        return selectedClinvarAlleleTypes;
    }

    public void setSelectedClinvarAlleleTypes(List<ClinvarAlleleType> selectedClinvarAlleleTypes) {
        this.selectedClinvarAlleleTypes = selectedClinvarAlleleTypes;
    }

    public List<ClinvarAlleleOrigin> getSelectedClinvarAlleleOrigin() {
        return selectedClinvarAlleleOrigin;
    }

    public void setSelectedClinvarAlleleOrigin(List<ClinvarAlleleOrigin> selectedClinvarAlleleOrigin) {
        this.selectedClinvarAlleleOrigin = selectedClinvarAlleleOrigin;
    }

    public List<Sift> getSelectedSifts() {
        return selectedSifts;
    }

    public void setSelectedSifts(List<Sift> selectedSifts) {
        this.selectedSifts = selectedSifts;
    }

    public List<PolyphenHdiv> getSelectedPolyphenHdivs() {
        return selectedPolyphenHdivs;
    }

    public void setSelectedPolyphenHdivs(List<PolyphenHdiv> selectedPolyphenHdivs) {
        this.selectedPolyphenHdivs = selectedPolyphenHdivs;
    }

    public List<PolyphenHvar> getSelectedPolyphenHvars() {
        return selectedPolyphenHvars;
    }

    public void setSelectedPolyphenHvars(List<PolyphenHvar> selectedPolyphenHvars) {
        this.selectedPolyphenHvars = selectedPolyphenHvars;
    }

    public List<MutationTaster> getSelectedMutationTasters() {
        return selectedMutationTasters;
    }

    public void setSelectedMutationTasters(List<MutationTaster> selectedMutationTasters) {
        this.selectedMutationTasters = selectedMutationTasters;
    }

    public List<Lrt> getSelectedLrts() {
        return selectedLrts;
    }

    public void setSelectedLrts(List<Lrt> selectedLrts) {
        this.selectedLrts = selectedLrts;
    }

    public List<Feature> getSelectedFeatures() {
        return selectedFeatures;
    }

    public void setSelectedFeatures(List<Feature> selectedFeatures) {
        this.selectedFeatures = selectedFeatures;
    }

    public List<Ensembl> getSelectedEnsembls() {
        return selectedEnsembls;
    }

    public void setSelectedEnsembls(List<Ensembl> selectedEnsembls) {
        this.selectedEnsembls = selectedEnsembls;
    }

    public List<InterproDomain> getSelectedInterproDomains() {
        return selectedInterproDomains;
    }

    public void setSelectedInterproDomains(List<InterproDomain> selectedInterproDomains) {
        this.selectedInterproDomains = selectedInterproDomains;
    }

    public List<VariantStatus> getSelectedVariantStatuses() {
        return selectedVariantStatuses;
    }

    public void setSelectedVariantStatuses(List<VariantStatus> selectedVariantStatuses) {
        this.selectedVariantStatuses = selectedVariantStatuses;
    }

    public List<GenoType> getSelectedGenoTypes() {
        return selectedGenoTypes;
    }

    public void setSelectedGenoTypes(List<GenoType> selectedGenoTypes) {
        this.selectedGenoTypes = selectedGenoTypes;
    }

    public List<String> getReadDepths() {
        return readDepths;
    }

    public void setReadDepths(List<String> readDepths) {
        this.readDepths = readDepths;
    }

    public List<String> getVariantTypies() {
        return variantTypies;
    }

    public void setVariantTypies(List<String> variantTypies) {
        this.variantTypies = variantTypies;
    }

    public List<Filtro> getFilters() {
        return filters;
    }

    public void setFilters(List<Filtro> filters) {
        this.filters = filters;
    }
    
    public void detail(Filtro f){
        Filtro fall = filterService.loadFull(f);
        filterSB.setFilter(fall);
        System.out.println("Detail runed");
        showFilters = false;
    }

    public boolean isShowFilters() {
        return showFilters;
    }

    public void setShowFilters(boolean showFilters) {
        this.showFilters = showFilters;
    }
    
    public void showHelperName(){
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("This name will..."));
    }

    
    
}
