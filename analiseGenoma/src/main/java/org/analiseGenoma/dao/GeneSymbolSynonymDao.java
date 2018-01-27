package org.analiseGenoma.dao;

import java.io.Serializable;
import org.analiseGenoma.model.GeneSymbolSynonym;

public class GeneSymbolSynonymDao extends DAO<GeneSymbolSynonym> implements Serializable {

    public GeneSymbolSynonymDao() {
        super(GeneSymbolSynonym.class);
    }

}
