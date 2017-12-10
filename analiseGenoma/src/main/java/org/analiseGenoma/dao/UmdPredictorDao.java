package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.UmdPredictor;

public class UmdPredictorDao extends DAO<UmdPredictor> {

    public UmdPredictorDao() {
        super(UmdPredictor.class);
    }

    public List<UmdPredictor> findByName(String name) {
        List<UmdPredictor> list = null;
        try {
            Query query = manager.createQuery("SELECT i FROM UmdPredictor i WHERE i.name like :name");
            query.setParameter("name", name);
            list = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return list;
    }
}
