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
        Gene geneBd = geneDao.buscarSimbolo(gene.getSimbolo());
        if (geneBd == null) {
            if (gene.getNovoGene() != null) {
                geneDao.atualizar(gene.getNovoGene());
            }
            geneDao.adicionar(gene);
            return gene;
        }
        return geneBd;
    }

    //@Transactional(Transactional.TxType.REQUIRES_NEW)
    @Transactional
    public Gene buscarSimboloAdd(Gene gene, String sinonimo) {
        Gene geneBd = geneDao.buscarSimbolo(gene.getSimbolo());
        if (geneBd == null) {
            geneDao.adicionar(gene);
            geneBd = gene;
        }
        if (sinonimo != null) {
            sinonimo = sinonimo.replace(" ", "");
            for (String simbolo : sinonimo.split(",")) {
                Gene gs = geneDao.buscarSimbolo(simbolo);
                if(gs == null){
                    gs = new Gene();
                    gs.setNome(geneBd.getNome());
                    gs.setSimbolo(simbolo);
                    gs.setNovoGene(geneBd);
                    geneDao.adicionar(gs);
                }
            }
        }
        //manager.flush();
        //manager.clear();
        return geneBd;
    }

}
