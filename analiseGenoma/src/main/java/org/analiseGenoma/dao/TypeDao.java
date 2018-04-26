package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.Type;

public class TypeDao extends DAO<Type> {

    public TypeDao() {
        super(Type.class);
    }

    public List<Type> findByName(String name) {
        List<Type> list = null;
        try {
            Query query = manager.createQuery("SELECT i FROM Type i WHERE i.name like :name");
            query.setParameter("name", name.toUpperCase());
            list = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return list;
    }
}
