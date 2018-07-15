package org.analiseGenoma.teste;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import javax.batch.api.AbstractBatchlet;
import javax.batch.operations.JobOperator;

import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.context.JobContext;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.FiltroDao;
import org.analiseGenoma.dao.VarianteDao;
import org.analiseGenoma.dao.VcfDao;
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
import org.analiseGenoma.model.Patient;
import org.analiseGenoma.model.PolyphenHdiv;
import org.analiseGenoma.model.PolyphenHvar;
import org.analiseGenoma.model.Sift;
import org.analiseGenoma.model.Type;
import org.analiseGenoma.model.UmdPredictor;
import org.analiseGenoma.model.VariantStatus;
import org.analiseGenoma.model.Variante;
import org.analiseGenoma.model.Vcf;
import org.analiseGenoma.model.VcfMetadata;
import org.analiseGenoma.model.VcfStatus;
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
import org.analiseGenoma.service.GeneService;
import org.analiseGenoma.service.GenoTypeService;
import org.analiseGenoma.service.ImpactService;
import org.analiseGenoma.service.ImpactoService;
import org.analiseGenoma.service.InterproDomainService;
import org.analiseGenoma.service.LrtService;
import org.analiseGenoma.service.MutationTasterService;
import org.analiseGenoma.service.PatientService;
import org.analiseGenoma.service.PolyphenHdivService;
import org.analiseGenoma.service.PolyphenHvarService;
import org.analiseGenoma.service.SiftService;
import org.analiseGenoma.service.TypeService;
import org.analiseGenoma.service.UmdPredictorService;
import org.analiseGenoma.service.VariantStatusService;
import org.analiseGenoma.service.VarianteService;
import org.analiseGenoma.service.VcfMetadataService;
import org.analiseGenoma.service.VcfService;
import org.analiseGenoma.service.ZygosityService;
import org.analiseGenoma.service.util.DoubleFactory;

/**
 *
 * @author marcelo
 */
@Named
public class MeuBatchlet extends AbstractBatchlet {

    @Inject
    private VarianteService varianteService;
    @Inject
    private JobContext jobContext;

    //inicio
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
    private FiltroDao filtroDao;

    @Inject
    private UmdPredictorService umdPredictorService;
    @Inject
    private ZygosityService zygosityService;
    @Inject
    private FilterService filterService;
    @Inject
    private TypeService typeService;
    @Inject
    private EffectService effectService;
    @Inject
    private ImpactService impactService;
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

    @Inject
    private VcfMetadataService vcfMetadataService;
//    @Inject
//    private PatientSB patientSB;
    @Inject
    private VcfService vcfService;
    @Inject
    private PatientService patientService;

    @Inject
    private VcfMetadataDao vcfMetadataDao;
    
    @PersistenceContext
    protected EntityManager manager;

    private List<Variante> variantes;

    @Override
//    @Transactional
    public String process() {
        long start = System.currentTimeMillis();
        int total = 0;
        
        
        Patient patient = new Patient();
        Vcf vcf = new Vcf();
        
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Properties jobParameters = jobOperator.getParameters(jobContext.getExecutionId());
        String resourceName = (String) jobParameters.get("nome");
        String arquivo = (String) jobParameters.get("arquivo");
                
        try {
            String pacientIdT = (String) jobParameters.get("pacientId");
            Long pacienteId = Long.valueOf(pacientIdT);
            System.out.println(":D peguei o id do paciente: " + pacienteId);
            patient = patientService.findById(pacienteId);
        } catch (Exception ex) {
            System.out.println("Erro ao converter pacienteId: " + ex.getMessage());
            System.out.println("--> " + (String) jobParameters.get("pacientId"));
        }

                


        vcf.setStatus(VcfStatus.importando);
        //vcf.setPaciente(patient);
        //vcf.setNome(arquivo);
        //vcf.setDataImportacao(new Date());
        vcfService.adicionar(vcf);

        //System.out.println("Arquivo: " + arquivo);
        System.out.println("Nome: " + resourceName);
        total = arquivo.split("\n").length;
        int x =0;
        for (String ln : arquivo.split("\n")) {
            x++;
            System.out.println("Feito: " + x + " de " + total + " percentual: " + ((x * 100) /total));
            String[] linha = ln.split("\t");
            String gene = linha[3];
            String cromossomo = linha[0];
            
            String[] referenceAlternate = linha[2].split(">");
            String referencia = referenceAlternate[0];
            String alterado = referenceAlternate[1];
            String umdPredictor = linha[5];
            String zygosity = linha[6];
            String[] allelicDeph = linha[7].split("/");
            Integer allelicDeph1 = null;
            Integer allelicDeph2 = null;
            
            try{
            if (allelicDeph.length == 2) {
                allelicDeph1 = Integer.valueOf(allelicDeph[0].trim());
                allelicDeph2 = Integer.valueOf(allelicDeph[1].trim());
            }
            }catch(Exception ex){
                System.out.println("Erro no allelicDeph: " + ex.getMessage() + "\n--->Alleic" + allelicDeph );
            }
            String filter = linha[8];
            
            String hgvsC = linha[9];
            String hgvsP = linha[10];
            String idSNP = linha[11];
            Integer exonIntron = 0;
            try{
                exonIntron = Integer.valueOf(linha[12]);
            }catch(Exception ex){
                System.out.println("Erro exonTringo: " + ex.getMessage() + "\n-->ExonIntro " + linha[12]);
            }

    
            varianteService.runSP(cromossomo,gene, referencia, alterado, umdPredictor
                ,zygosity, allelicDeph1, allelicDeph2, filter
                ,hgvsC, hgvsP, idSNP, exonIntron);
                /*
            if(x%10 == 0){
                System.out.println("Fluxar");
                manager.flush();
                manager.clear();
                System.out.println("Fuxado...");
            }*/
        }

        /*
        Patient patient = new Patient();
        Vcf vcf = new Vcf();
        
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Properties jobParameters = jobOperator.getParameters(jobContext.getExecutionId());
        String resourceName = (String) jobParameters.get("nome");
        String arquivo = (String) jobParameters.get("arquivo");
                
        try {
            String pacientIdT = (String) jobParameters.get("pacientId");
            Long pacienteId = Long.valueOf(pacientIdT);
            System.out.println(":D peguei o id do paciente: " + pacienteId);
            patient = patientService.findById(pacienteId);
        } catch (Exception ex) {
            System.out.println("Erro ao converter pacienteId: " + ex.getMessage());
            System.out.println("--> " + (String) jobParameters.get("pacientId"));
        }

                


        vcf.setStatus(VcfStatus.importando);
        //vcf.setPaciente(patient);
        //vcf.setNome(arquivo);
        //vcf.setDataImportacao(new Date());
        vcfService.adicionar(vcf);

        //System.out.println("Arquivo: " + arquivo);
        System.out.println("Nome: " + resourceName);
        int total = arquivo.split("\n").length;
        int x =0;
        for (String ln : arquivo.split("\n")) {
        //    Variante v = this.makeVariante(vcf, ln);
            x++;
            System.out.println("Feito: " + x + " de " + total + " percentual: " + x/total);
            varianteService.runSP();
            //varianteService.persiste(v);
        }
        
        
        
        vcf.setStatus(VcfStatus.importado);        
        vcfService.atualizar(vcf);
         */
        System.out.println("Importacao ok");
        long elapsed = System.currentTimeMillis() - start;
        System.out.println("Tempo: " + elapsed / 60000);
        return "COMPLETED";
    }

    //fim
    //@Override
    public String process_ok() {
        Patient patient = new Patient();
        System.out.println("Processando... MeuBatchlet.process");

        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Properties jobParameters = jobOperator.getParameters(jobContext.getExecutionId());
        String resourceName = (String) jobParameters.get("nome");
        String arquivo = (String) jobParameters.get("arquivo");
        //patient = patientSB.getPatient();

        try {
            String pacientIdT = (String) jobParameters.get("pacientId");
            Long pacienteId = Long.valueOf(pacientIdT);
            System.out.println(":D peguei o id do paciente: " + pacienteId);
            patient = patientService.findById(pacienteId);
        } catch (Exception ex) {
            System.out.println("Erro ao converter pacienteId: " + ex.getMessage());
            System.out.println("--> " + (String) jobParameters.get("pacientId"));
        }

        Vcf vcf = new Vcf();
        vcf.setStatus(VcfStatus.importando);
        vcf.setPaciente(patient);
        vcf.setDataImportacao(new Date());
        vcfService.adicionar(vcf);

        //System.out.println("Arquivo: " + arquivo);
        System.out.println("Nome: " + resourceName);

        for (String ln : arquivo.split("\n")) {
            
            
            
            Variante v = this.makeVariante(vcf, ln);

            varianteService.persiste(v);
        }

//        Vcf vcf = new Vcf();
//        vcf.setNome("Teste Lote");
        //vcf.setDataImportacao(new Date());
        //this.importar(arquivo, vcf);
        variantes = varianteDao.findByVCF(vcf.getId());

        try {
            //aqui marcelo
            //VcfMetadata vcfM = vcfMetadataService.makeMetadata(vcf);
            VcfMetadata vcfM = this.makeMetadata(vcf);
            vcfMetadataService.persiste(vcfM);
        } catch (Exception ex) {
            System.out.println("Erro ao gerar metadados: " + ex.getMessage());
        }

        vcf.setStatus(VcfStatus.importado);
        //this.atualizar(vcf);
        vcfService.atualizar(vcf);
        System.out.println("Importacao ok");
        return "COMPLETED";
    }

    public Variante makeVariante(Vcf vcf, String ln) {
        Variante v = new Variante();
        v.setVcf(vcf);
        String[] linha = ln.split("\t");
        v.setCromossomo(cromossomoService.buscarOuCriar(linha[0]));
        v.setPosition(linha[1]);
        String[] referenceAlternate = linha[2].split(">");
        v.setReferencia(referenceAlternate[0]);
        v.setAlterado(referenceAlternate[1]);
        v.setAllelicDeph(linha[7]);
        //v.setAllelicDeph1(1);
        //v.setAllelicDeph2(2);
        //v.setAlterado("teste9");
        Gene gene = geneService.findBySymbol(linha[3]);
        if (gene == null) {
            gene = new Gene();
            gene.setSymbol(linha[3]);
            gene.setName(linha[4]);
            geneService.persiste(gene);
        }
        v.setGene(gene);

        v.setUmdPredictor(umdPredictorService.findOrCreate(linha[5]));
        v.setZygosity(zygosityService.findOrCreate(linha[6]));
        v.setAllelicDeph(linha[7]);
        v.setFilter(filterService.findOrCreate(linha[8]));
        v.setHgvsC(linha[9]);
        v.setHgvsP(linha[10]);
        v.setIdSNP(linha[11]);
        v.setExonIntron(linha[12]);
        v.setType(typeService.findOrCreate(linha[13]));
        v.setEffect(effectService.findOrCreate(linha[14]));
        v.setImpact(impactService.findOrCreate(linha[15]));
        v.setClinvarSignificance(clinvarSignificanceService.findOrCreate(linha[16]));

        v.setClinvarDisease(clinvarDiseaseService.findOrCreate(linha[17]));
        v.setClinvarAccession(clinvarAccessionService.findOrCreate(linha[18]));
        v.setClinvarAlleleType(clinvarAlleleTypeService.findOrCreate(linha[19]));
        v.setClinvarAlleleOrigin(clinvarAlleleOriginService.findOrCreate(linha[20]));
        v.setSift(siftService.findOrCreate(linha[21]));

        v.setPolyphenHdiv(polyphenHdivService.findOrCreate(linha[22]));
        v.setPolyphenHvar(polyphenHvarService.findOrCreate(linha[23]));
        v.setMutationTaster(mutationTasterService.findOrCreate(linha[24]));
        v.setLrt(lrtService.findOrCreate(linha[25]));
        v.setGerpRsScore(linha[26]);
        v.setGerpNeutralRate(linha[27]);
        v.setFeature(featureService.findOrCreate(linha[28]));
        v.setEnsembl(ensemblService.findOrCreate(linha[29]));
        v.setVertebrateGenomesConservationScore(linha[30]);
        v.setInterproDomain(interproDomainService.findOrCreate(linha[31]));
        v.setVariantStatus(variantStatusService.findOrCreate(linha[32]));
        v.setGenoType(genoTypeService.findOrCreate(linha[33]));
        v.setReadDepth(linha[34]);
        v.setAlleleMutFraction(linha[35]);
        v.setMeanBaseQuality(linha[36]);
        v.setVarintType(linha[37]);
        v.setValidate(linha[38]);
        v.setDonorSpliceSite(linha[39]);
        v.setAcceptorSpliceSite(linha[40]);
        v.setMutation(linha[41]);
        v.setEuropeanVarintFreq(new DoubleFactory().make(linha[42]));
        v.setAfricanVarintFreq(new DoubleFactory().make(linha[43]));
        v.setAsianVarintFreq(new DoubleFactory().make(linha[44]));
        v.setAmericanVarintFreq(new DoubleFactory().make(linha[45]));
        v.setWholeVarintFreq(new DoubleFactory().make(linha[46]));
        return v;
    }

    private VcfMetadata makeMetadata(Vcf vcf) {
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

        System.out.println("Metadados ok");

        return vmd;
    }

    public int getQtdVariante() {
        return variantes.size();
    }

    public Set<Cromossomo> getCromossomos() {
        try {
            return new HashSet<>(
                    variantes.stream()
                            .map(v -> v.getCromossomo())
                            .distinct()
                            .filter(v -> Objects.nonNull(v))
                            .collect(Collectors.toSet())
            );
        } catch (Exception ex) {
            System.out.println("Erro MeuBatchlet.getCromossomos: " + ex.getMessage());
            return null;
        }
    }

    public Set<String> getReferencias() {
        try {
            return variantes.stream()
                    .map(v -> v.getReferencia())
                    .distinct()
                    .filter(v -> Objects.nonNull(v))
                    .collect(Collectors.toSet());
        } catch (Exception ex) {
            System.out.println("Erro MeuBatchlet.getReferencias: " + ex.getMessage());
            return null;
        }
    }

    public Set<String> getAlterados() {
        try {
            return variantes.stream()
                    .map(v -> v.getAlterado())
                    .distinct()
                    .filter(v -> Objects.nonNull(v))
                    .collect(Collectors.toSet());
        } catch (Exception ex) {
            System.out.println("Erro MeuBatchlet.getAlterados: " + ex.getMessage());
            return null;
        }
    }

    public Long getPostionMax() {
        try {
            return variantes.stream()
                    .max((v1, v2) -> Long.compare(v1.getPosition(), v2.getPosition()))
                    .get()
                    .getPosition();
        } catch (Exception ex) {
            System.out.println("Erro MeuBatchlet.getPositionMax: " + ex.getMessage());
            return null;
        }
    }

    public Long getPostionMin() {
        try {
            return variantes.stream()
                    .min((v1, v2) -> Long.compare(v1.getPosition(), v2.getPosition()))
                    .get()
                    .getPosition();
        } catch (Exception ex) {
            System.out.println("Erro MeuBatchlet.getPositonMin: " + ex.getMessage());
            return null;
        }
    }

    public Set<Gene> getGenes() {
        try {
            return variantes.stream()
                    .map(v -> v.getGene())
                    .distinct()
                    .filter(v -> Objects.nonNull(v))
                    .collect(Collectors.toSet());
        } catch (Exception ex) {
            System.out.println("Erro MeuBatchlet.getGenes: " + ex.getMessage());
            return null;
        }
    }

    public Set<UmdPredictor> getUmdPredictors() {
        System.out.println("MeuBatchlet.getUMDPredictors");
        try {
            Set<UmdPredictor> out = variantes.stream()
                    .map(v -> v.getUmdPredictor())
                    .distinct()
                    .filter(v -> Objects.nonNull(v))
                    .collect(Collectors.toSet());
            System.out.println("---> " + out.size());
            for (UmdPredictor u : out) {
                System.out.println("u ---> " + u.getName() + " id: " + u.getId());
            }

            return out;
        } catch (Exception ex) {
            System.out.println("Erro MeuBatchlet.getUmdPredictos: " + ex.getMessage());
            return null;
        }
    }

    public Set<Effect> getEffect() {
        try {
            return variantes.stream()
                    .map(v -> v.getEffect())
                    .distinct()
                    .collect(Collectors.toSet());
        } catch (Exception ex) {
            System.out.println("Erro MeuBatchlet.getEffect: " + ex.getMessage());
            return null;
        }
    }

    public Set<Sift> getSift() {
        try {
            return variantes.stream()
                    .map(v -> v.getSift())
                    .distinct()
                    .filter(v -> Objects.nonNull(v))
                    .collect(Collectors.toSet());
        } catch (Exception ex) {
            System.out.println("Erro MeuBatchlet.getSift: " + ex.getMessage());
            return null;
        }
    }

    public VcfMetadata findByVcfId(Long id) {
        try {
            List<VcfMetadata> list = vcfMetadataDao.findByVcf(id);
            if (list.size() == 1) {
                return list.get(0);
            }
            return null;
        } catch (Exception ex) {
            System.out.println("Erro MeuBatchlet.getVcfId: " + ex.getMessage());
            return null;
        }
    }

    private Map<Gene, Integer> getMapGene() {
        try {
            HashMap<Gene, Integer> map = new HashMap<Gene, Integer>();
            for (Variante v : variantes) {
                Integer total = map.get(v.getGene());
                if (total == null) {
                    total = 0;
                }
                total++;
                map.put(v.getGene(), total);
            }
            return map;
        } catch (Exception ex) {
            System.out.println("Erro MeuBatchlet.getMapGene: " + ex.getMessage());
            return null;
        }

    }

    private Set<Zygosity> getZygositys() {
        try {
            return variantes.stream()
                    .map(v -> v.getZygosity())
                    .distinct()
                    .filter(v -> Objects.nonNull(v))
                    .collect(Collectors.toSet());
        } catch (Exception ex) {
            System.out.println("Erro MeuBatchlet.getZygositys: " + ex.getMessage());
            return null;
        }
    }

    public Set<String> getAllelicDephs() {
        try {
            return variantes.stream()
                    .map(v -> v.getAllelicDeph())
                    .distinct()
                    .filter(v -> Objects.nonNull(v))
                    .collect(Collectors.toSet());
        } catch (Exception ex) {
            System.out.println("Erro MeuBatchlet.getAllelicDephs: " + ex.getMessage());
            return null;
        }
    }

    private Set<Filter> getFilters() {
        try {
            return variantes.stream()
                    .map(v -> v.getFilter())
                    .distinct()
                    .filter(v -> Objects.nonNull(v))
                    .collect(Collectors.toSet());
        } catch (Exception ex) {
            System.out.println("Erro MeuBatchlet.getFilters: " + ex.getMessage());
            return null;
        }
    }

    private Set<String> getHgvsCs() {
        try {
            return variantes.stream()
                    .map(v -> v.getHgvsC())
                    .distinct()
                    .filter(v -> Objects.nonNull(v))
                    .collect(Collectors.toSet());
        } catch (Exception ex) {
            System.out.println("Erro MeuBatchlet.getHgvsCS: " + ex.getMessage());
            return null;
        }
    }

    private Set<String> getHgvsPs() {
        try {
            return variantes.stream()
                    .map(v -> v.getHgvsP())
                    .distinct()
                    .filter(v -> Objects.nonNull(v))
                    .collect(Collectors.toSet());
        } catch (Exception ex) {
            System.out.println("Erro MeuBatchlet.getHgvsPS: " + ex.getMessage());
            return null;
        }
    }

    private Set<String> getIdSNPs() {
        try {
            return variantes.stream()
                    .map(v -> v.getIdSNP())
                    .distinct()
                    .filter(v -> Objects.nonNull(v))
                    .collect(Collectors.toSet());
        } catch (Exception ex) {
            System.out.println("Erro MeuBatchlet.getIdSNPs: " + ex.getMessage());
            return null;
        }
    }

    private Set<Integer> getExonIntrons() {
        try {
            return variantes.stream()
                    .map(v -> v.getExonIntron())
                    .distinct()
                    .filter(v -> Objects.nonNull(v))
                    .collect(Collectors.toSet());
        } catch (Exception ex) {
            System.out.println("Erro MeuBatchlet.getExonIntrons: " + ex.getMessage());
            return null;
        }
    }

    private Set<Type> getTypies() {
        try {
            return variantes.stream()
                    .map(v -> v.getType())
                    .distinct()
                    .filter(v -> Objects.nonNull(v))
                    .collect(Collectors.toSet());
        } catch (Exception ex) {
            System.out.println("Erro MeuBatchlet.getTypies: " + ex.getMessage());
            return null;
        }
    }

    private Set<Impact> getImpacts() {
        try {
            return variantes.stream()
                    .map(v -> v.getImpact())
                    .distinct()
                    .filter(v -> Objects.nonNull(v))
                    .collect(Collectors.toSet());
        } catch (Exception ex) {
            System.out.println("Erro MeuBatchlet.getImpacts: " + ex.getMessage());
            return null;
        }
    }

    private Set<ClinvarSignificance> getClinvarSignificances() {
        try {
            return variantes.stream()
                    .map(v -> v.getClinvarSignificance())
                    .distinct()
                    .filter(v -> Objects.nonNull(v))
                    .collect(Collectors.toSet());
        } catch (Exception ex) {
            System.out.println("Erro MeuBatchlet.getClinvarSignificance: " + ex.getMessage());
            return null;
        }
    }

    private Set<ClinvarDisease> getClinvarDiseases() {
        return variantes.stream()
                .map(v -> v.getClinvarDisease())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<ClinvarAccession> getClinvarAccessions() {
        return variantes.stream()
                .map(v -> v.getClinvarAccession())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<ClinvarAlleleType> getClinvarAlleleTypies() {
        return variantes.stream()
                .map(v -> v.getClinvarAlleleType())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<ClinvarAlleleOrigin> getClinvarAlleleOrigins() {
        return variantes.stream()
                .map(v -> v.getClinvarAlleleOrigin())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<PolyphenHdiv> getPolyphenHdivs() {
        return variantes.stream()
                .map(v -> v.getPolyphenHdiv())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<PolyphenHvar> getPolyphenHvars() {
        return variantes.stream()
                .map(v -> v.getPolyphenHvar())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<MutationTaster> getMutationTasters() {
        return variantes.stream()
                .map(v -> v.getMutationTaster())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<Lrt> getLrts() {
        return variantes.stream()
                .map(v -> v.getLrt())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<Double> getGerpRsScores() {
        return variantes.stream()
                .map(v -> v.getGerpRsScore())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<Double> getGerpNeutralRates() {
        return variantes.stream()
                .map(v -> v.getGerpNeutralRate())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<Feature> getFeatures() {
        return variantes.stream()
                .map(v -> v.getFeature())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<Ensembl> getEnsembls() {
        return variantes.stream()
                .map(v -> v.getEnsembl())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<Double> getVertebrateGenomesConservationScores() {
        return variantes.stream()
                .map(v -> v.getVertebrateGenomesConservationScore())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<InterproDomain> getInterproDomains() {
        return variantes.stream()
                .map(v -> v.getInterproDomain())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<VariantStatus> getVariantStatus() {
        return variantes.stream()
                .map(v -> v.getVariantStatus())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<GenoType> getGenoTypies() {
        return variantes.stream()
                .map(v -> v.getGenoType())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<String> getReadDepths() {
        return variantes.stream()
                .map(v -> v.getReadDepth())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<Double> getAlleleMutFractions() {
        return variantes.stream()
                .map(v -> v.getAlleleMutFraction())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<Double> getMeanBaseQualities() {
        return variantes.stream()
                .map(v -> v.getMeanBaseQuality())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<String> getVarintTypies() {
        return variantes.stream()
                .map(v -> v.getVarintType())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<Boolean> getValidates() {
        return variantes.stream()
                .map(v -> v.getValidate())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<Boolean> getDonorSpliceSites() {
        return variantes.stream()
                .map(v -> v.getDonorSpliceSite())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<Boolean> getAcceptorSpliceSites() {
        return variantes.stream()
                .map(v -> v.getAcceptorSpliceSite())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<Boolean> getMutations() {
        return variantes.stream()
                .map(v -> v.getMutation())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<Double> getEuropeanVarintFreqs() {
        return variantes.stream()
                .map(v -> v.getEuropeanVarintFreq())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<Double> getAfricanVarintFreqs() {
        return variantes.stream()
                .map(v -> v.getAfricanVarintFreq())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<Double> getAsianVarintFreqs() {
        return variantes.stream()
                .map(v -> v.getAsianVarintFreq())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<Double> getAmericanVarintFreqs() {
        return variantes.stream()
                .map(v -> v.getAmericanVarintFreq())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Set<Double> getWholeVarintFreqs() {
        return variantes.stream()
                .map(v -> v.getWholeVarintFreq())
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .collect(Collectors.toSet());
    }

    private Double getPrevalenceMin() {
        try {
            Double r = variantes.stream()
                    .filter(v -> v.getWholeVarintFreq() != null)
                    .min((v1, v2) -> Double.compare(v1.getWholeVarintFreq(), v2.getWholeVarintFreq()))
                    .get()
                    .getWholeVarintFreq();
            return r;
        } catch (Exception ex) {
            System.out.println("Erro: VcfMetadataService.getPrevalenceMin:" + ex.getMessage());
        }
        return 0d;
    }

    private Double getPrevalenceMax() {
        try {
            return variantes.stream()
                    .filter(v -> v.getWholeVarintFreq() != null)
                    .max((v1, v2) -> Double.compare(v1.getWholeVarintFreq(), v2.getWholeVarintFreq()))
                    .get()
                    .getWholeVarintFreq();
        } catch (Exception ex) {
            System.out.println("Erro: VcfMetadataService.getPrevalenceMaxn:" + ex.getMessage());
        }
        return 0d;
    }

}
