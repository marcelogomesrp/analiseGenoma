package org.analiseGenoma.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.analiseGenoma.model.Paciente;

public class DAO<T> implements Serializable {

    private final Class<T> classe;
    @PersistenceContext
    protected EntityManager manager;

    public DAO(Class<T> classe) {
        this.classe = classe;
    }

    public void adicionar(T obj) {
        manager.persist(obj);
    }

    public void remover(T obj) {
        manager.remove(obj);
    }

    public void atualizar(T obj) {
        manager.merge(obj);
    }
    
    public List<T> buscar(){
        return manager.createQuery("Select t from " + classe.getSimpleName() + " t").getResultList();    
    }

    /*
    public List listaTodos() {
        CriteriaQuery query = manager.getCriteriaBuilder().createQuery(classe);
        query.from(classe);
        return manager.createQuery(query).getResultList();
    }
*/

    public T buscarPorId(Long id) {
        return manager.find(classe, id);
    }
    
    protected List<T> findByExample(T obj,List<Predicate> condicoes  ) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(classe);
        Root<T> root = criteriaQuery.from(classe);        
        /*
        if(!(null == p.getId())){
            Path<Long> atributoId = root.get("id");
            Predicate whereId = criteriaBuilder.equal(atributoId, p.getId());
            condicoes.add(whereId);
        }        
        if(!(null == p.getNome() || "".equals(p.getNome()))){
            Path<String> atributoSigla = root.get("nome");
            Predicate where = criteriaBuilder.like(atributoSigla, p.getNome());
            condicoes.add(where);
        }
        if(!(null == p.getGender() || "".equals(p.getGender()))){
            Path<String> atributoSigla = root.get("gender");
            Predicate where = criteriaBuilder.equal(atributoSigla, p.getGender());
            condicoes.add(where);
        }
        */
        Predicate[] condicoesArray = condicoes.toArray(new Predicate[condicoes.size()]);
        Predicate todasCondicoes = criteriaBuilder.and(condicoesArray);
        criteriaQuery.where(todasCondicoes);
        TypedQuery<T> query = manager.createQuery(criteriaQuery);
        return query.getResultList();
    }
    
    
}
