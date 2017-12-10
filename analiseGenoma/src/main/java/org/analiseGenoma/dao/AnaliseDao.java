package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.Query;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.Cromossomo;
import org.analiseGenoma.model.Impact;

public class AnaliseDao extends DAO<Analise> {

    public AnaliseDao() {
        super(Analise.class);
    }

    /*
    public List<Analise> buscarNome(String nome) {
        try {
            Query query = manager.createQuery("SELECT p FROM Paciente p WHERE p.nome like :nome");
            query.setParameter("nome", nome);
            List<Paciente> pacientes = query.getResultList();
            return pacientes;
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
            return null;
        }
    }
     */
    public Double buscarQualidadeMax(Long analiseId) {
        //Query query = manager.createQuery("SELECT DISTINCT v.gene from Variante v WHERE v.vcf.id in (SELECT a.vcf.id FROM Analise a WHERE a.id = :analiseId)");
        Query query = manager.createQuery("SELECT MAX(v.qualidade) from Variante v WHERE v.vcf.id in (SELECT a.vcf.id FROM Analise a WHERE a.id = :analiseId)");
        query.setParameter("analiseId", analiseId);
        Double max = (Double) query.getSingleResult();
        return max;
    }
    
    public Double buscarQualidadeMin(Long analiseId) {
        Query query = manager.createQuery("SELECT MIN(v.qualidade) from Variante v WHERE v.vcf.id in (SELECT a.vcf.id FROM Analise a WHERE a.id = :analiseId)");
        query.setParameter("analiseId", analiseId);
        Double min = (Double) query.getSingleResult();
        return min;
    }
    

    public List<Cromossomo> buscarCromossomos(Long analiseId) {
        Query query = manager.createQuery("SELECT DISTINCT v.cromossomo from Variante v WHERE v.vcf.id in (SELECT a.vcf.id FROM Analise a WHERE a.id = :analiseId)");
        query.setParameter("analiseId", analiseId);
        return query.getResultList();
    }
    
    public List<Impact> buscarImpactos(Long analiseId) {
        Query query = manager.createQuery("SELECT DISTINCT v.impacto from Variante v WHERE v.vcf.id in (SELECT a.vcf.id FROM Analise a WHERE a.id = :analiseId)");
        query.setParameter("analiseId", analiseId);
        return query.getResultList();
    }
    
}
