package org.analiseGenoma.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.Disease;
import org.analiseGenoma.model.Gene;

public class DiseaseDao extends DAO<Disease> {

    public DiseaseDao() {
        super(Disease.class);
    }

    public List<Disease> buscarLikeNome(String name) {
        try {
            Query query = manager.createQuery("SELECT p FROM Disease p WHERE p.name like :name");
            query.setParameter("name", name);
            List<Disease> patologias = query.getResultList();
            return patologias;
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
            return null;
        }
    }

    public Disease buscarNome(String name) {
        try {
            //Query query = manager.createQuery("SELECT p FROM Patologia p WHERE p.nome = :nome");
            Query query = manager.createQuery("SELECT p FROM Disease p WHERE p.name = :name");
            query.setParameter("name", name);
            return (Disease) query.getSingleResult();
            //List<Patologia> patologias = query.getResultList();
            //return patologias;
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
            return null;
        }
    }

    public List<Disease> buscarCid(String cid) {
        try {
            Query query = manager.createQuery("SELECT p FROM Disease p WHERE p.cid like :cid");
            query.setParameter("cid", cid);
            List<Disease> patologias = query.getResultList();
            return patologias;
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
            return null;
        }
    }

    public Disease findByName(String name) {
        return this.getFirstOrNull(this.findByProperty("name", name));
    }

    public List<String> buscarNameByLikeName(String name) {
        try {
            Query query = manager.createQuery("SELECT p.name FROM Disease p WHERE p.name like :name ORDER BY p.name");
            query.setParameter("name", name + "%");
            query.setMaxResults(5);
            query.setHint("org.hibernate.cacheable", true);
            return query.getResultList();
        } catch (Exception ex) {
            System.out.println("Erro DiseaseDao.buscarByLikeName: " + ex.getMessage());
        }
        return new ArrayList<>();

    }

    public List<Disease> findByLikeName(String name) {
        try {
            Query query = manager.createQuery("SELECT p FROM Disease p WHERE p.name like :name ORDER BY p.name");
            query.setParameter("name", name + "%");
            query.setMaxResults(5);
            query.setHint("org.hibernate.cacheable", true);
            return query.getResultList();
        } catch (Exception ex) {
            System.out.println("Erro DiseaseDao.findByLikeName: " + ex.getMessage());
        }
        return new ArrayList<>();
    }

    public List<Disease> findByGenes(List<Gene> genes) {
        try {
            //Set genesSet = new HashSet(genes);
            List<Long> genes2 = new ArrayList<>();
            for(Gene g: genes){
                genes2.add(g.getId());
            }
            //Query query = manager.createQuery("SELECT p FROM Disease p INNER JOIN Disease ORDER BY p.name");
            //Query query = manager.createQuery("SELECT db.disease FROM DbBioInfo db WHERE db.genes IN :genes");
            //Query query = manager.createQuery("SELECT db.disease FROM DbBioInfo db");
            //Query query = manager.createQuery("SELECT db.disease FROM DbBioInfo db WHERE db.genes IN (:genes)");
            Query query = manager.createQuery("SELECT db.disease FROM DbBioInfo db JOIN db.genes g WHERE g.id IN (:genes)");
            //query.setParameter("genes", genes);
            query.setParameter("genes", genes2);
            //query.setMaxResults(5);
            query.setHint("org.hibernate.cacheable", true);
            return query.getResultList();
        } catch (Exception ex) {
            System.out.println("Erro DiseaseDao.findByLikeName: " + ex.getMessage());
        }
        return new ArrayList<>();
    }

}

