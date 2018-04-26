package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.Effect;


public class EffectDao extends DAO<Effect> {

    public EffectDao() {
        super(Effect.class);
    }
    
    
    public List<Effect> findByName(String name) {
        List<Effect> list = null;
        try {
            Query query = manager.createQuery("SELECT i FROM Effect i WHERE i.name like :name");
            query.setParameter("name", name.toUpperCase());
            list = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return list;
    }
    
    public List<Effect> findLikeName(String name) {
        return this.findByProperty("name", name, DAO.MatchMode.ANYWHERE);
    }
}