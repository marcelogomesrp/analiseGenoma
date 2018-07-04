package org.analiseGenoma.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.analiseGenoma.model.Population;
import org.analiseGenoma.model.User;
import org.analiseGenoma.model.Variante;
import org.analiseGenoma.model.VarianteRevisada;
import org.analiseGenoma.model.Vcf;

public class VarianteRevisadaDao extends DAO<VarianteRevisada> {

    public VarianteRevisadaDao() {
        super(VarianteRevisada.class);
    }

    public List<VarianteRevisada> findByName(String name) {
        List<VarianteRevisada> list = null;
        try {
            Query query = manager.createQuery("SELECT i FROM VarianteRevisada i WHERE i.name like :name");
            query.setParameter("name", name);
            list = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return list;
    }
    
    
    public List<VarianteRevisada> findByAnaliseRevisor(Vcf vcf, User revisor) {
        List<VarianteRevisada> list = null;
        try {
            Query query = manager.createQuery("SELECT vr FROM VarianteRevisada vr WHERE vr.revisor = :revisor and vr.variant.vcf = :vcf");
            query.setParameter("revisor", revisor);
            query.setParameter("vcf", vcf);
            list = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return list;
    }
    
    public List<VarianteRevisada> findByVarianteRevisor(Variante variante, User revisor) {
        List<VarianteRevisada> list = null;
        try {
            Query query = manager.createQuery("SELECT vr FROM VarianteRevisada vr WHERE vr.revisor = :revisor and vr.variant = :variante");
            query.setParameter("revisor", revisor);
            query.setParameter("variante", variante);
            list = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return list;
    }
    
    
    public List<VarianteRevisada> findByVarianteRevisor(String idRevisor, Long idVariant) {
        List<VarianteRevisada> list = null;
        try {
            Query query = manager.createQuery("SELECT vr FROM VarianteRevisada vr WHERE vr.revisor.id = :revisor and vr.variant.id = :variante");
            query.setParameter("revisor", Long.valueOf(idRevisor));
            query.setParameter("variante", idVariant);
            list = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return list;
    }
    

    @Override
    public List<VarianteRevisada> findByExample(VarianteRevisada vr) throws Exception {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<VarianteRevisada> criteriaQuery = criteriaBuilder.createQuery(VarianteRevisada.class);
        Root<Population> root = criteriaQuery.from(Population.class);
        List<Predicate> condicoes = new ArrayList<Predicate>();
        if (!(null == vr.getId())) {
            Path<Long> atributoId = root.get("id");
            Predicate whereId = criteriaBuilder.equal(atributoId, vr.getId());
            condicoes.add(whereId);
        }
        
        if (!(null == vr.getRevisor())) {
            Path<User> atributo = root.get("revisor");
            Predicate where = criteriaBuilder.equal(atributo, vr.getRevisor());
            condicoes.add(where);            
        }
        
               
        Predicate[] condicoesArray = condicoes.toArray(new Predicate[condicoes.size()]);
        Predicate todasCondicoes = criteriaBuilder.and(condicoesArray);
        criteriaQuery.where(todasCondicoes);
        TypedQuery<VarianteRevisada> query = manager.createQuery(criteriaQuery);
        return query.getResultList();
    }

}
