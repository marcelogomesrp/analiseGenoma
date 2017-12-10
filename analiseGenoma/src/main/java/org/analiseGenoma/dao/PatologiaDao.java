package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.Patologia;

public class PatologiaDao extends DAO<Patologia> {

    public PatologiaDao() {
        super(Patologia.class);
    }

    public List<Patologia> buscarLikeNome(String nome) {
        try {
            Query query = manager.createQuery("SELECT p FROM Patologia p WHERE p.nome like :nome");
            query.setParameter("nome", nome);
            List<Patologia> patologias = query.getResultList();
            return patologias;
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
            return null;
        }
    }
    
    public Patologia buscarNome(String nome) {
        try {
            Query query = manager.createQuery("SELECT p FROM Patologia p WHERE p.nome = :nome");
            query.setParameter("nome", nome);
            return (Patologia) query.getSingleResult();
            //List<Patologia> patologias = query.getResultList();
            //return patologias;
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
            return null;
        }
    }
    

    public List<Patologia>  buscarCid(String cid) {
        try {
            Query query = manager.createQuery("SELECT p FROM Patologia p WHERE p.cid like :cid");
            query.setParameter("cid", cid);
            List<Patologia> patologias = query.getResultList();
            return patologias;
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
            return null;
        }
    }
}