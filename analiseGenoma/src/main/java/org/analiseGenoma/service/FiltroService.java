package org.analiseGenoma.service;

import java.util.HashSet;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.FiltroDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.Filtro;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.VcfMetadata;

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

    public FiltroService() {
        super(Filtro.class);
    }

    private FiltroDao getDao() {
        return ((FiltroDao) dao);
    }

    public Filtro buscarPorAnalise(Long idAnalise) {
        Filtro filtro =  getDao().buscarPorAnalise(idAnalise);
        //List<Gene> lgenes = getDao().buscarGene(filtro.getId());
        filtro.setGenes(getDao().buscarGene(filtro.getId()));
        filtro.setCromossomos(filtroDao.buscarCromossomos(filtro.getId()));        
        //filtro.setCromossomos(getDao().buscarCromossomos(filtro.getId()));
        //filtro.setGenes(geneDao.buscarAnalise(idAnalise));
        //filtro.setCromossomos(cromossomoDao.buscarPorAnalise(idAnalise));
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
        filtro.setGenes(new HashSet<>( vcfMetada.getGenes() ) );
        
        return filtro;
    }

}
