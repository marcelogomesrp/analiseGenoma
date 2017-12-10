package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.MutationTaster;

public class MutationTasterDao extends DAO<MutationTaster> {

    public MutationTasterDao() {
        super(MutationTaster.class);
    }

    public List<MutationTaster> findByName(String name) {
        List<MutationTaster> list = null;
        try {
            Query query = manager.createQuery("SELECT i FROM MutationTaster i WHERE i.name like :name");
            query.setParameter("name", name);
            list = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return list;
    }
}
