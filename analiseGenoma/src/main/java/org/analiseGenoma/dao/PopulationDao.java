package org.analiseGenoma.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import org.analiseGenoma.model.Population;
import javax.persistence.criteria.Predicate;

public class PopulationDao extends DAO<Population> {

    public PopulationDao() {
        super(Population.class);
    }

    public void persiste(List<Population> list) {
        list.forEach((e) -> {
            manager.persist(e);
        });
    }

//    @Override
//    public List<Population> findByExample(Population etnia) {
//        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
//        CriteriaQuery<Population> criteriaQuery = criteriaBuilder.createQuery(Population.class);
//        Root<Population> root = criteriaQuery.from(Population.class);
//        List<Predicate> condicoes = new ArrayList<Predicate>();
//        if(!(null == etnia.getId())){
//            Path<Long> atributoId = root.get("id");
//            Predicate whereId = criteriaBuilder.equal(atributoId, etnia.getId());
//            condicoes.add(whereId);
//        }
//        //if(etnia.getSigla() != null){
//        if(!(null == etnia.getSigla() || "".equals(etnia.getSigla()))){
//            Path<String> atributoSigla = root.get("sigla");
//            Predicate whereSigla = criteriaBuilder.like(atributoSigla, etnia.getSigla());
//            condicoes.add(whereSigla);
//        }
//        //if(etnia.getNome()!= null){
//        if(!(null == etnia.getNome()|| "".equals(etnia.getNome()))){
//            Path<String> atributoNome = root.get("nome");
//            Predicate whereNome = criteriaBuilder.like(atributoNome, etnia.getNome());
//            condicoes.add(whereNome);
//        }
//         if(!(null == etnia.getOrigem()|| "".equals(etnia.getOrigem()))){
//            Path<String> atributoOrigem = root.get("origem");
//            Predicate whereOrigem = criteriaBuilder.like(atributoOrigem, etnia.getOrigem());
//            condicoes.add(whereOrigem);
//        }                
//        Predicate[] condicoesArray = condicoes.toArray(new Predicate[condicoes.size()]);
//        Predicate todasCondicoes = criteriaBuilder.and(condicoesArray);
//        criteriaQuery.where(todasCondicoes);
//        TypedQuery<Population> query = manager.createQuery(criteriaQuery);
//        return query.getResultList();
//    }
    public Population findByCode(String code) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Population> criteriaQuery = criteriaBuilder.createQuery(Population.class);
        Root<Population> root = criteriaQuery.from(Population.class);
        Path<String> attribute = root.get("code");
        criteriaQuery.where(criteriaBuilder.equal(attribute, code));
        TypedQuery<Population> query = manager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }

//    public List<Population> findTesteUsa(String code) {
//        List<Predicate> condicoes = new ArrayList<Predicate>();
//        Root<Population> root = criteriaQuery.from(Population.class);
//        Path<String> atribf = root.get("code");
//        Predicate where = criteriaBuilder.like(atribf, value);
//        condicoes.add(where);
//    }
//
//    public List<Population> findTeste(List<Predicate> condicoes) {
//        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
//        CriteriaQuery<Population> criteriaQuery = criteriaBuilder.createQuery(Population.class);
//        Root<Population> root = criteriaQuery.from(Population.class);
//
//        Predicate[] condicoesArray = condicoes.toArray(new javax.persistence.criteria.Predicate[condicoes.size()]);
//        javax.persistence.criteria.Predicate todasCondicoes = criteriaBuilder.and(condicoesArray);
//        criteriaQuery.where(todasCondicoes);
//        TypedQuery<Population> query = manager.createQuery(criteriaQuery);
//        return query.getResultList();
//    }

}
