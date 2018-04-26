package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.GenoType;

public class GenoTypeDao extends DAO<GenoType> {

    public GenoTypeDao() {
        super(GenoType.class);
    }

    public List<GenoType> findByName(String name) {
        List<GenoType> list = null;
        try {
            Query query = manager.createQuery("SELECT i FROM GenoType i WHERE i.name like :name");
            query.setParameter("name", name.toUpperCase());
            list = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return list;
    }
}
