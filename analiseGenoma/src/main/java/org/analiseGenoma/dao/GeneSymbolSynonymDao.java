package org.analiseGenoma.dao;

import java.io.Serializable;
import javax.transaction.Transactional;
import org.analiseGenoma.model.GeneSymbolSynonym;

public class GeneSymbolSynonymDao extends DAO<GeneSymbolSynonym> implements Serializable {

    public GeneSymbolSynonymDao() {
        super(GeneSymbolSynonym.class);
    }
//    @Inject
//    private GeneDao geneDao;

    @Transactional
    @Override    
    public void persist(GeneSymbolSynonym gs) {
//        Gene gene = gs.getGene();
//        manager.merge(gene);
//        gs.setGene(gene);
        manager.persist(gs);
    }
    

}
