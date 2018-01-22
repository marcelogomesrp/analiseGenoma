package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.Disease;

public class DiseaseDao extends DAO<Disease> {

    public DiseaseDao() {
        super(Disease.class);
    }

    public List<Disease> buscarLikeNome(String nome) {
        try {
            Query query = manager.createQuery("SELECT p FROM Patologia p WHERE p.nome like :nome");
            query.setParameter("nome", nome);
            List<Disease> patologias = query.getResultList();
            return patologias;
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
            return null;
        }
    }
    
    public Disease buscarNome(String nome) {
        try {
            Query query = manager.createQuery("SELECT p FROM Patologia p WHERE p.nome = :nome");
            query.setParameter("nome", nome);
            return (Disease) query.getSingleResult();
            //List<Patologia> patologias = query.getResultList();
            //return patologias;
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
            return null;
        }
    }
    

    public List<Disease>  buscarCid(String cid) {
        try {
            Query query = manager.createQuery("SELECT p FROM Patologia p WHERE p.cid like :cid");
            query.setParameter("cid", cid);
            List<Disease> patologias = query.getResultList();
            return patologias;
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
            return null;
        }
    }
}