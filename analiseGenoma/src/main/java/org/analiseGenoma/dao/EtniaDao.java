package org.analiseGenoma.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.TypedQuery;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.analiseGenoma.model.Etnia;

public class EtniaDao extends DAO<Etnia> {

    public EtniaDao() {
        super(Etnia.class);
    }

    public void adicionar(List<Etnia> list) {
        list.forEach((e) -> {
            manager.persist(e);
        });
    }

    public List<Etnia> findByExample(Etnia etnia) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Etnia> criteriaQuery = criteriaBuilder.createQuery(Etnia.class);
        Root<Etnia> root = criteriaQuery.from(Etnia.class);
        List<Predicate> condicoes = new ArrayList<Predicate>();
        if(!(null == etnia.getId())){
            Path<Long> atributoId = root.get("id");
            Predicate whereId = criteriaBuilder.equal(atributoId, etnia.getId());
            condicoes.add(whereId);
        }
        //if(etnia.getSigla() != null){
        if(!(null == etnia.getSigla() || "".equals(etnia.getSigla()))){
            Path<String> atributoSigla = root.get("sigla");
            Predicate whereSigla = criteriaBuilder.like(atributoSigla, etnia.getSigla());
            condicoes.add(whereSigla);
        }
        //if(etnia.getNome()!= null){
        if(!(null == etnia.getNome()|| "".equals(etnia.getNome()))){
            Path<String> atributoNome = root.get("nome");
            Predicate whereNome = criteriaBuilder.like(atributoNome, etnia.getNome());
            condicoes.add(whereNome);
        }
         if(!(null == etnia.getOrigem()|| "".equals(etnia.getOrigem()))){
            Path<String> atributoOrigem = root.get("origem");
            Predicate whereOrigem = criteriaBuilder.like(atributoOrigem, etnia.getOrigem());
            condicoes.add(whereOrigem);
        }                
        Predicate[] condicoesArray = condicoes.toArray(new Predicate[condicoes.size()]);
        Predicate todasCondicoes = criteriaBuilder.and(condicoesArray);
        criteriaQuery.where(todasCondicoes);
        TypedQuery<Etnia> query = manager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
