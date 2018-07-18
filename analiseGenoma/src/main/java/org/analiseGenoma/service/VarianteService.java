package org.analiseGenoma.service;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.VarianteDao;
import org.analiseGenoma.model.Variante;

@Named
public class VarianteService extends Service<Variante> {

    @Inject
    private CromossomoService cromossomoService;
    @Inject
    private GeneService geneService;
    //@Inject private VcfService vcfService;
    @Inject
    private UmdPredictorService umdPredictorService;
    @Inject
    private ZygosityService zygosityService;

    public VarianteService() {
        super(Variante.class);
    }

    private VarianteDao getDao() {
        return ((VarianteDao) dao);
    }

    @Override
    @Transactional
    public void persiste(Variante variante) {
//        //if(variante.getVcf() != null)
//            //vcfService.atualizar(variante.getVcf());
//        if(variante.getCromossomo() !=null)
//            cromossomoService.atualizar(variante.getCromossomo());
//        if(variante.getGene() != null)
////            geneService.atualizar(variante.getGene());
////        if(variante.getUmdPredictor() != null)
////            umdPredictorService.atualizar(variante.getUmdPredictor());
//        if(variante.getZygosity() != null)
//            zygosityService.merge(variante.getZygosity());

        //super.persiste(variante); 
        try {
            getDao().persist(variante);
        } catch (Exception ex) {
            System.out.println("Erro ao persistir variante: " + ex.getMessage());
        }
    }

    public int runSP() {
        return getDao().runSP();
    }

    public int runSP(String cromossomo, String gene, String referencia, String alterado,
            String umdPredictor, String zygosity, Integer allelicDeph1, Integer allelicDeph2, String filter,
            String hgvsC, String hgvsP, String idSNP, Integer exonIntron,
            String type, String effect, String impacto, String clinvarSignificance,
            String clinvarDisease, String clinvarAccession, String clinvarAlleleType, String clinvarAlleleOrigin,
            String sift,String polyphenHiv,String polyphenHvar, String mutationTaster, String lrt, Double gerpRsScore, Double gerpNeutralRate,
            String feature, String ensembl, Double vertebrateGenomesConservationScore, String interproDomain, String variantStatus, String genoType,
            String readDepth, Double alleleMutFraction, Double meanBaseQuality, String varintType, Boolean validate,
            Boolean donorSpliceSite,Boolean  acceptorSpliceSite,Boolean  mutation,
            Double europeanVarintFreq, Double africanVarintFreq, Double asianVarintFreq, Double americanVarintFreq, Double wholeVarintFreq,
            Long vcfId, Long position
    ) {

        
        
        return getDao().runSP(cromossomo, gene, referencia, alterado,
                umdPredictor, zygosity, allelicDeph1, allelicDeph2,
                filter, hgvsC, hgvsP, idSNP, exonIntron, type, effect, impacto, clinvarSignificance,
                clinvarDisease, clinvarAccession, clinvarAlleleType, clinvarAlleleOrigin,
                sift, polyphenHiv, polyphenHvar, mutationTaster, lrt, gerpRsScore, gerpNeutralRate,
                feature, ensembl, vertebrateGenomesConservationScore, interproDomain, variantStatus, genoType,
                readDepth, alleleMutFraction, meanBaseQuality, varintType, validate, 
                donorSpliceSite, acceptorSpliceSite, mutation,                 
                europeanVarintFreq, africanVarintFreq, asianVarintFreq, americanVarintFreq, wholeVarintFreq,
                vcfId, position
        );
    }

    public int runSPMetadados(Long vcfId) {
        return getDao().runSPMetadados(vcfId);
    }

}
