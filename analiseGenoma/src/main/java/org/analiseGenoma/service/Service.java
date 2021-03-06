package org.analiseGenoma.service;

import java.io.InputStream;
import java.io.Serializable;
import java.io.Writer;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.DAO;

public abstract class Service<T> implements Serializable {

    protected Class<T> classe;
    @Inject
    protected DAO<T> dao;

    private DAO<T> getDao() {
        return dao;
    }

    public Service(Class<T> classe) {
        this.classe = classe;
    }

    @PostConstruct
    public void init() {
    }

    @Transactional
    public void persiste(T obj) {
        dao.persist(obj);
    }

    @Transactional
    public void persiste(List<T> list) {
        list.forEach((o) -> {
            persiste(o);
        });
    }

    @Transactional
    public void merge(T obj) {
        dao.merge(obj);
    }

    public List<T> find() {
        //return dao.find();
        return getDao().find();
    }

    public T findById(Long id) {
        return getDao().findById(id);
    }

    public List<T> findByExample(T obj) throws Exception {
        return dao.findByExample(obj);
    }

    public T getFirstOrNull(List<T> list) {
        if (list != null) {
            if (!(list.isEmpty())) {
                return list.get(0);
            }
        }
        return null;
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
    public T XmlToObject(InputStream inputStream) throws Exception {
        return getDao().XmlToObject(inputStream);

    }

    public Writer ObjectToXML(T obj) throws Exception {
        return getDao().ObjectToXML(obj);
    }
}
