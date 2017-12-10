package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.Patologia;

public class GeneDao extends DAO<Gene> {

    public GeneDao() {
        super(Gene.class);
    }

    public List<Gene> buscarLikeNome(String nome) {
        try {
            Query query = manager.createQuery("SELECT g FROM Gene g WHERE g.nome like :nome");
            query.setParameter("nome", nome);
            List<Gene> genes = query.getResultList();
            return genes;
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
            return null;
        }
    }

    public Gene buscarNome(String nome) {
        try {
            Query query = manager.createQuery("SELECT g FROM Gene g WHERE g.nome = :nome");
            query.setParameter("nome", nome);
            return (Gene) query.getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
            return null;
        }
    }

    public Gene buscarSimbolo(String simbolo) {
        try {
            Query query = manager.createQuery("SELECT g FROM Gene g WHERE g.simbolo = :simbolo");
            query.setParameter("simbolo", simbolo);
            //return (Gene) query.getSingleResult();
            //Gene gene = (Gene) query.getSingleResult();
            List<Gene> genes = query.getResultList();
            Gene gene = null;
            if (genes.size() > 0) {
                gene = genes.get(0);
            }
            System.out.println("Buscando pelo simbolo: " + simbolo);
            System.out.println("Retornando: " + gene);
            return gene;
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
            return null;
        }
    }

    public List<Gene> buscarLikeSimbolo(String simbolo) {
        List<Gene> genes = null;
        try {
            Query query = manager.createQuery("SELECT g FROM Gene g WHERE g.simbolo like :simbolo");
            query.setParameter("simbolo", simbolo);
            genes = query.getResultList();

        } catch (NoResultException ex) {
            System.out.println("Opa deu erro na busca de genes::" + ex.getMessage());
        }
        return genes;
    }

    public List<Gene> buscarAnalise(Long analiseId) {
        List<Gene> genes = null;
        try {
            Query query = manager.createQuery("SELECT DISTINCT v.gene from Variante v WHERE v.vcf.id in (SELECT a.vcf.id FROM Analise a WHERE a.id = :analiseId)");
            query.setParameter("analiseId", analiseId);
            genes = query.getResultList();

        } catch (NoResultException ex) {
            System.out.println("Opa deu erro na busca de genes::" + ex.getMessage());
        }
        return genes;
    }



    
}
