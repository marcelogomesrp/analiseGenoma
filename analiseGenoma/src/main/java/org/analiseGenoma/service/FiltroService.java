package org.analiseGenoma.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.DAO;
import org.analiseGenoma.dao.EffectDao;
import org.analiseGenoma.dao.FiltroDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.dao.UmdPredictorDao;
import org.analiseGenoma.dao.ZygosityDao;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.Disease;
import org.analiseGenoma.model.Effect;
import org.analiseGenoma.model.Filtro;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.UmdPredictor;
import org.analiseGenoma.model.VcfMetadata;
import org.analiseGenoma.model.Zygosity;

@Named
public class FiltroService extends Service<Filtro> {

    @Inject
    private GeneDao geneDao;
    @Inject
    private CromossomoDao cromossomoDao;
    @Inject
    private FiltroDao filtroDao;
    @Inject
    private VcfMetadataService vcfMetadataService;
    @Inject
    private EffectDao effectDao;
    @Inject
    private ZygosityDao zygosityDao;
    @Inject
    private UmdPredictorDao umdPredictorDao;
    
    
    
    public FiltroService() {
        super(Filtro.class);
    }

    private FiltroDao getDao() {
        return ((FiltroDao) dao);
    }

//    @Override
//    public Filtro findById(Long id) {
//        //return super.findById(id); 
//        
//    }
//    

    @Override
    public Filtro findById(Long id) {
        Filtro ret =  super.findById(id); 
        ret.setCromossomos(getDao().findCromossomoFromFiltro(ret));
        return ret;
    }
    
    
    

    public Filtro buscarPorAnalise(Long idAnalise) {
        Filtro filtro =  getDao().buscarPorAnalise(idAnalise);
        //List<Gene> lgenes = getDao().buscarGene(filtro.getId());
        filtro.setGenes(getDao().buscarGene(filtro.getId()));
        filtro.setCromossomos(filtroDao.buscarCromossomos(filtro.getId()));        
        //filtro.setCromossomos(getDao().buscarCromossomos(filtro.getId()));
        //filtro.setGenes(geneDao.buscarAnalise(idAnalise));
        //filtro.setCromossomos(cromossomoDao.buscarPorAnalise(idAnalise));
        filtro.setEffects(getDao().buscarEffect(filtro.getId()));
        
        filtro = this.loadFull(filtro);
        return filtro;
    }

    //*aqui defino o filtro inicial
    public Filtro makeFiltro(Analise analise) {
        Filtro filtro = new Filtro();
        VcfMetadata vcfMetada = vcfMetadataService.findByVcfId(analise.getVcf().getId());
        filtro.setAnalise(analise);
        filtro.setCromossomos(vcfMetada.getCromossomos());
        filtro.setPositionMin(vcfMetada.getPositonMin());
        filtro.setPositionMax(vcfMetada.getPositonMax());
        Disease disease =  analise.getPatologia();
        List<Gene> genes = geneDao.find(disease);
        if(genes == null){
            filtro.setGenes(new HashSet<>( vcfMetada.getGenes() ) );
        }else{
            if(genes.isEmpty()){
                filtro.setGenes(new HashSet<>( vcfMetada.getGenes() ) );
            }else{
                filtro.setGenes(new HashSet<>(genes));
            }
        }
        
        
        List<Effect> effects = new ArrayList<>();
        effectDao.findLikeName("missence").forEach(e -> effects.add(e));
        effectDao.findLikeName("splice").forEach(e -> effects.add(e));
        effectDao.findLikeName("inframe").forEach(e -> effects.add(e));
        
        filtro.setEffects(new HashSet<>(effects));
        
        if(disease.getInheritanceType() != null){
            
            if(disease.getInheritanceType().getType().contains("recessive")){
                //zygosities.add(zygosityDao.findByName("Homozygous"));
                filtro.setZygosities(new HashSet<>( zygosityDao.findByName("Homozygous")) );
            }else{
                if(disease.getInheritanceType().getType().contains("dominant")){
                    List<Zygosity> zygosities = new ArrayList<>();
                    zygosities.addAll(zygosityDao.findByName("Homozygous"));
                    zygosities.addAll(zygosityDao.findByName("Heterozygous"));
                    filtro.setZygosities(new HashSet<>( zygosities ) );
                }
            }
            
            
        }
        
        //4
//        filtro.setUmdPredictors(new HashSet<>(vcfMetada.getUmdPredictors()));
        List<UmdPredictor> listUmds = new ArrayList<>();
        listUmds.addAll(umdPredictorDao.findByName("none"));
        listUmds.addAll(umdPredictorDao.findByName("Pathogenic"));
        listUmds.addAll(umdPredictorDao.findByName("Probably pathogenic"));
        filtro.setUmdPredictors(new HashSet<>( listUmds ));
        
        filtro.setPrevalenceMin(vcfMetada.getPrevalenceMin());
        filtro.setPrevalenceMax(vcfMetada.getPrevalenceMax());
        
        //5
        if(disease.getPrevalence() != null){
            if(disease.getPrevalence() <= 0.001){
                filtro.setPrevalenceMax(0.001);
            }else{
                // nao raras
            }
        }
        
        
        
        
        
        return filtro;
    }

    public Filtro makeFiltroDefault(Analise analise) {
        Filtro filtro = new Filtro();
        VcfMetadata vcfMetada = vcfMetadataService.findByVcfId(analise.getVcf().getId());
        filtro.setAnalise(analise);
        filtro.setCromossomos(vcfMetada.getCromossomos());
        filtro.setPositionMin(vcfMetada.getPositonMin());
        filtro.setPositionMax(vcfMetada.getPositonMax());
        Disease disease =  analise.getPatologia();
        
        filtro.setGenes(new HashSet<>( vcfMetada.getGenes() ) );
        
        
        List<Effect> effects = new ArrayList<>();
        
//        effectDao.findLikeName("missence").forEach(e -> effects.add(e));
//        effectDao.findLikeName("splice").forEach(e -> effects.add(e));
//        effectDao.findLikeName("inframe").forEach(e -> effects.add(e));
//        
//        filtro.setEffects(new HashSet<>(effects));
        filtro.setEffects(vcfMetada.getEffects());
        
        filtro.setPrevalenceMin(vcfMetada.getPrevalenceMin());
        filtro.setPrevalenceMax(vcfMetada.getPrevalenceMax());
        
        
        filtro.setUmdPredictors(vcfMetada.getUmdPredictors());
        
        return filtro;        
    }

    public List<Filtro> findByName(String name) {
        return getDao().findByProperty("name", name, DAO.MatchMode.START);
    }

    public Filtro loadFull(Filtro filtro) {
        filtro = this.findById(filtro.getId());
        filtro.setGenes(getDao().buscarGene(filtro.getId()));
        filtro.setUmdPredictors(getDao().findUmdPerdictors(filtro));
        filtro.setEffects(getDao().buscarEffect(filtro.getId()));
        filtro.setZygosities(getDao().buscarZygocity(filtro));
        filtro.setFilters(getDao().buscarFilter(filtro));
        filtro.setImpacts(getDao().buscarImpact(filtro));
        //filtro.setHgvscs(getDao().buscarHgvsC(filtro.getId()));
//        filtro.setAlleciDeph1s(getDao().buscarAllelicDeph1(filtro));
//        filtro.setAlleciDeph2s(getDao().buscarAllelicDeph2(filtro));
        return filtro;
    }


}
