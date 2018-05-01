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
    
    
    
    //novo inicio
    @Column(name = "by_cromossomo")
    private boolean byCromossomo;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Double> qualidades;
    @Column(name = "by_qualidade")
    private boolean byQualidade;
    
    @ManyToMany
    private Set<Impact> impacts;
    @Column(name = "by_impact")
    private boolean byImpact;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Integer> exonintrons;
    @Column(name = "by_exonintron")
    private boolean byExonIntron;
    
    @ManyToMany
    private Set<Effect> effects;
    @Column(name = "by_effect")
    private boolean byEffect;
    
    @ManyToMany
    private Set<ClinvarSignificance> clinvarsignificances;
    @Column(name = "by_clinvarsignificance")
    private boolean byCinvarSignificance;
    
    @ManyToMany
    private Set<ClinvarDisease> clinvardiseases;
    @Column(name = "by_clinvardisease")
    private boolean byClinvarDisease;
    
    @ManyToMany
    private Set<ClinvarAccession> clinvarAccessions;
    @Column(name = "by_clinvaraccession")
    private boolean byClinvarAccession;
    
    @ManyToMany
    private Set<ClinvarAlleleType> clinvarAlleleTypes;
    @Column(name = "by_clinvaralleletype")
    private boolean byClinvarAlleleType;
    
    @ManyToMany
    private Set<ClinvarAlleleOrigin> clinvarAlleleOrigins;
    @Column(name = "by_clinvaralleleorigin")
    private boolean byClinvarAlleleOrigin;
    
    @ManyToMany
    private Set<Sift> sifts;
    @Column(name = "by_sift")
    private boolean bySift;
    
    @ManyToMany
    private Set<PolyphenHdiv> polyphenHdivs;
    @Column(name = "by_polyphenhdiv")
    private boolean byPolyphenhdiv;
    
    @ManyToMany
    private Set<PolyphenHvar> polyphenHvars;
    @Column(name = "by_polyphenhvar")
    private boolean byPolypheHvar;
    
    @ManyToMany
    private Set<MutationTaster> mutationTasters;
    @Column(name = "by_mutationtaster")
    private boolean byMutationTaster;
    
    @ManyToMany
    private Set<Lrt> lrts;
    @Column(name = "by_lrt")
    private boolean byLrt;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Double> gerpScores ;
    @Column(name = "by_gerprsscore")
    private boolean bygerpRsScore;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Double> gerpNeutralRates;
    @Column(name = "by_gerpneutralrate")
    private boolean byGerpNeutralRate;
    
    @ManyToMany
    private Set<Feature> features;
    @Column(name = "by_feature")
    private boolean byFeature;
    
    @ManyToMany
    private Set<Ensembl> ensembls;
    @Column(name = "by_ensembl")
    private boolean byEnsembl;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Double> vertebrateGenomesConservationScore;
    @Column(name = "by_vertebrategenomesconservationscore")
    private boolean byVertebrateGenomeConservationScore;
    
    @ManyToMany
    private Set<InterproDomain> interproDomains;
    @Column(name = "by_interprodomain")
    private boolean byInterproDomain;
    
    @ManyToMany
    private Set<VariantStatus> variantStatuses;
    @Column(name = "by_variantstatus")
    private boolean byVariantStatus;
    
    @ManyToMany
    private Set<GenoType> genoTypes;
    @Column(name = "by_genoType")
    private boolean byGenoType;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> readDepths;
    @Column(name = "by_readdepth")
    private boolean byReadDepth;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Double> alleleMutFractions;
    @Column(name = "by_allelemutfraction")
    private boolean byAlleleMutFraction;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Double> meansbasequalities;
    @Column(name = "by_meansbasequality")
    private boolean byMeansBaseQyality;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> varntTypies;
    @Column(name = "by_varntType")
    private boolean byVarintType;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Boolean> validates;
    @Column(name = "by_validate")
    private boolean byValidate;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Boolean> donorSpliceSites;
    @Column(name = "by_donorsplicesite")
    private boolean byDonorSpliceSite;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Boolean> mutations;
    @Column(name = "by_mutation")
    private boolean byMutation;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Double> europeanVariantFreqs;
    @Column(name = "by_europeanvariantfreq")
    private boolean byEuropeanVariantFreq;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Double> AfricanVariantFreqs;
    @Column(name = "by_africanvariantfreq")
    private boolean byAfricanvariantfreq;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Double> asianVariantFreqs;
    @Column(name = "by_asianvariantfreq")
    private boolean byAsianVariantFreq;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Double> americanVariantFreqs;
    @Column(name = "by_americanvariantfreq")
    private boolean byAmericanVariantFreq;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Double> WholeVariantFreqs;
    @Column(name = "by_wholevariantfreq")
    private boolean byWholeVariantFreq;
    //novo fim
    
    
    
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
    
    //novo2
    private Double gerpRsScoreMin;
    private Double gerpRsScoreMax;
    private Double gerpNeutralRateMin;
    private Double gerpNeutralRateMax;
    private Double vertebrateGenomesConservationScoreMin;
    private Double vertebrateGenomesConservationScoreMax;
    private Double alleleMutFractionMin;
    private Double alleleMutFractionMax;
    private Double meanBaseQualityMin;
    private Double meanBaseQualityMax;
    private Double europeanVariantFreqMin;
    private Double europeanVariantFreqMax;
    private Double africanVariantFreqMin;
    private Double africanVariantFreqMax;
    private Double asianVariantFreqMin;
    private Double asianVariantFreqMax;
    private Double americanVariantFreqMin;
    private Double americanVariantFreqMax;
    private Double wholeVariantFreqMin;
    private Double wholeVariantFreqMax;
    
    private Boolean validate;
    private Boolean donorSpliceSite;      
    private Boolean acceptorSpliceSite;
    @Column(name = "by_byacceptorsplicesite")
    private Boolean byAcceptorSpliceSite;
    private Boolean mutation;
    
    
    
    //@OneToMany
//    @ManyToMany(fetch = FetchType.EAGER)
//    private List<Impact> impacto;
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

//    public List<Impact> getImpacto() {
//        return impacto;
//    }
//
//    public void setImpacto(List<Impact> impacto) {
//        this.impacto = impacto;
//    }

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
        Set<String> nova = new HashSet<>();
        
        hgvscs.forEach((s) -> {
            nova.add( s.replaceAll("&gt;", ">") );
        });
        this.hgvscs = nova;
        
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
        cloned.setByClinvarAlleleType(byClinvarAlleleType);
        
        
        return cloned;
    }
    
     
//get and set novo 
    
//get and set novo fim

    public boolean isByCromossomo() {
        return byCromossomo;
    }

    public void setByCromossomo(boolean byCromossomo) {
        this.byCromossomo = byCromossomo;
    }

    public boolean isByQualidade() {
        return byQualidade;
    }

    public void setByQualidade(boolean byQualidade) {
        this.byQualidade = byQualidade;
    }

    public boolean isByImpact() {
        return byImpact;
    }

    public void setByImpact(boolean byImpact) {
        this.byImpact = byImpact;
    }

    public boolean isByExonIntron() {
        return byExonIntron;
    }

    public void setByExonIntron(boolean byExonIntron) {
        this.byExonIntron = byExonIntron;
    }

    public boolean isByEffect() {
        return byEffect;
    }

    public void setByEffect(boolean byEffect) {
        this.byEffect = byEffect;
    }

    public boolean isByCinvarSignificance() {
        return byCinvarSignificance;
    }

    public void setByCinvarSignificance(boolean byCinvarSignificance) {
        this.byCinvarSignificance = byCinvarSignificance;
    }

    public boolean isByClinvarDisease() {
        return byClinvarDisease;
    }

    public void setByClinvarDisease(boolean byClinvarDisease) {
        this.byClinvarDisease = byClinvarDisease;
    }

    public boolean isByClinvarAccession() {
        return byClinvarAccession;
    }

    public void setByClinvarAccession(boolean byClinvarAccession) {
        this.byClinvarAccession = byClinvarAccession;
    }

    public boolean isByClinvarAlleleOrigin() {
        return byClinvarAlleleOrigin;
    }

    public void setByClinvarAlleleOrigin(boolean byClinvarAlleleOrigin) {
        this.byClinvarAlleleOrigin = byClinvarAlleleOrigin;
    }

    public boolean isBySift() {
        return bySift;
    }

    public void setBySift(boolean bySift) {
        this.bySift = bySift;
    }

    public boolean isByPolyphenhdiv() {
        return byPolyphenhdiv;
    }

    public void setByPolyphenhdiv(boolean byPolyphenhdiv) {
        this.byPolyphenhdiv = byPolyphenhdiv;
    }

    public boolean isByPolypheHvar() {
        return byPolypheHvar;
    }

    public void setByPolypheHvar(boolean byPolypheHvar) {
        this.byPolypheHvar = byPolypheHvar;
    }

    public boolean isByMutationTaster() {
        return byMutationTaster;
    }

    public void setByMutationTaster(boolean byMutationTaster) {
        this.byMutationTaster = byMutationTaster;
    }

    public boolean isByLrt() {
        return byLrt;
    }

    public void setByLrt(boolean byLrt) {
        this.byLrt = byLrt;
    }

    public boolean isBygerpRsScore() {
        return bygerpRsScore;
    }

    public void setBygerpRsScore(boolean bygerpRsScore) {
        this.bygerpRsScore = bygerpRsScore;
    }

    public boolean isByGerpNeutralRate() {
        return byGerpNeutralRate;
    }

    public void setByGerpNeutralRate(boolean byGerpNeutralRate) {
        this.byGerpNeutralRate = byGerpNeutralRate;
    }

    public boolean isByFeature() {
        return byFeature;
    }

    public void setByFeature(boolean byFeature) {
        this.byFeature = byFeature;
    }

    public boolean isByEnsembl() {
        return byEnsembl;
    }

    public void setByEnsembl(boolean byEnsembl) {
        this.byEnsembl = byEnsembl;
    }

    public boolean isByVertebrateGenomeConservationScore() {
        return byVertebrateGenomeConservationScore;
    }

    public void setByVertebrateGenomeConservationScore(boolean byVertebrateGenomeConservationScore) {
        this.byVertebrateGenomeConservationScore = byVertebrateGenomeConservationScore;
    }

    public boolean isByInterproDomain() {
        return byInterproDomain;
    }

    public void setByInterproDomain(boolean byInterproDomain) {
        this.byInterproDomain = byInterproDomain;
    }

    public boolean isByVariantStatus() {
        return byVariantStatus;
    }

    public void setByVariantStatus(boolean byVariantStatus) {
        this.byVariantStatus = byVariantStatus;
    }

    public boolean isByGenoType() {
        return byGenoType;
    }

    public void setByGenoType(boolean byGenoType) {
        this.byGenoType = byGenoType;
    }

    public boolean isByReadDepth() {
        return byReadDepth;
    }

    public void setByReadDepth(boolean byReadDepth) {
        this.byReadDepth = byReadDepth;
    }

    public boolean isByAlleleMutFraction() {
        return byAlleleMutFraction;
    }

    public void setByAlleleMutFraction(boolean byAlleleMutFraction) {
        this.byAlleleMutFraction = byAlleleMutFraction;
    }

    public boolean isByMeansBaseQyality() {
        return byMeansBaseQyality;
    }

    public void setByMeansBaseQyality(boolean byMeansBaseQyality) {
        this.byMeansBaseQyality = byMeansBaseQyality;
    }

    public boolean isByVarintType() {
        return byVarintType;
    }

    public void setByVarintType(boolean byVarintType) {
        this.byVarintType = byVarintType;
    }

    public boolean isByValidate() {
        return byValidate;
    }

    public void setByValidate(boolean byValidate) {
        this.byValidate = byValidate;
    }

    public boolean isByDonorSpliceSite() {
        return byDonorSpliceSite;
    }

    public void setByDonorSpliceSite(boolean byDonorSpliceSite) {
        this.byDonorSpliceSite = byDonorSpliceSite;
    }

    public boolean isByMutation() {
        return byMutation;
    }

    public void setByMutation(boolean byMutation) {
        this.byMutation = byMutation;
    }

    public boolean isByEuropeanVariantFreq() {
        return byEuropeanVariantFreq;
    }

    public void setByEuropeanVariantFreq(boolean byEuropeanVariantFreq) {
        this.byEuropeanVariantFreq = byEuropeanVariantFreq;
    }

    public boolean isByAfricanvariantfreq() {
        return byAfricanvariantfreq;
    }

    public void setByAfricanvariantfreq(boolean byAfricanvariantfreq) {
        this.byAfricanvariantfreq = byAfricanvariantfreq;
    }

    public boolean isByAsianVariantFreq() {
        return byAsianVariantFreq;
    }

    public void setByAsianVariantFreq(boolean byAsianVariantFreq) {
        this.byAsianVariantFreq = byAsianVariantFreq;
    }

    public boolean isByAmericanVariantFreq() {
        return byAmericanVariantFreq;
    }

    public void setByAmericanVariantFreq(boolean byAmericanVariantFreq) {
        this.byAmericanVariantFreq = byAmericanVariantFreq;
    }

    public boolean isByWholeVariantFreq() {
        return byWholeVariantFreq;
    }

    public void setByWholeVariantFreq(boolean byWholeVariantFreq) {
        this.byWholeVariantFreq = byWholeVariantFreq;
    }

    public Set<Double> getQualidades() {
        return qualidades;
    }

    public void setQualidades(Set<Double> qualidades) {
        this.qualidades = qualidades;
    }

    public Set<Impact> getImpacts() {
        return impacts;
    }

    public void setImpacts(Set<Impact> impacts) {
        this.impacts = impacts;
    }

    public Set<Integer> getExonintrons() {
        return exonintrons;
    }

    public void setExonintrons(Set<Integer> exonintrons) {
        this.exonintrons = exonintrons;
    }

    public Set<ClinvarSignificance> getClinvarsignificances() {
        return clinvarsignificances;
    }

    public void setClinvarsignificances(Set<ClinvarSignificance> clinvarsignificances) {
        this.clinvarsignificances = clinvarsignificances;
    }

    public Set<ClinvarDisease> getClinvardiseases() {
        return clinvardiseases;
    }

    public void setClinvardiseases(Set<ClinvarDisease> clinvardiseases) {
        this.clinvardiseases = clinvardiseases;
    }

    public Set<ClinvarAccession> getClinvarAccessions() {
        return clinvarAccessions;
    }

    public void setClinvarAccessions(Set<ClinvarAccession> clinvarAccessions) {
        this.clinvarAccessions = clinvarAccessions;
    }

    public Set<ClinvarAlleleOrigin> getClinvarAlleleOrigins() {
        return clinvarAlleleOrigins;
    }

    public void setClinvarAlleleOrigins(Set<ClinvarAlleleOrigin> clinvarAlleleOrigins) {
        this.clinvarAlleleOrigins = clinvarAlleleOrigins;
    }

    public Set<Sift> getSifts() {
        return sifts;
    }

    public void setSifts(Set<Sift> sifts) {
        this.sifts = sifts;
    }

    public Set<PolyphenHdiv> getPolyphenHdivs() {
        return polyphenHdivs;
    }

    public void setPolyphenHdivs(Set<PolyphenHdiv> polyphenHdivs) {
        this.polyphenHdivs = polyphenHdivs;
    }

    public Set<PolyphenHvar> getPolyphenHvars() {
        return polyphenHvars;
    }

    public void setPolyphenHvars(Set<PolyphenHvar> polyphenHvars) {
        this.polyphenHvars = polyphenHvars;
    }

    public Set<MutationTaster> getMutationTasters() {
        return mutationTasters;
    }

    public void setMutationTasters(Set<MutationTaster> mutationTasters) {
        this.mutationTasters = mutationTasters;
    }

    public Set<Lrt> getLrts() {
        return lrts;
    }

    public void setLrts(Set<Lrt> lrts) {
        this.lrts = lrts;
    }

    public Set<Double> getGerpScores() {
        return gerpScores;
    }

    public void setGerpScores(Set<Double> gerpScores) {
        this.gerpScores = gerpScores;
    }

    public Set<Double> getGerpNeutralRates() {
        return gerpNeutralRates;
    }

    public void setGerpNeutralRates(Set<Double> gerpNeutralRates) {
        this.gerpNeutralRates = gerpNeutralRates;
    }

    public Set<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(Set<Feature> features) {
        this.features = features;
    }

    public Set<Ensembl> getEnsembls() {
        return ensembls;
    }

    public void setEnsembls(Set<Ensembl> ensembls) {
        this.ensembls = ensembls;
    }

    public Set<Double> getVertebrateGenomesConservationScore() {
        return vertebrateGenomesConservationScore;
    }

    public void setVertebrateGenomesConservationScore(Set<Double> vertebrateGenomesConservationScore) {
        this.vertebrateGenomesConservationScore = vertebrateGenomesConservationScore;
    }

    public Set<InterproDomain> getInterproDomains() {
        return interproDomains;
    }

    public void setInterproDomains(Set<InterproDomain> interproDomains) {
        this.interproDomains = interproDomains;
    }

    public Set<VariantStatus> getVariantStatuses() {
        return variantStatuses;
    }

    public void setVariantStatuses(Set<VariantStatus> variantStatuses) {
        this.variantStatuses = variantStatuses;
    }

    public Set<GenoType> getGenoTypes() {
        return genoTypes;
    }

    public void setGenoTypes(Set<GenoType> genoTypes) {
        this.genoTypes = genoTypes;
    }

    public Set<String> getReadDepths() {
        return readDepths;
    }

    public void setReadDepths(Set<String> readDepths) {
        this.readDepths = readDepths;
    }

    public Set<Double> getAlleleMutFractions() {
        return alleleMutFractions;
    }

    public void setAlleleMutFractions(Set<Double> alleleMutFractions) {
        this.alleleMutFractions = alleleMutFractions;
    }

    public Set<Double> getMeansbasequalities() {
        return meansbasequalities;
    }

    public void setMeansbasequalities(Set<Double> meansbasequalities) {
        this.meansbasequalities = meansbasequalities;
    }

    public Set<String> getVarntTypies() {
        return varntTypies;
    }

    public void setVarntTypies(Set<String> varntTypies) {
        this.varntTypies = varntTypies;
    }

    public Set<Boolean> getValidates() {
        return validates;
    }

    public void setValidates(Set<Boolean> validates) {
        this.validates = validates;
    }

    public Set<Boolean> getDonorSpliceSites() {
        return donorSpliceSites;
    }

    public void setDonorSpliceSites(Set<Boolean> donorSpliceSites) {
        this.donorSpliceSites = donorSpliceSites;
    }

    public Set<Boolean> getMutations() {
        return mutations;
    }

    public void setMutations(Set<Boolean> mutations) {
        this.mutations = mutations;
    }

    public Set<Double> getEuropeanVariantFreqs() {
        return europeanVariantFreqs;
    }

    public void setEuropeanVariantFreqs(Set<Double> europeanVariantFreqs) {
        this.europeanVariantFreqs = europeanVariantFreqs;
    }

    public Set<Double> getAfricanVariantFreqs() {
        return AfricanVariantFreqs;
    }

    public void setAfricanVariantFreqs(Set<Double> AfricanVariantFreqs) {
        this.AfricanVariantFreqs = AfricanVariantFreqs;
    }

    public Set<Double> getAsianVariantFreqs() {
        return asianVariantFreqs;
    }

    public void setAsianVariantFreqs(Set<Double> asianVariantFreqs) {
        this.asianVariantFreqs = asianVariantFreqs;
    }

    public Set<Double> getAmericanVariantFreqs() {
        return americanVariantFreqs;
    }

    public void setAmericanVariantFreqs(Set<Double> americanVariantFreqs) {
        this.americanVariantFreqs = americanVariantFreqs;
    }

    public Set<Double> getWholeVariantFreqs() {
        return WholeVariantFreqs;
    }

    public void setWholeVariantFreqs(Set<Double> WholeVariantFreqs) {
        this.WholeVariantFreqs = WholeVariantFreqs;
    }

    public Set<ClinvarAlleleType> getClinvarAlleleTypes() {
        return clinvarAlleleTypes;
    }

    public void setClinvarAlleleTypes(Set<ClinvarAlleleType> clinvarAlleleTypes) {
        this.clinvarAlleleTypes = clinvarAlleleTypes;
    }

    public boolean isByClinvarAlleleType() {
        return byClinvarAlleleType;
    }

    public void setByClinvarAlleleType(boolean byClinvarAlleleType) {
        this.byClinvarAlleleType = byClinvarAlleleType;
    }

    public Double getGerpRsScoreMin() {
        return gerpRsScoreMin;
    }

    public void setGerpRsScoreMin(Double gerpRsScoreMin) {
        this.gerpRsScoreMin = gerpRsScoreMin;
    }

    public Double getGerpRsScoreMax() {
        return gerpRsScoreMax;
    }

    public void setGerpRsScoreMax(Double gerpRsScoreMax) {
        this.gerpRsScoreMax = gerpRsScoreMax;
    }

    public Double getGerpNeutralRateMin() {
        return gerpNeutralRateMin;
    }

    public void setGerpNeutralRateMin(Double gerpNeutralRateMin) {
        this.gerpNeutralRateMin = gerpNeutralRateMin;
    }

    public Double getGerpNeutralRateMax() {
        return gerpNeutralRateMax;
    }

    public void setGerpNeutralRateMax(Double gerpNeutralRateMax) {
        this.gerpNeutralRateMax = gerpNeutralRateMax;
    }

    public Double getVertebrateGenomesConservationScoreMin() {
        return vertebrateGenomesConservationScoreMin;
    }

    public void setVertebrateGenomesConservationScoreMin(Double vertebrateGenomesConservationScoreMin) {
        this.vertebrateGenomesConservationScoreMin = vertebrateGenomesConservationScoreMin;
    }

    public Double getVertebrateGenomesConservationScoreMax() {
        return vertebrateGenomesConservationScoreMax;
    }

    public void setVertebrateGenomesConservationScoreMax(Double vertebrateGenomesConservationScoreMax) {
        this.vertebrateGenomesConservationScoreMax = vertebrateGenomesConservationScoreMax;
    }

    public Double getAlleleMutFractionMin() {
        return alleleMutFractionMin;
    }

    public void setAlleleMutFractionMin(Double alleleMutFractionMin) {
        this.alleleMutFractionMin = alleleMutFractionMin;
    }

    public Double getAlleleMutFractionMax() {
        return alleleMutFractionMax;
    }

    public void setAlleleMutFractionMax(Double alleleMutFractionMax) {
        this.alleleMutFractionMax = alleleMutFractionMax;
    }

    public Double getMeanBaseQualityMin() {
        return meanBaseQualityMin;
    }

    public void setMeanBaseQualityMin(Double meanBaseQualityMin) {
        this.meanBaseQualityMin = meanBaseQualityMin;
    }

    public Double getMeanBaseQualityMax() {
        return meanBaseQualityMax;
    }

    public void setMeanBaseQualityMax(Double meanBaseQualityMax) {
        this.meanBaseQualityMax = meanBaseQualityMax;
    }

    public Double getEuropeanVariantFreqMin() {
        return europeanVariantFreqMin;
    }

    public void setEuropeanVariantFreqMin(Double europeanVariantFreqMin) {
        this.europeanVariantFreqMin = europeanVariantFreqMin;
    }

    public Double getEuropeanVariantFreqMax() {
        return europeanVariantFreqMax;
    }

    public void setEuropeanVariantFreqMax(Double europeanVariantFreqMax) {
        this.europeanVariantFreqMax = europeanVariantFreqMax;
    }

    public Double getAfricanVariantFreqMin() {
        return africanVariantFreqMin;
    }

    public void setAfricanVariantFreqMin(Double africanVariantFreqMin) {
        this.africanVariantFreqMin = africanVariantFreqMin;
    }

    public Double getAfricanVariantFreqMax() {
        return africanVariantFreqMax;
    }

    public void setAfricanVariantFreqMax(Double africanVariantFreqMax) {
        this.africanVariantFreqMax = africanVariantFreqMax;
    }

    public Double getAsianVariantFreqMin() {
        return asianVariantFreqMin;
    }

    public void setAsianVariantFreqMin(Double asianVariantFreqMin) {
        this.asianVariantFreqMin = asianVariantFreqMin;
    }

    public Double getAsianVariantFreqMax() {
        return asianVariantFreqMax;
    }

    public void setAsianVariantFreqMax(Double asianVariantFreqMax) {
        this.asianVariantFreqMax = asianVariantFreqMax;
    }

    public Double getAmericanVariantFreqMin() {
        return americanVariantFreqMin;
    }

    public void setAmericanVariantFreqMin(Double americanVariantFreqMin) {
        this.americanVariantFreqMin = americanVariantFreqMin;
    }

    public Double getAmericanVariantFreqMax() {
        return americanVariantFreqMax;
    }

    public void setAmericanVariantFreqMax(Double americanVariantFreqMax) {
        this.americanVariantFreqMax = americanVariantFreqMax;
    }

    public Double getWholeVariantFreqMin() {
        return wholeVariantFreqMin;
    }

    public void setWholeVariantFreqMin(Double wholeVariantFreqMin) {
        this.wholeVariantFreqMin = wholeVariantFreqMin;
    }

    public Double getWholeVariantFreqMax() {
        return wholeVariantFreqMax;
    }

    public void setWholeVariantFreqMax(Double wholeVariantFreqMax) {
        this.wholeVariantFreqMax = wholeVariantFreqMax;
    }

    public Boolean getValidate() {
        return validate;
    }

    public void setValidate(Boolean validate) {
        this.validate = validate;
    }

    public Boolean getDonorSpliceSite() {
        return donorSpliceSite;
    }

    public void setDonorSpliceSite(Boolean donorSpliceSite) {
        this.donorSpliceSite = donorSpliceSite;
    }

    public Boolean getAcceptorSpliceSite() {
        return acceptorSpliceSite;
    }

    public void setAcceptorSpliceSite(Boolean acceptorSpliceSite) {
        this.acceptorSpliceSite = acceptorSpliceSite;
    }

    public Boolean getMutation() {
        return mutation;
    }

    public void setMutation(Boolean mutation) {
        this.mutation = mutation;
    }

    public Boolean getByAcceptorSpliceSite() {
        return byAcceptorSpliceSite;
    }

    public void setByAcceptorSpliceSite(Boolean byAcceptorSpliceSite) {
        this.byAcceptorSpliceSite = byAcceptorSpliceSite;
    }

    
    

    
    
    @Override
    public String toString() {
        return "Filtro{" + "id=" + id + ", analise=" + analise + ", genes=" + genes + ", cromossomos=" + cromossomos + ", qualidadeMin=" + "qualidadeMin" + ", qualidadeMax=" + "qualidadeMax" +  ", infBiologica=" + infBiologica + '}';
    }
    
    

    
}