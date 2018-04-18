package org.analiseGenoma.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "filtro")
public class Filtro  implements Serializable, Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_filtro")
    private Long id;
    @OneToOne
    @JoinColumn(name = "analise_id")
    private Analise analise;
    
    @Column(name = "by_gene")
    private boolean byGene;    
    @Column(name = "by_gene_analyse")
    private boolean geneAnalyse;
    @Column(name = "by_umd_predictor")
    private boolean byUmdPredictor;
    @Column(name = "by_zygocity")
    private boolean byZygocity;
    @Column(name = "by_allelic_deph1")
    private boolean byAllelicDeph1;
    @Column(name = "by_allelic_deph2")
    private boolean byAllelicDeph2;
    @Column(name = "by_filter")
    private boolean byFilter;
    
    @Column(name = "by_hgvsc")
    private boolean byHgvsc;
    @Column(name = "by_hgvsp")
    private boolean byHgvsp;
    @Column(name = "by_idsnp")
    private boolean byIdSNP;
    @Column(name = "by_type")
    private boolean byType;
    
    //@OneToMany
    @ManyToMany //(cascade = CascadeType.ALL)
    //(fetch = FetchType.EAGER)
    //@JoinTable(name="a_genes")
    private Set<Gene> genes;
    //@OneToMany
    @Column(name = "by_chromosome")
    private boolean byChromosome;
    @ManyToMany
    private Set<Cromossomo> cromossomos;
    

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> hgvscs;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> hgvsps;
    
//    private Double qualidadeMin;
//    private Double qualidadeMax;
    
    @Column(name = "by_position")
    private boolean byPosition;
    private Long positionMin;
    private Long positionMax;
    private String name;
    
    //@OneToMany
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Impact> impacto;
    //@OneToMany
    @ManyToMany
    private List<InformacaoBiologica> infBiologica;
    @ManyToMany
    private Set<UmdPredictor> umdPredictors;
    
    @Column(name = "by_reference")
    private boolean byReference;    
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> referencias;
    
    @Column(name = "by_changed")
    private boolean byChanged;    
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> changeds;
       
    
    @ManyToMany
    private Set<Effect> effects;

    @ManyToMany
    private Set<Zygosity> zygosities;
    
    @ManyToMany
    private Set<Filter> filters;
    
    @ManyToMany
    private Set<Type> typies;

   @ElementCollection(fetch = FetchType.EAGER)
    private Set<Integer> alleciDeph1s;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Integer> alleciDeph2s;
    
    
    private Double prevalenceMin;
    private Double prevalenceMax;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Analise getAnalise() {
        return analise;
    }

    public void setAnalise(Analise analise) {
        this.analise = analise;
    }

    public Set<Gene> getGenes() {
        return genes;
    }

    public void setGenes(Set<Gene> genes) {
        this.genes = genes;
    }

    public Set<UmdPredictor> getUmdPredictors() {
        return umdPredictors;
    }

    public void setUmdPredictors(Set<UmdPredictor> umdPredictors) {
        this.umdPredictors = umdPredictors;
    }

    public Set<Zygosity> getZygosities() {
        return zygosities;
    }

    public void setZygosities(Set<Zygosity> zygosities) {
        this.zygosities = zygosities;
    }

    public Set<Cromossomo> getCromossomos() {
        return cromossomos;
    }

    public void setCromossomos(Set<Cromossomo> cromossomos) {
        this.cromossomos = cromossomos;
    }


//    public Double getQualidadeMin() {
//        return qualidadeMin;
//    }
//
//    public void setQualidadeMin(Double qualidadeMin) {
//        this.qualidadeMin = qualidadeMin;
//    }
//
//    public Double getQualidadeMax() {
//        return qualidadeMax;
//    }
//
//    public void setQualidadeMax(Double qualidadeMax) {
//        this.qualidadeMax = qualidadeMax;
//    }

    public List<Impact> getImpacto() {
        return impacto;
    }

    public void setImpacto(List<Impact> impacto) {
        this.impacto = impacto;
    }

    public List<InformacaoBiologica> getInfBiologica() {
        return infBiologica;
    }

    public void setInfBiologica(List<InformacaoBiologica> infBiologica) {
        this.infBiologica = infBiologica;
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

    public Set<String> getReferencias() {
        return referencias;
    }

    public void setReferencias(Set<String> referencias) {
        this.referencias = referencias;
    }

    public Set<Effect> getEffects() {
        return effects;
    }

    public void setEffects(Set<Effect> effects) {
        this.effects = effects;
    }

    public Double getPrevalenceMin() {
        return prevalenceMin;
    }

    public void setPrevalenceMin(Double prevalenceMin) {
        this.prevalenceMin = prevalenceMin;
    }

    public Double getPrevalenceMax() {
        return prevalenceMax;
    }

    public void setPrevalenceMax(Double prevalenceMax) {
        this.prevalenceMax = prevalenceMax;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    public boolean isGeneAnalyse() {
        return geneAnalyse;
    }

    public void setGeneAnalyse(boolean geneAnalyse) {
        this.geneAnalyse = geneAnalyse;
    }

    public boolean isByChromosome() {
        return byChromosome;
    }

    public void setByChromosome(boolean byChromosome) {
        this.byChromosome = byChromosome;
    }

    public boolean isByPosition() {
        return byPosition;
    }

    public void setByPosition(boolean byPosition) {
        this.byPosition = byPosition;
    }

    public boolean isByGene() {
        return byGene;
    }

    public void setByGene(boolean byGene) {
        this.byGene = byGene;
    }

    public boolean isByReference() {
        return byReference;
    }

    public void setByReference(boolean byReference) {
        this.byReference = byReference;
    }

    public boolean isByChanged() {
        return byChanged;
    }

    public void setByChanged(boolean byChanged) {
        this.byChanged = byChanged;
    }

    public Set<String> getChangeds() {
        return changeds;
    }

    public void setChangeds(Set<String> changeds) {
        this.changeds = changeds;
    }

    public boolean isByUmdPredictor() {
        return byUmdPredictor;
    }

    public void setByUmdPredictor(boolean byUmdPredictor) {
        this.byUmdPredictor = byUmdPredictor;
    }

    public boolean isByZygocity() {
        return byZygocity;
    }

    public void setByZygocity(boolean byZygocity) {
        this.byZygocity = byZygocity;
    }

    public boolean isByAllelicDeph1() {
        return byAllelicDeph1;
    }

    public void setByAllelicDeph1(boolean byAllelicDeph1) {
        this.byAllelicDeph1 = byAllelicDeph1;
    }

    public boolean isByAllelicDeph2() {
        return byAllelicDeph2;
    }

    public void setByAllelicDeph2(boolean byAllelicDeph2) {
        this.byAllelicDeph2 = byAllelicDeph2;
    }

    public Set<Integer> getAlleciDeph1s() {
        return alleciDeph1s;
    }

    public void setAlleciDeph1s(Set<Integer> alleciDeph1s) {
        this.alleciDeph1s = alleciDeph1s;
    }

    public Set<Integer> getAlleciDeph2s() {
        return alleciDeph2s;
    }

    public void setAlleciDeph2s(Set<Integer> alleciDeph2s) {
        this.alleciDeph2s = alleciDeph2s;
    }

    public boolean isByFilter() {
        return byFilter;
    }

    public void setByFilter(boolean byFilter) {
        this.byFilter = byFilter;
    }

    public Set<Filter> getFilters() {
        return filters;
    }

    public void setFilters(Set<Filter> filters) {
        this.filters = filters;
    }

    public boolean isByHgvsc() {
        return byHgvsc;
    }

    public void setByHgvsc(boolean byHgvsc) {
        this.byHgvsc = byHgvsc;
    }

    public Set<String> getHgvscs() {
        return hgvscs;
    }

    public void setHgvscs(Set<String> hgvscs) {
        this.hgvscs = hgvscs;
    }

    public boolean isByHgvsp() {
        return byHgvsp;
    }

    public void setByHgvsp(boolean byHgvsp) {
        this.byHgvsp = byHgvsp;
    }

    public Set<String> getHgvsps() {
        return hgvsps;
    }

    public void setHgvsps(Set<String> hgvsps) {
        this.hgvsps = hgvsps;
    }

    public boolean isByIdSNP() {
        return byIdSNP;
    }

    public void setByIdSNP(boolean byIdSNP) {
        this.byIdSNP = byIdSNP;
    }

    public boolean isByType() {
        return byType;
    }

    public void setByType(boolean byType) {
        this.byType = byType;
    }

    public Set<Type> getTypies() {
        return typies;
    }

    public void setTypies(Set<Type> typies) {
        this.typies = typies;
    }

    
    

    @Override
    public Filtro clone() throws CloneNotSupportedException {
        Filtro cloned = new Filtro();
        cloned.setId(id);
        cloned.setName(name);
        cloned.setAnalise(analise);
        cloned.setCromossomos(new HashSet<>(cromossomos));
        cloned.setPositionMin(positionMin);
        cloned.setPositionMax(positionMax);
        cloned.setGeneAnalyse(geneAnalyse);
        cloned.setGenes(new HashSet<>(genes));
        cloned.setUmdPredictors(new HashSet<>(umdPredictors));
        cloned.setEffects(new HashSet<>(effects));
        cloned.setByChromosome(byChromosome);
        cloned.setByPosition(byPosition);
        cloned.setByUmdPredictor(byUmdPredictor);
        cloned.setByAllelicDeph1(byAllelicDeph1);
        cloned.setByAllelicDeph2(byAllelicDeph2);
        cloned.setByFilter(byFilter);
        cloned.setByHgvsc(byHgvsc);
        cloned.setByHgvsp(byHgvsp);
        cloned.setByIdSNP(byIdSNP);
        cloned.setByType(byType);
        
        
        return cloned;
    }
    
     



    
    
    @Override
    public String toString() {
        return "Filtro{" + "id=" + id + ", analise=" + analise + ", genes=" + genes + ", cromossomos=" + cromossomos + ", qualidadeMin=" + "qualidadeMin" + ", qualidadeMax=" + "qualidadeMax" + ", impacto=" + impacto + ", infBiologica=" + infBiologica + '}';
    }
    
    

    
}