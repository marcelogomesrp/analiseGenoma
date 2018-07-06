package org.analiseGenoma.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.dao.VarianteDao;
import org.analiseGenoma.dao.VcfMetadataDao;
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
import org.analiseGenoma.model.Variante;
import org.analiseGenoma.model.Vcf;
import org.analiseGenoma.model.VcfMetadata;
import org.analiseGenoma.model.Zygosity;


@Named
public class VcfMetadataService extends Service<VcfMetadata>{
    private Vcf vcf;
    private List<Variante> variantes;
    @Inject private VarianteDao varianteDao;

    
    public VcfMetadataService() {
        super(VcfMetadata.class);
    }
    
    private VcfMetadataDao getDao(){
        return ((VcfMetadataDao) dao);
    }

    public void setVcf(Vcf vcf) {
        this.vcf = vcf;
        variantes = varianteDao.findByVCF(vcf.getId());
    }
    
    
    //aqui vou criar as estatisticas 
    public VcfMetadata makeMetadata(Vcf vcf){
        this.setVcf(vcf);
        VcfMetadata vmd = new VcfMetadata();
        vmd.setVcf(vcf);
        vmd.setQtdVariante(this.getQtdVariante());
        vmd.setCromossomos(this.getCromossomos());
        vmd.setPositonMax(this.getPostionMax());
        vmd.setPositonMin(this.getPostionMin());
        vmd.setReferencias(this.getReferencias());
        vmd.setAlterado((this.getAlterados()));
        vmd.setGenes(this.getGenes());
        vmd.setUmdPredictors(this.getUmdPredictors());
        vmd.setEffects(this.getEffect());
        vmd.setSifts(this.getSift());
        vmd.setMapGene(this.getMapGene());     
        vmd.setZygosities(this.getZygositys());
        vmd.setAllelicDeph(this.getAllelicDephs());
        vmd.setFilters(this.getFilters());
        vmd.setHgvsCs(this.getHgvsCs());
        vmd.setHgvsPs(this.getHgvsPs());
        vmd.setIdSNPs(this.getIdSNPs());
        vmd.setExonIntrons(this.getExonIntrons());
        vmd.setTypies(this.getTypies());
        vmd.setImpacts(this.getImpacts());
        vmd.setClinvarSignificances(this.getClinvarSignificances());
        vmd.setClinvarDiseases(this.getClinvarDiseases());
        vmd.setClinvarAccessions(this.getClinvarAccessions());
        vmd.setClinvarAlleleTypies(this.getClinvarAlleleTypies());
        vmd.setClinvarAlleleOrigins(this.getClinvarAlleleOrigins());
        vmd.setPolyphenHdivs(this.getPolyphenHdivs());
        //aqui3
        vmd.setPolyphenHvars(this.getPolyphenHvars());
        
        vmd.setMutationTasters(this.getMutationTasters());
        vmd.setLrts(this.getLrts());
        vmd.setGerpRsScores(this.getGerpRsScores());
        vmd.setGerpNeutralRates(this.getGerpNeutralRates());
        vmd.setFeatures(this.getFeatures());
        vmd.setEnsembls(this.getEnsembls());
        vmd.setVertebrateGenomesConservationScores(this.getVertebrateGenomesConservationScores());
        vmd.setInterproDomains(this.getInterproDomains());
        vmd.setVariantStatus(this.getVariantStatus());
        vmd.setGenoTypies(this.getGenoTypies());
        vmd.setReadDepths(this.getReadDepths());
        vmd.setAlleleMutFraction(this.getAlleleMutFractions());
        vmd.setMeanBaseQuality(this.getMeanBaseQualities());
        vmd.setVarintType(this.getVarintTypies());
        vmd.setValidate(this.getValidates());
        vmd.setDonorSpliceSite(this.getDonorSpliceSites());
        vmd.setAcceptorSpliceSite(this.getAcceptorSpliceSites());
        vmd.setMutation(this.getMutations());
        vmd.setEuropeanVarintFreq(this.getEuropeanVarintFreqs());
        vmd.setAfricanVarintFreq(this.getAfricanVarintFreqs());
        vmd.setAsianVarintFreq(this.getAsianVarintFreqs());
        vmd.setAmericanVarintFreq(this.getAmericanVarintFreqs());
        vmd.setWholeVarintFreq(this.getWholeVarintFreqs());
        vmd.setPrevalenceMin(this.getPrevalenceMin());
        vmd.setPrevalenceMax(this.getPrevalenceMax());
        
        return vmd;
    }
    
    
    public int getQtdVariante() {
        return variantes.size();
    }
    public Set<Cromossomo> getCromossomos(){
        return new HashSet<>(
                variantes.stream()
                .map(v -> v.getCromossomo())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet())
                );
    }
    
    public Set<String> getReferencias(){
        return variantes.stream()
                .map(v -> v.getReferencia())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }
    
    public Set<String> getAlterados(){
        return variantes.stream()
                .map(v -> v.getAlterado())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }
    
    public Long getPostionMax(){
        return variantes.stream()
                .max((v1,v2) -> Long.compare(v1.getPosition(), v2.getPosition()))
                .get()
                .getPosition();        
    }

    public Long getPostionMin(){
        return variantes.stream()
                .min((v1,v2) -> Long.compare(v1.getPosition(), v2.getPosition()))
                .get()
                .getPosition();        
    }

    public Set<Gene> getGenes() {
        return variantes.stream()
                .map(v -> v.getGene())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }
    
    public Set<UmdPredictor> getUmdPredictors(){
        Set<UmdPredictor> out =  variantes.stream()
                .map(v -> v.getUmdPredictor())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
        return out;
    }

    public Set<Effect> getEffect(){
        return variantes.stream()
                .map(v -> v.getEffect())
                .distinct()
                .collect(Collectors.toSet());
    }    
    public Set<Sift> getSift(){
        return variantes.stream()
                .map(v -> v.getSift())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    public VcfMetadata findByVcfId(Long id) {
        List<VcfMetadata> list = getDao().findByVcf(id);
        if(list.size() == 1)
            return list.get(0);
        return null;
    }

    private Map<Gene, Integer> getMapGene() {
        HashMap<Gene, Integer> map = new HashMap<Gene,Integer>();
        for(Variante v: variantes){
            Integer total = map.get(v.getGene());
            if(total == null){
                total = 0;
            }
            total++;
            map.put(v.getGene(), total);
        }
        return map;
            
    }

    private Set<Zygosity> getZygositys() {
        return variantes.stream()
                .map(v -> v.getZygosity())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }


    public Set<String> getAllelicDephs(){
        return variantes.stream()
                .map(v -> v.getAllelicDeph())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<Filter> getFilters() {
        return variantes.stream()
                .map(v -> v.getFilter())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }
    
    private Set<String> getHgvsCs(){
        return variantes.stream()
                .map(v -> v.getHgvsC())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }
    
    private Set<String> getHgvsPs(){
        return variantes.stream()
                .map(v -> v.getHgvsP())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<String> getIdSNPs(){
        return variantes.stream()
                .map(v -> v.getIdSNP())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<Integer> getExonIntrons(){
        return variantes.stream()
                .map(v -> v.getExonIntron())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }    
    
    private Set<Type> getTypies(){
        return variantes.stream()
                .map(v -> v.getType())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }   

    private Set<Impact> getImpacts(){
        return variantes.stream()
                .map(v -> v.getImpact())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    } 
    
    private Set<ClinvarSignificance> getClinvarSignificances(){
        return variantes.stream()
                .map(v -> v.getClinvarSignificance())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }     

    private Set<ClinvarDisease> getClinvarDiseases(){
        return variantes.stream()
                .map(v -> v.getClinvarDisease())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }
    
    private Set<ClinvarAccession> getClinvarAccessions(){
        return variantes.stream()
                .map(v -> v.getClinvarAccession())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }
    
    private Set<ClinvarAlleleType> getClinvarAlleleTypies(){
        return variantes.stream()
                .map(v -> v.getClinvarAlleleType())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<ClinvarAlleleOrigin> getClinvarAlleleOrigins(){
        return variantes.stream()
                .map(v -> v.getClinvarAlleleOrigin())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }
    
    private Set<PolyphenHdiv> getPolyphenHdivs(){
        return variantes.stream()
                .map(v -> v.getPolyphenHdiv())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }
    
    private Set<PolyphenHvar> getPolyphenHvars(){
        return variantes.stream()
                .map(v -> v.getPolyphenHvar())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }
    
    private Set<MutationTaster> getMutationTasters(){
        return variantes.stream()
                .map(v -> v.getMutationTaster())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }
    
    private Set<Lrt> getLrts(){
        return variantes.stream()
                .map(v -> v.getLrt())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<Double> getGerpRsScores(){
        return variantes.stream()
                .map(v -> v.getGerpRsScore())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }    
    
    private Set<Double> getGerpNeutralRates(){
        return variantes.stream()
                .map(v -> v.getGerpNeutralRate())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    } 

    private Set<Feature> getFeatures(){
        return variantes.stream()
                .map(v -> v.getFeature())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }  
    
    private Set<Ensembl> getEnsembls(){
        return variantes.stream()
                .map(v -> v.getEnsembl())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }  
    
    private Set<Double> getVertebrateGenomesConservationScores(){
        return variantes.stream()
                .map(v -> v.getVertebrateGenomesConservationScore())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }  
    
    private Set<InterproDomain> getInterproDomains(){
        return variantes.stream()
                .map(v -> v.getInterproDomain())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }  

    private Set<VariantStatus> getVariantStatus(){
        return variantes.stream()
                .map(v -> v.getVariantStatus())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }  

    private Set<GenoType> getGenoTypies(){
        return variantes.stream()
                .map(v -> v.getGenoType())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }     
    
    private Set<String> getReadDepths(){
        return variantes.stream()
                .map(v -> v.getReadDepth())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }  
    
    private Set<Double> getAlleleMutFractions(){
        return variantes.stream()
                .map(v -> v.getAlleleMutFraction())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }
    
    private Set<Double> getMeanBaseQualities(){
        return variantes.stream()
                .map(v -> v.getMeanBaseQuality())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    } 
    
    private Set<String> getVarintTypies(){
        return variantes.stream()
                .map(v -> v.getVarintType())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }   
    
    private Set<Boolean> getValidates(){
        return variantes.stream()
                .map(v -> v.getValidate())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }
    
    private Set<Boolean> getDonorSpliceSites(){
        return variantes.stream()
                .map(v -> v.getDonorSpliceSite() )
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }
    
    private Set<Boolean> getAcceptorSpliceSites(){
        return variantes.stream()
                .map(v -> v.getAcceptorSpliceSite())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }
    
    private Set<Boolean> getMutations(){
        return variantes.stream()
                .map(v -> v.getMutation())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<Double> getEuropeanVarintFreqs(){
        return variantes.stream()
                .map(v -> v.getEuropeanVarintFreq() )
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }
    
    private Set<Double> getAfricanVarintFreqs(){
        return variantes.stream()
                .map(v -> v.getAfricanVarintFreq() )
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }
    
    private Set<Double> getAsianVarintFreqs(){
        return variantes.stream()
                .map(v -> v.getAsianVarintFreq() )
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<Double> getAmericanVarintFreqs(){
        return variantes.stream()
                .map(v -> v.getAmericanVarintFreq() )
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }    
    
    private Set<Double> getWholeVarintFreqs(){
        return variantes.stream()
                .map(v -> v.getWholeVarintFreq() )
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Double getPrevalenceMin() {
        try{
        Double r =  variantes.stream()
                .filter(v -> v.getWholeVarintFreq() != null)
                .min((v1,v2) -> Double.compare(v1.getWholeVarintFreq(), v2.getWholeVarintFreq()))
                .get()
                .getWholeVarintFreq();
        return r;
        }catch(Exception ex){
            System.out.println("Erro: VcfMetadataService.getPrevalenceMin:" + ex.getMessage());
        }
        return 0d;
    }
    
    private Double getPrevalenceMax() {
        try{
        return variantes.stream()
                .filter(v -> v.getWholeVarintFreq() != null)
                .max((v1,v2) -> Double.compare(v1.getWholeVarintFreq(), v2.getWholeVarintFreq()))
                .get()
                .getWholeVarintFreq();
        }catch(Exception ex){
            System.out.println("Erro: VcfMetadataService.getPrevalenceMaxn:" + ex.getMessage());
        }
        return 0d;
    }
    
}

