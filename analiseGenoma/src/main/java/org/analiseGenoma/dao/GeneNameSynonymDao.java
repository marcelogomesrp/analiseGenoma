package org.analiseGenoma.dao;

import java.io.Serializable;
import org.analiseGenoma.model.GeneNameSynonym;

public class GeneNameSynonymDao extends DAO<GeneNameSynonym> implements Serializable{

    public GeneNameSynonymDao() {
        super(GeneNameSynonym.class);
    }
    
}
