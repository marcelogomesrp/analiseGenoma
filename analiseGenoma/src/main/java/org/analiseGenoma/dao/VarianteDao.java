package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.Filtro;
import org.analiseGenoma.model.Variante;

public class VarianteDao extends DAO<Variante> {

    public VarianteDao() {
        super(Variante.class);
    }
    
    public List<Variante> findByVCF(Long vcfId){
        List<Variante> variantes = null;
        try {
            Query query = manager.createQuery("SELECT v FROM Variante v WHERE v.vcf.id like :vcfId");
            query.setParameter("vcfId", vcfId);
            variantes = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return variantes;
    }
    
    public List<Variante> buscarAnalise(Long vcfId){
        List<Variante> variantes = null;
        try {
            Query query = manager.createQuery("SELECT v FROM Variante v WHERE v.vcf.id like :vcfId");
            query.setParameter("vcfId", vcfId);
            variantes = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return variantes;
    }
/*
    public List<Gene> buscarNome(String nome) {
        try {
            Query query = manager.createQuery("SELECT g FROM Gene g WHERE g.nome like :nome");
            query.setParameter("nome", nome);
            List<Gene> genes = query.getResultList();
            return genes;
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
            return null;
        }
    }*/
    
    
    

    public List<Variante> buscarAnalise(Long vcfId, Filtro filtro) {
        List<Variante> variantes = null;
        
        try {
            //Query query = manager.createQuery("SELECT v FROM Variante v WHERE v.vcf.id like :vcfId and v.gene in (:genes)");
            Query query = manager.createQuery("SELECT v FROM Variante v WHERE v.vcf.id like :vcfId and v.gene in (:genes) and v.cromossomo in (:cromossomos) and v.qualidade >= :qualiMin and v.qualidade <= :qualiMax and v.impacto in (:impactos)");            
            query.setParameter("vcfId", vcfId);
            query.setParameter("genes", filtro.getGenes());
            query.setParameter("cromossomos", filtro.getCromossomos());
            query.setParameter("qualiMin", filtro.getQualidadeMin());
            query.setParameter("qualiMax", filtro.getQualidadeMax());
            query.setParameter("impactos", filtro.getImpacto());
            variantes = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return variantes;
    }
}

/*

    public List<Variante> buscarAnalise(Long vcfId, Filtro filtro) {
        List<Variante> variantes = null;
        
        try {
            //Query query = manager.createQuery("SELECT v FROM Variante v WHERE v.vcf.id like :vcfId and v.gene in (:genes)");
            Query query = manager.createQuery("SELECT v FROM Variante v WHERE v.vcf.id like :vcfId and v.gene in (:genes) and v.cromossomo in (:cromossomos) and v.qualidade >= :qualiMin and v.qualidade <= :qualiMax");            
            query.setParameter("vcfId", vcfId);
            query.setParameter("genes", filtro.getGenes());
            query.setParameter("cromossomos", filtro.getCromossomos());
            query.setParameter("qualiMin", filtro.getQualidadeMin());
            query.setParameter("qualiMax", filtro.getQualidadeMax());
            variantes = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return variantes;
    }
*/





/*
    public List<Variante> buscarAnalise(Long vcfId, Filtro filtro) {
        List<Variante> variantes = null;
        
        try {
            //Query query = manager.createQuery("SELECT v FROM Variante v WHERE v.vcf.id like :vcfId and v.gene in (:genes)");
            Query query = manager.createQuery("SELECT v FROM Variante v WHERE v.vcf.id like :vcfId and v.gene in (:genes) and v.cromossomo in (:cromossomos)");            
            query.setParameter("vcfId", vcfId);
            query.setParameter("genes", filtro.getGenes());
            query.setParameter("cromossomos", filtro.getCromossomos());
            variantes = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return variantes;
    }
*/



/*public List<Variante> buscarAnalise(Long vcfId, Filtro filtro) {
        List<Variante> variantes = null;
        
        try {
           // String squery = "SELECT v FROM Variante v WHERE v.vcf.id like :vcfId and v.";
            //Query query = manager.createQuery(squery);
            //Query query = manager.createQuery("SELECT v FROM Variante v WHERE v.vcf.id like :vcfId and v.gene in (:genes) and v.cromossomo in (:cromossomos)");
            String squery = "SELECT v FROM Variante v WHERE v.vcf.id like :vcfId and v.gene in (:genes) ";
            //Query query = manager.createQuery("SELECT v FROM Variante v WHERE v.vcf.id like :vcfId and v.gene in (:genes)");
            
            if(filtro.getCromossomos() != null){
                squery = squery + " and v.cromossomo in (:cromossomos)";
            }
            Query query = manager.createQuery("SELECT v FROM Variante v WHERE v.vcf.id like :vcfId and v.gene in (:genes)");
            query.setParameter("vcfId", vcfId);
            query.setParameter("genes", filtro.getGenes());
            
            if(filtro.getCromossomos() != null){
                query.setParameter("cromossomos", filtro.getCromossomos());
            }
            
            variantes = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return variantes;
    }*/