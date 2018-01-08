package org.analiseGenoma.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
    
    public List<T> findByExample(T obj) throws Exception {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(classe);
        Root<T> root = criteriaQuery.from(classe);     
        List<Predicate> condicoes = new ArrayList<Predicate>();
        
        for (Field atributo : classe.getDeclaredFields()) {
            if(atributo.getType().equals(Long.class)){
                atributo.setAccessible(true);
                Long value = (Long) atributo.get(obj);
                if(!(null == value )){
                    Path<Long> atribf = root.get(atributo.getName());
                    Predicate where = criteriaBuilder.equal(atribf, value);
                    condicoes.add(where);
                }
            }
            else if(atributo.getType().equals(String.class)){
                atributo.setAccessible(true);
                String value = (String) atributo.get(obj);
                if(! (null == value )  || "".equals(value) ){
                    Path<String> atribf = root.get(atributo.getName());
                    Predicate where = criteriaBuilder.like(atribf, value);
                    condicoes.add(where);
                }
            }
            
            else if(atributo.getType().equals(Date.class)){
                atributo.setAccessible(true);
                Date value = (Date) atributo.get(obj);
                if(!(null == value )){
                    Path<String> atribf = root.get(atributo.getName());
                    Predicate where = criteriaBuilder.equal(atribf, value);
                    condicoes.add(where);
                }
            }

            else if(atributo.getType().equals(Integer.class)){
                atributo.setAccessible(true);
                Integer value = (Integer) atributo.get(obj);
                if(!(null == value )){
                    Path<Integer> atribf = root.get(atributo.getName());
                    Predicate where = criteriaBuilder.equal(atribf, value);
                    condicoes.add(where);
                }
            }
            
            else if( (atributo.getType().equals(Boolean.class)) || (atributo.getType().equals(boolean.class))  ){
                atributo.setAccessible(true);
                Boolean value = (Boolean) atributo.get(obj);
                if(!(null == value )){
                    Path<Boolean> atribf = root.get(atributo.getName());
                    Predicate where = criteriaBuilder.equal(atribf, value);
                    condicoes.add(where);
                }
            }
        }
        
        Predicate[] condicoesArray = condicoes.toArray(new Predicate[condicoes.size()]);
        Predicate todasCondicoes = criteriaBuilder.and(condicoesArray);
        criteriaQuery.where(todasCondicoes);
        TypedQuery<T> query = manager.createQuery(criteriaQuery);
        return query.getResultList();
    }
    
    
}
