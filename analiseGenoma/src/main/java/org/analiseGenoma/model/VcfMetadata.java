package org.analiseGenoma.model;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToOne;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

//http://www.algaworks.com/aulas/575/cache-com-infinispan-jta-e-no-wildfly/

@Entity
@Table(name = "vcf_metadata")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@NamedStoredProcedureQuery(
        name = "make_vcf_metadados",
        procedureName = "make_vcf_metadados",
        parameters
        = {
            @StoredProcedureParameter(name = "vcfId", mode = ParameterMode.IN, type = Long.class),
        }
)

public class VcfMetadata implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vcfmetadata")
    private Long id;
    @OneToOne
    @JoinColumn(name = "vcf_id")
    private Vcf vcf;
    private int qtdVariante;
    @ManyToMany(fetch = FetchType.EAGER)
    @Cache(usage=CacheConcurrencyStrategy.TRANSACTIONAL)
    private Set<Cromossomo> cromossomos;
    @ElementCollection(fetch = FetchType.EAGER)
    //@CollectionTable(name="vcfmetadata_referencias")
    private Set<String> referencias;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> alterado;
    private Long positonMax;
    private Long positonMin;
    @ManyToMany(fetch = FetchType.EAGER)
    
    private Set<Gene> genes;
//    @ManyToMany(fetch=FetchType.EAGER )
//    //@JoinColumn(columnDefinition="integer", name="customer_id")
//    @JoinColumn( columnDefinition = "bigint(20)" ,nullable = true, insertable=true, updatable=true)    
    //@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "vcf_metadata_umd_predictor", joinColumns = {
        @JoinColumn(name = "vcfMetadata_id_vcfmetadata", nullable = true, updatable = false)},
            inverseJoinColumns = {
                @JoinColumn(name = "umdPredictors_id_umdpredictor",
                        nullable = true, updatable = false)})
    private Set<UmdPredictor> umdPredictors;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Effect> effects;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Sift> sifts;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "gene_qtd", joinColumns = @JoinColumn(name = "id"))
    @MapKeyColumn(name = "gene_id")
    @Column(name = "qtd")
    private Map<Gene, Integer> mapGene;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Zygosity> zygosities;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> allelicDeph;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Filter> filters;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> hgvsCs;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> hgvsPs;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> idSNPs;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Integer> exonIntrons;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Type> typies;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Impact> impacts;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<ClinvarSignificance> clinvarSignificances;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<ClinvarDisease> clinvarDiseases;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<ClinvarAccession> clinvarAccessions;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<ClinvarAlleleType> clinvarAlleleTypies;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<ClinvarAlleleOrigin> clinvarAlleleOrigins;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<PolyphenHdiv> polyphenHdivs;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<PolyphenHvar> polyphenHvars;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<MutationTaster> mutationTasters;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Lrt> lrts;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Double> gerpRsScores;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Double> gerpNeutralRates;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Feature> features;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Ensembl> ensembls;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Double> vertebrateGenomesConservationScores;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<InterproDomain> interproDomains;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<VariantStatus> variantStatus;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<GenoType> genoTypies;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> readDepths;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Double> alleleMutFraction;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Double> meanBaseQuality;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> varintType;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Boolean> validate;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Boolean> donorSpliceSite;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Boolean> acceptorSpliceSite;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Boolean> mutation;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Double> europeanVarintFreq;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Double> africanVarintFreq;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Double> asianVarintFreq;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Double> americanVarintFreq;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Double> wholeVarintFreq;
    private Double wholevariantfreqmin;
    private Double wholevariantfreqmax;

    private Double prevalenceMin;
    private Double prevalenceMax;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vcf getVcf() {
        return vcf;
    }

    public void setVcf(Vcf vcf) {
        this.vcf = vcf;
    }

    public int getQtdVariante() {
        return qtdVariante;
    }

    public void setQtdVariante(int qtdVariante) {
        this.qtdVariante = qtdVariante;
    }

    public Set<Cromossomo> getCromossomos() {
        return cromossomos;
    }

    public void setCromossomos(Set<Cromossomo> cromossomos) {
        this.cromossomos = cromossomos;
    }

    public Long getPositonMax() {
        return positonMax;
    }

    public void setPositonMax(Long positonMax) {
        this.positonMax = positonMax;
    }

    public Long getPositonMin() {
        return positonMin;
    }

    public void setPositonMin(Long positonmin) {
        this.positonMin = positonmin;
    }

    public Set<String> getReferencias() {
        return referencias;
    }

    public void setReferencias(Set<String> referencias) {
        this.referencias = referencias;
    }

    public Set<String> getAlterado() {
        return alterado;
    }

    public void setAlterado(Set<String> alterado) {
        this.alterado = alterado;
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

    public Set<Effect> getEffects() {
        return effects;
    }

    public void setEffects(Set<Effect> effects) {
        this.effects = effects;
    }

    public Set<Sift> getSifts() {
        return sifts;
    }

    public void setSifts(Set<Sift> sifts) {
        this.sifts = sifts;
    }

    public Map<Gene, Integer> getMapGene() {
        return mapGene;
    }

    public void setMapGene(Map<Gene, Integer> mapGene) {
        this.mapGene = mapGene;
    }

    public Set<Zygosity> getZygosities() {
        return zygosities;
    }

    public void setZygosities(Set<Zygosity> zygosities) {
        this.zygosities = zygosities;
    }

    public Set<String> getAllelicDeph() {
        return allelicDeph;
    }

    public void setAllelicDeph(Set<String> allelicDeph) {
        this.allelicDeph = allelicDeph;
    }

    public Set<Filter> getFilters() {
        return filters;
    }

    public void setFilters(Set<Filter> filters) {
        this.filters = filters;
    }

    public Set<String> getHgvsCs() {
        return hgvsCs;
    }

    public void setHgvsCs(Set<String> hgvsCs) {
        this.hgvsCs = hgvsCs;
    }

    public Set<String> getHgvsPs() {
        return hgvsPs;
    }

    public void setHgvsPs(Set<String> hgvsPs) {
        this.hgvsPs = hgvsPs;
    }

    public Set<String> getIdSNPs() {
        return idSNPs;
    }

    public void setIdSNPs(Set<String> idSNPs) {
        this.idSNPs = idSNPs;
    }

    public Set<Integer> getExonIntrons() {
        return exonIntrons;
    }

    public void setExonIntrons(Set<Integer> exonIntrons) {
        this.exonIntrons = exonIntrons;
    }

    public Set<Type> getTypies() {
        return typies;
    }

    public void setTypies(Set<Type> typies) {
        this.typies = typies;
    }

    public Set<Impact> getImpacts() {
        return impacts;
    }

    public void setImpacts(Set<Impact> impacts) {
        this.impacts = impacts;
    }

    public Set<ClinvarSignificance> getClinvarSignificances() {
        return clinvarSignificances;
    }

    public void setClinvarSignificances(Set<ClinvarSignificance> clinvarSignificances) {
        this.clinvarSignificances = clinvarSignificances;
    }

    public Set<ClinvarDisease> getClinvarDiseases() {
        return clinvarDiseases;
    }

    public void setClinvarDiseases(Set<ClinvarDisease> clinvarDiseases) {
        this.clinvarDiseases = clinvarDiseases;
    }

    public Set<ClinvarAccession> getClinvarAccessions() {
        return clinvarAccessions;
    }

    public void setClinvarAccessions(Set<ClinvarAccession> clinvarAccessions) {
        this.clinvarAccessions = clinvarAccessions;
    }

    public Set<ClinvarAlleleType> getClinvarAlleleTypies() {
        return clinvarAlleleTypies;
    }

    public void setClinvarAlleleTypies(Set<ClinvarAlleleType> clinvarAlleleTypies) {
        this.clinvarAlleleTypies = clinvarAlleleTypies;
    }

    public Set<ClinvarAlleleOrigin> getClinvarAlleleOrigins() {
        return clinvarAlleleOrigins;
    }

    public void setClinvarAlleleOrigins(Set<ClinvarAlleleOrigin> clinvarAlleleOrigins) {
        this.clinvarAlleleOrigins = clinvarAlleleOrigins;
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

    public Set<Double> getGerpRsScores() {
        return gerpRsScores;
    }

    public void setGerpRsScores(Set<Double> gerpRsScores) {
        this.gerpRsScores = gerpRsScores;
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

    public Set<Double> getVertebrateGenomesConservationScores() {
        return vertebrateGenomesConservationScores;
    }

    public void setVertebrateGenomesConservationScores(Set<Double> vertebrateGenomesConservationScores) {
        this.vertebrateGenomesConservationScores = vertebrateGenomesConservationScores;
    }

    public Set<InterproDomain> getInterproDomains() {
        return interproDomains;
    }

    public void setInterproDomains(Set<InterproDomain> interproDomains) {
        this.interproDomains = interproDomains;
    }

    public Set<VariantStatus> getVariantStatus() {
        return variantStatus;
    }

    public void setVariantStatus(Set<VariantStatus> variantStatus) {
        this.variantStatus = variantStatus;
    }

    public Set<GenoType> getGenoTypies() {
        return genoTypies;
    }

    public void setGenoTypies(Set<GenoType> genoTypies) {
        this.genoTypies = genoTypies;
    }

    public Set<String> getReadDepths() {
        return readDepths;
    }

    public void setReadDepths(Set<String> readDepths) {
        this.readDepths = readDepths;
    }

    public Set<Double> getAlleleMutFraction() {
        return alleleMutFraction;
    }

    public void setAlleleMutFraction(Set<Double> alleleMutFraction) {
        this.alleleMutFraction = alleleMutFraction;
    }

    public Set<Double> getMeanBaseQuality() {
        return meanBaseQuality;
    }

    public void setMeanBaseQuality(Set<Double> meanBaseQuality) {
        this.meanBaseQuality = meanBaseQuality;
    }

    public Set<String> getVarintType() {
        return varintType;
    }

    public void setVarintType(Set<String> varintType) {
        this.varintType = varintType;
    }

    public Set<Boolean> getValidate() {
        return validate;
    }

    public void setValidate(Set<Boolean> validate) {
        this.validate = validate;
    }

    public Set<Boolean> getDonorSpliceSite() {
        return donorSpliceSite;
    }

    public void setDonorSpliceSite(Set<Boolean> donorSpliceSite) {
        this.donorSpliceSite = donorSpliceSite;
    }

    public Set<Boolean> getAcceptorSpliceSite() {
        return acceptorSpliceSite;
    }

    public void setAcceptorSpliceSite(Set<Boolean> acceptorSpliceSite) {
        this.acceptorSpliceSite = acceptorSpliceSite;
    }

    public Set<Boolean> getMutation() {
        return mutation;
    }

    public void setMutation(Set<Boolean> mutation) {
        this.mutation = mutation;
    }

    public Set<Double> getEuropeanVarintFreq() {
        return europeanVarintFreq;
    }

    public void setEuropeanVarintFreq(Set<Double> europeanVarintFreq) {
        this.europeanVarintFreq = europeanVarintFreq;
    }

    public Set<Double> getAfricanVarintFreq() {
        return africanVarintFreq;
    }

    public void setAfricanVarintFreq(Set<Double> africanVarintFreq) {
        this.africanVarintFreq = africanVarintFreq;
    }

    public Set<Double> getAsianVarintFreq() {
        return asianVarintFreq;
    }

    public void setAsianVarintFreq(Set<Double> asianVarintFreq) {
        this.asianVarintFreq = asianVarintFreq;
    }

    public Set<Double> getAmericanVarintFreq() {
        return americanVarintFreq;
    }

    public void setAmericanVarintFreq(Set<Double> americanVarintFreq) {
        this.americanVarintFreq = americanVarintFreq;
    }

    public Set<Double> getWholeVarintFreq() {
        return wholeVarintFreq;
    }

    public void setWholeVarintFreq(Set<Double> wholeVarintFreq) {
        this.wholeVarintFreq = wholeVarintFreq;
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

    public Double getWholevariantfreqmin() {
        return wholevariantfreqmin;
    }

    public void setWholevariantfreqmin(Double wholevariantfreqmin) {
        this.wholevariantfreqmin = wholevariantfreqmin;
    }

    public Double getWholevariantfreqmax() {
        return wholevariantfreqmax;
    }

    public void setWholevariantfreqmax(Double wholevariantfreqmax) {
        this.wholevariantfreqmax = wholevariantfreqmax;
    }
    
    

}
