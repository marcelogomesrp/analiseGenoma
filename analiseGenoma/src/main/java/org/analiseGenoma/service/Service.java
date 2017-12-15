package org.analiseGenoma.service;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.DAO;
import org.analiseGenoma.model.ClinvarDisease;

public abstract class Service<T> implements Serializable {

    protected Class<T> classe;    
    @Inject
    protected DAO<T> dao;

    public Service(Class<T> classe) {
        this.classe = classe;
    }
    
    @PostConstruct
    public void init() {
    }

    @Transactional
    public void adicionar(T obj) {
        dao.adicionar(obj);
    }

    @Transactional
    public void atualizar(T obj) {
        dao.atualizar(obj);
    }

    public List<T> buscar() {
        return dao.buscar();
    }

    public T buscarPorId(Long id) {
        return dao.buscarPorId(id);
    }
    
    /*
    
    public List<T> findByName(String name){
        return dao.findByName(name);
    }
        @Transactional
    public T findOrCreate(String name) {
        List<T> list = this.findByName(name);
        if (list.size() == 1) {
            return list.get(0);
        }
        Class c = null;
        T obj = (T)c.newInstance();
        obj.setName(name);
        this.adicionar(obj);
        return obj;
    }
 */
}
