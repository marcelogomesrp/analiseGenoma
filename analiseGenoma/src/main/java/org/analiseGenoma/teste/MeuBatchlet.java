/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.analiseGenoma.teste;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Properties;
import javax.batch.api.AbstractBatchlet;
import javax.batch.operations.JobOperator;

import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.context.JobContext;

import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.FiltroDao;
import org.analiseGenoma.dao.VarianteDao;
import org.analiseGenoma.dao.VcfDao;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.Patient;
import org.analiseGenoma.model.Variante;
import org.analiseGenoma.model.Vcf;
import org.analiseGenoma.model.VcfMetadata;
import org.analiseGenoma.model.VcfStatus;
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
import org.analiseGenoma.sessionbean.PatientSB;

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
    @Inject
    private PatientSB patientSB;
    @Inject
    private VcfService vcfService;

    //fim
    @Override
    public String process() {
        //Patient patient = new Patient();
        System.out.println("Processando... MeuBatchlet.process");
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Properties jobParameters = jobOperator.getParameters(jobContext.getExecutionId());
        String resourceName = (String) jobParameters.get("nome");
        String arquivo = (String) jobParameters.get("arquivo");
        //patient = patientSB.getPatient();
        /*
        try {
            String pacientIdT = (String) jobParameters.get("pacientId");
            Long pacienteId = Long.valueOf(pacientIdT);
            System.out.println(":D peguei o id do paciente: " + pacienteId);
        } catch (Exception ex) {
            System.out.println("Erro ao converter pacienteId: " + ex.getMessage());
            System.out.println("--> " + (String) jobParameters.get("pacientId"));
        }
        */
        /*
        Vcf vcf = new Vcf();        
        vcf.setPaciente(patientSB.getPatient());
        vcf.setStatus(VcfStatus.importando);
        vcfService.adicionar(vcf);
*/
        //System.out.println("Arquivo: " + arquivo);
        System.out.println("Nome: " + resourceName);
        for (String ln : arquivo.split("\n")) {
            Variante v = new Variante();
            //v.setVcf(vcf);
            String[] linha = ln.split("\t");
            v.setCromossomo(cromossomoService.buscarOuCriar(linha[0]));

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

            varianteService.persiste(v);
        }

//        Vcf vcf = new Vcf();
//        vcf.setNome("Teste Lote");
        //vcf.setDataImportacao(new Date());
        //this.importar(arquivo, vcf);
/*
        try {
            //aqui marcelo
            VcfMetadata vcfM = vcfMetadataService.makeMetadata(vcf);
            vcfMetadataService.persiste(vcfM);
        } catch (Exception ex) {
            System.out.println("Erro ao gerar metadados: " + ex.getMessage());
        }
        */

        //vcf.setStatus(VcfStatus.importado);

        //this.atualizar(vcf);
        return "COMPLETED";
    }

    public void importar(String contents, Vcf vcf) {
        //este esta sendo usado
        String[] arquivol = contents.split("\n");
        Variante variante = null;
        for (String ln : arquivol) {
            variante = new Variante();
            variante.setVcf(vcf);
            String[] linha = ln.split("\t");
            variante.setCromossomo(cromossomoService.buscarOuCriar(linha[0]));
            variante.setPosition(linha[1]);
            String[] referenceAlternate = linha[2].split(">");
            variante.setReferencia(referenceAlternate[0]);
            variante.setAlterado(referenceAlternate[1]);
            //variante.setAllelicDeph(linha[7]); 
            /*
            Gene gene = geneService.findBySymbol(linha[3]);
            if (gene == null) {
                gene = new Gene();
                gene.setSymbol(linha[3]);
                gene.setName(linha[4]);
                geneService.persiste(gene);
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

             */
            varianteService.persiste(variante);
        }
        try {
            //aqui marcelo
            VcfMetadata vcfM = vcfMetadataService.makeMetadata(vcf);
            vcfMetadataService.persiste(vcfM);
        } catch (Exception ex) {
            System.out.println("Erro ao gerar metadados: " + ex.getMessage());
        }

        vcf.setStatus(VcfStatus.importado);
        //this.atualizar(vcf);

    }

}
