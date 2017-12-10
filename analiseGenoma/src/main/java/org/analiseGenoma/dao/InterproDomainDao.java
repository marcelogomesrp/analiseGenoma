package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.InterproDomain;

public class InterproDomainDao extends DAO<InterproDomain> {

    public InterproDomainDao() {
        super(InterproDomain.class);
    }

    public List<InterproDomain> findByName(String name) {
        List<InterproDomain> list = null;
        try {
            Query query = manager.createQuery("SELECT i FROM InterproDomain i WHERE i.name like :name");
            query.setParameter("name", name);
            list = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return list;
    }
}
