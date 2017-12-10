package org.analiseGenoma.service;

import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.FiltroDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.Filtro;

@Named
public class FiltroService extends Service<Filtro>{
    @Inject private GeneDao geneDao;
    @Inject private CromossomoDao cromossomoDao;
    
    public FiltroService() {
        super(Filtro.class);
    }
    
    private FiltroDao getDao(){
        return ((FiltroDao) dao);
    }

    
    public Filtro buscarPorAnalise(Long idAnalise) {
        Filtro filtro =  getDao().buscarPorAnalise(idAnalise);
        filtro.setGenes(getDao().buscarGene(filtro.getId()));
        filtro.setCromossomos(getDao().buscarCromossomos(filtro.getId()));
        //filtro.setGenes(geneDao.buscarAnalise(idAnalise));
        //filtro.setCromossomos(cromossomoDao.buscarPorAnalise(idAnalise));
        return filtro;
    }
}
