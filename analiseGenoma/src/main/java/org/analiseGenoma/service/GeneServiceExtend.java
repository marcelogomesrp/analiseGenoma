package org.analiseGenoma.service;

import java.io.Serializable;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.Gene;

public class GeneServiceExtend implements Serializable {

    //@PersistenceContext
    //protected EntityManager manager;
    
    @Inject
    private GeneDao geneDao;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Gene buscarSimboloAdd(Gene gene) {
        Gene geneBd = geneDao.buscarSimbolo(gene.getSymbol());
        if (geneBd == null) {
            if (gene.getSynonymou() != null) {
                geneDao.merge(gene.getSynonymou());
            }
            geneDao.persist(gene);
            return gene;
        }
        return geneBd;
    }

    //@Transactional(Transactional.TxType.REQUIRES_NEW)
    @Transactional
    public Gene buscarSimboloAdd(Gene gene, String sinonimo) {
        Gene geneBd = geneDao.buscarSimbolo(gene.getSymbol());
        if (geneBd == null) {
            geneDao.persist(gene);
            geneBd = gene;
        }
        if (sinonimo != null) {
            sinonimo = sinonimo.replace(" ", "");
            for (String simbolo : sinonimo.split(",")) {
                Gene gs = geneDao.buscarSimbolo(simbolo);
                if(gs == null){
                    gs = new Gene();
                    gs.setName(geneBd.getName());
                    gs.setSymbol(simbolo);
                    gs.setSynonymou(geneBd);
                    geneDao.persist(gs);
                }
            }
        }
        //manager.flush();
        //manager.clear();
        return geneBd;
    }

}
