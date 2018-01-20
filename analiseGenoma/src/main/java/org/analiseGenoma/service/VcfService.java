package org.analiseGenoma.service;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.FiltroDao;
import org.analiseGenoma.dao.VarianteDao;
import org.analiseGenoma.dao.VcfDao;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.Filtro;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.Variante;
import org.analiseGenoma.model.Vcf;
import org.analiseGenoma.model.VcfStatus;
import org.analiseGenoma.service.util.DoubleFactory;

@Named
public class VcfService implements Serializable {

    @Inject
    private VcfDao vcfDao;
    @Inject
    private VarianteDao varianteDao;
    @Inject
    private GeneService geneService;
    @Inject
    private CromossomoDao cromossomoDao;
    @Inject
    private CromossomoService cromossomoService;

    @Inject
    private ImpactoService impactoService;
    @Inject
    private VarianteService varianteService;
    @Inject
    private FiltroDao filtroDao;
    
    @Inject private UmdPredictorService umdPredictorService;
    @Inject private ZygosityService zygosityService;
    @Inject private FilterService filterService;
    @Inject private TypeService typeService;
    @Inject private EffectService effectService;
    @Inject private ImpactService impactService;
    @Inject private ClinvarSignificanceService clinvarSignificanceService;
    @Inject private ClinvarDiseaseService clinvarDiseaseService;
    @Inject private ClinvarAccessionService clinvarAccessionService;
    @Inject private ClinvarAlleleTypeService clinvarAlleleTypeService;
    @Inject private ClinvarAlleleOriginService clinvarAlleleOriginService;
    @Inject private SiftService siftService;
    @Inject private PolyphenHdivService polyphenHdivService;
    @Inject private PolyphenHvarService polyphenHvarService;
    @Inject private MutationTasterService mutationTasterService;
    @Inject private LrtService lrtService;
    @Inject private FeatureService featureService;
    @Inject private EnsemblService ensemblService;
    @Inject private InterproDomainService interproDomainService;
    @Inject private VariantStatusService variantStatusService;
    @Inject private GenoTypeService genoTypeService;
    
    @Inject private VcfMetadataService vcfMetadataService;

    @PostConstruct
    public void init() {
    }

    @Transactional
    public void adicionar(Vcf vcf) {
        vcf.setStatus(VcfStatus.importando);
        vcf.setDataImportacao(new Date());
        vcfDao.persist(vcf);
    }

    @Transactional
    public void atualizar(Vcf vcf) {
        vcfDao.merge(vcf);
    }

    public List<Vcf> buscar() {
        return vcfDao.find();
    }

    public Vcf buscarId(Long id) {
        return vcfDao.findById(id);
    }

    public List<Vcf> buscarPacienteId(Long id) {
        return vcfDao.buscarPacienteId(id);
    }

    public List<Variante> buscarVariante(Long idAnalise) {        
        return varianteDao.buscarAnalise(idAnalise);
    }
    
    public List<Variante> buscarVariante(Long idAnalise, Long idFiltro){
       // acertar a busca
       Filtro filtro = filtroDao.findById(idFiltro);
       
       return varianteDao.buscarAnalise(idAnalise);
    }

    @Transactional
    public void importar(byte[] contents, Vcf vcf) {
        //este esta sendo usado
        String[] arquivo = new String(contents, StandardCharsets.UTF_8).split("\n");
        Variante variante = null;
        for(String ln:arquivo){
            variante = new Variante();
            variante.setVcf(vcf);
            String[] linha = ln.split("\t");
            variante.setCromossomo(cromossomoService.buscarOuCriar(linha[0]));
            variante.setPosition(linha[1]);
            String[] referenceAlternate = linha[2].split(">");            
            variante.setReferencia(referenceAlternate[0]);
            variante.setAlterado(referenceAlternate[1]);
            Gene gene = geneService.buscarNovoSimbolo(linha[3]);
            if(gene == null){
                gene = new Gene();
                gene.setSymbol(linha[3]);
                gene.setName(linha[4]);
                gene.setSynonymou(null);
                geneService.adicionar(gene);
            }
            variante.setGene(gene);
            variante.setUmdPredictor(umdPredictorService.findOrCreate(linha[5]));
            variante.setZygosity(zygosityService.findOrCreate(linha[6]));
            variante.setAllelicDeph(linha[7]);
            variante.setFilter(filterService.findOrCreate(linha[8]));
            variante.setHgvsC(linha[9]);
            variante.setHgvsP(linha[10]);
            variante.setIdSNP(linha[11]);
            variante.setExonIntron(linha[12]);
            variante.setType(typeService.findOrCreate(linha[13]));
            variante.setEffect(effectService.findOrCreate(linha[14]));
            variante.setImpact(impactService.findOrCreate(linha[15]));
            variante.setClinvarSignificance(clinvarSignificanceService.findOrCreate(linha[16]));
            variante.setClinvarDisease(clinvarDiseaseService.findOrCreate(linha[17]));
            variante.setClinvarAccession(clinvarAccessionService.findOrCreate(linha[18]));
            variante.setClinvarAlleleType(clinvarAlleleTypeService.findOrCreate(linha[19]));
            variante.setClinvarAlleleOrigin(clinvarAlleleOriginService.findOrCreate(linha[20]));
            variante.setSift(siftService.findOrCreate(linha[21]));
            variante.setPolyphenHdiv(polyphenHdivService.findOrCreate(linha[22]));
            variante.setPolyphenHvar(polyphenHvarService.findOrCreate(linha[23]));
            variante.setMutationTaster(mutationTasterService.findOrCreate(linha[24]));
            variante.setLrt(lrtService.findOrCreate(linha[25]));
            variante.setGerpRsScore(linha[26]);
            variante.setGerpNeutralRate(linha[27]);
            variante.setFeature(featureService.findOrCreate(linha[28]));
            variante.setEnsembl(ensemblService.findOrCreate(linha[29]));
            variante.setVertebrateGenomesConservationScore(linha[30]);
            variante.setInterproDomain(interproDomainService.findOrCreate(linha[31]));
            variante.setVariantStatus(variantStatusService.findOrCreate(linha[32]));
            variante.setGenoType(genoTypeService.findOrCreate(linha[33]));
            variante.setReadDepth(linha[34]);
            variante.setAlleleMutFraction(linha[35]);
            variante.setMeanBaseQuality(linha[36]);
            variante.setVarintType(linha[37]);
            variante.setValidate(linha[38]);
            variante.setDonorSpliceSite(linha[39]);
            variante.setAcceptorSpliceSite(linha[40]);
            variante.setMutation(linha[41]);
            variante.setEuropeanVarintFreq(new DoubleFactory().make(linha[42]));
            variante.setAfricanVarintFreq(new DoubleFactory().make(linha[43]));
            variante.setAsianVarintFreq(new DoubleFactory().make(linha[44]));
            variante.setAmericanVarintFreq(new DoubleFactory().make(linha[45]));
            variante.setWholeVarintFreq(new DoubleFactory().make(linha[46]));
            varianteService.persiste(variante);            
        }
        
        vcfMetadataService.persiste(vcfMetadataService.makeMetadata(vcf));
        
        vcf.setStatus(VcfStatus.importado);        
        this.atualizar(vcf);
        
    }
    
//    public List<Variante> buscarVariante(Long idAnalise, Filtro filtro) {
//        return varianteDao.buscarAnalise(idAnalise, filtro);
//    }

    public List<Variante> findVariante(Analise analise, Filtro filtro) {
        if(filtro == null){
            return varianteDao.find(analise);
        }else{
            return varianteDao.findByAnaliseFiltro(analise, filtro);
        }
    }

    

}
