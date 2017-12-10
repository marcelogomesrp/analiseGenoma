package org.analiseGenoma.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}
