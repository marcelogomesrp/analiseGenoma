package org.analiseGenoma.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.Disease;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.GeneDbBio;

public class GeneDao extends DAO<Gene> {

    public GeneDao() {
        super(Gene.class);
    }

    @Override
    public List<Gene> find() {
        try{
        List<Gene> genes =  manager.createQuery("SELECT distinct g FROM Gene g LEFT JOIN FETCH g.geneSymbolSynonym ORDER BY g.symbol").getResultList();   
        return genes;
        }catch(Exception ex){
            System.out.println("Erro: GeneDao.find: " + ex.getMessage());
        }
        return null;
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
    
    
    public List<Gene> find(Disease disease){
        List<Gene> genes = null;
        try{
            Query query = manager.createQuery("SELECT DISTINCT db.genes from DbBioInfo db WHERE db.disease = :disease");
            query.setParameter("disease", disease);
            genes = query.getResultList();
        }catch(Exception ex){
            System.out.println("Erro na busca de gene por disease");;
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

    public GeneDbBio findByDbIdGeneId(String bdId, Long geneId) {
        List<GeneDbBio> genes = null;
        try {
//            Query query = manager.createQuery("SELECT g FROM GeneDbBio g WHERE g.dbBio.id = :dbId AND g.gene.id = :geneId");
//            query.setParameter("dbId", bdId);
//            query.setParameter("geneId", geneId);
                Query query = manager.createQuery("SELECT g FROM GeneDbBio g WHERE g.dbBio.id = :dbId  AND g.gene.id = :geneId");
            query.setParameter("dbId", Long.valueOf(bdId));
            query.setParameter("geneId", geneId);
            genes = query.getResultList();
            if(genes.size() > 0){
                return genes.get(0);
            }
        } catch (NoResultException ex) {
            System.out.println("Opa deu erro na busca de genes::" + ex.getMessage());
        }
        return null;
    }

    
    

    
}
