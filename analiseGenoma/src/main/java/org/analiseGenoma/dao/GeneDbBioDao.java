package org.analiseGenoma.dao;

import java.io.Serializable;
import org.analiseGenoma.model.GeneDbBio;

public class GeneDbBioDao extends DAO<GeneDbBio> implements Serializable{

    public GeneDbBioDao() {
        super(GeneDbBio.class);
    }
    
}
