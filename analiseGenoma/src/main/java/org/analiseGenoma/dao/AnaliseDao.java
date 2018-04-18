package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.Cromossomo;
import org.analiseGenoma.model.Impact;

public class AnaliseDao extends DAO<Analise> {

    public AnaliseDao() {
        super(Analise.class);
    }
    
//    @Override
//    public Analise buscarPorId(Long idAnalise) {
//        try {
//            //Query query = manager.createQuery("SELECT a FROM Analise a JOIN FETCH a.revisores WHERE a.id = :idAnalise");
//            //Query query = manager.createQuery("SELECT a FROM Analise a WHERE a.id = :idAnalise");
//            Query query = manager.createQuery("SELECT a FROM Analise a INNER JOIN a.revisores  WHERE a.id = :idAnalise");
//            query.setParameter("idAnalise", idAnalise);
//            Analise analise = (Analise) query.getSingleResult();
//            return analise;
//        } catch (NoResultException ex) {
//            System.out.println("Erro:: " + ex.getMessage());
//            return null;
//        }
//    }

//    @Override
//    public Analise buscarPorId(Long id) {
//        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
//        CriteriaQuery<Analise> criteriaQuery = criteriaBuilder.createQuery(Analise.class);
//        Root<Analise> root = criteriaQuery.from(Analise.class);       
//        root.fetch("revisores");
//        //root.fetch("revisores",JoinType.INNER);
//        
//        List<Predicate> condicoes = new ArrayList<Predicate>();
//        if (!(null == id)) {
//            Path<Long> atributoId = root.get("id");
//            Predicate whereId = criteriaBuilder.equal(atributoId, id);
//            condicoes.add(whereId);
//        }
//        
//        
//        
//        //Join<Analise, Usuario> join = root.join("revisores");        
//        //root.fetch("revisores", JoinType.INNER);
//        //criteriaQuery.select(root);
//        
//        Predicate[] condicoesArray = condicoes.toArray(new Predicate[condicoes.size()]);
//        Predicate todasCondicoes = criteriaBuilder.and(condicoesArray);
//        criteriaQuery.where(todasCondicoes);
//        TypedQuery<Analise> query = manager.createQuery(criteriaQuery);
//        return query.getSingleResult();
//    }

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

    public List<Analise> findDesc() {
        return manager.createQuery("Select t from Analise t ORDER BY t.id DESC").getResultList();
    }

}
