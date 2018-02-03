package org.analiseGenoma.dao;

import java.io.Serializable;
import org.analiseGenoma.model.GeneDbBio;
import org.analiseGenoma.model.GeneDbBioPK;

public class GeneDbBioDao extends DAO<GeneDbBio> implements Serializable{

    public GeneDbBioDao() {
        super(GeneDbBio.class);
    }
    
    

    public GeneDbBio findById(GeneDbBio gd) {
        GeneDbBioPK id = new GeneDbBioPK();
        id.setDbBio(gd.getDbBio());
        id.setGene(gd.getGene());
        return manager.find(GeneDbBio.class, id);
    }
    
}
