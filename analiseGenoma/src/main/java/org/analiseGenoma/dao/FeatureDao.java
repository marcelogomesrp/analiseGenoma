package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.Feature;

public class FeatureDao extends DAO<Feature> {

    public FeatureDao() {
        super(Feature.class);
    }

    public List<Feature> findByName(String name) {
        List<Feature> list = null;
        try {
            Query query = manager.createQuery("SELECT i FROM Feature i WHERE i.name like :name");
            query.setParameter("name", name.toUpperCase());
            list = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return list;
    }
}
