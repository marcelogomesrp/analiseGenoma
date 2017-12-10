package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.VarianteRevisada;

public class VarianteRevisadaDao extends DAO<VarianteRevisada> {

    public VarianteRevisadaDao() {
        super(VarianteRevisada.class);
    }

    public List<VarianteRevisada> findByName(String name) {
        List<VarianteRevisada> list = null;
        try {
            Query query = manager.createQuery("SELECT i FROM VarianteRevisada i WHERE i.name like :name");
            query.setParameter("name", name);
            list = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return list;
    }
}
