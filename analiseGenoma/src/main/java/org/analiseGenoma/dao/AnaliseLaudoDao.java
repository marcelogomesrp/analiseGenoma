package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.AnaliseLaudo;

public class AnaliseLaudoDao extends DAO<AnaliseLaudo> {

    public AnaliseLaudoDao() {
        super(AnaliseLaudo.class);
    }

    public List<AnaliseLaudo> find(Analise analise) {
        List<AnaliseLaudo> list = null;
        try {
            //Query query = manager.createQuery("SELECT i FROM Effect i WHERE i.name like :name");
            Query query = manager.createQuery("SELECT a FROM AnaliseLaudo a WHERE a.analise = :analise", AnaliseLaudo.class);
            query.setParameter("analise", analise);
            list = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return list;
    }
    
}
