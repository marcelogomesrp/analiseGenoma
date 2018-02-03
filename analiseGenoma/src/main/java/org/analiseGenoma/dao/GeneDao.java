package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.GeneSymbolSynonym;

public class GeneDao extends DAO<Gene> {

    public GeneDao() {
        super(Gene.class);
    }
    
//    @Override
//    public List<Gene> find(){
//        //List<Gene> genes =  manager.createQuery("SELECT g FROM Gene g  LEFT JOIN FETCH g.listDbBio LEFT JOIN FETCH g.geneSymbolSynonym LEFT JOIN FETCH g.geneNameSynonym").getResultList();
//        //List<Gene> genes =  manager.createQuery("SELECT distinct g FROM Gene g  JOIN FETCH g.listDbBio JOIN FETCH g.geneSymbolSynonym JOIN FETCH g.geneNameSynonym").getResultList();
//        List<Gene> genes =  manager.createQuery("SELECT distinct g FROM Gene g  LEFT JOIN FETCH g.listDbBio LEFT JOIN FETCH g.geneSymbolSynonym").getResultList();
//        return genes;
//        //geneNameSynonym
//    }
    
    
//    public List<Gene> find2() {
//        List<Gene> genes =  manager.createQuery("SELECT g FROM Gene g LEFT JOIN FETCH g.dbbio").getResultList();
//        return genes;
//    }

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

    public Gene buscarSimbolo(String symbol) {
        try {
            Query query = manager.createQuery("SELECT g FROM Gene g WHERE g.symbol = :symbol");
            query.setParameter("symbol", symbol);
            //return (Gene) query.getSingleResult();
            //Gene gene = (Gene) query.getSingleResult();
            List<Gene> genes = query.getResultList();
            Gene gene = null;
            if (genes.size() > 0) {
                gene = genes.get(0);
            }
            System.out.println("Buscando pelo simbolo: " + symbol);
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

//    @Transactional
//    @Override
//    public void persist(Gene g) {        
//        manager.persist(g);
//    }


    
    
    public Gene findBySymbol(String symbol) {
        Query q = manager.createQuery("SELECT g.gene from GeneSymbolSynonym g JOIN FETCH g.gene.geneSymbolSynonym WHERE g.symbol = :symbol ", Gene.class);
        q.setParameter("symbol", symbol);
        List<Gene> genes = q.getResultList();
        if(!genes.isEmpty()){
            return genes.get(0);
        }
        return null;
                
    }

    public List<Gene> findBySymbol(List<String> symbolList) {
        Query q = manager.createQuery("SELECT DISTINCT g.gene from GeneSymbolSynonym g JOIN FETCH g.gene.geneSymbolSynonym WHERE g.symbol in :symbols ", Gene.class);
        q.setParameter("symbols", symbolList);
        List<Gene> genes = q.getResultList();
        if(!genes.isEmpty()){
            return genes;
        }
        return null;
    }





    
}
