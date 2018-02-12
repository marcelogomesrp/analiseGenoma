package org.analiseGenoma.dao;

import java.io.InputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class DAO<T> implements Serializable {

    public enum MatchMode {
        START, END, EXACT, ANYWHERE
    }

    private final Class<T> classe;
    @PersistenceContext
    protected EntityManager manager;

    public DAO(Class<T> classe) {
        this.classe = classe;
    }

    public void persist(T obj) {
        manager.persist(obj);
    }

    public void remove(T obj) {
        manager.remove(obj);
    }

    public void merge(T obj) {
        manager.merge(obj);
    }

    public List<T> find() {
        return manager.createQuery("Select t from " + classe.getSimpleName() + " t").getResultList();
    }

    /*
    public List listaTodos() {
        CriteriaQuery query = manager.getCriteriaBuilder().createQuery(classe);
        query.from(classe);
        return manager.createQuery(query).getResultList();
    }
     */
    public T findById(Long id) {
        return manager.find(classe, id);
    }

    public List<T> findByExample(T obj) throws Exception {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(classe);
        Root<T> root = criteriaQuery.from(classe);
        List<Predicate> condicoes = new ArrayList<Predicate>();

        for (Field atributo : classe.getDeclaredFields()) {
            if (atributo.getType().equals(Long.class)) {
                atributo.setAccessible(true);
                Long value = (Long) atributo.get(obj);
                if (!(null == value)) {
                    Path<Long> atribf = root.get(atributo.getName());
                    Predicate where = criteriaBuilder.equal(atribf, value);
                    condicoes.add(where);
                }
            } else if (atributo.getType().equals(String.class)) {
                atributo.setAccessible(true);
                String value = (String) atributo.get(obj);
                if (!(null == value) || "".equals(value)) {
                    Path<String> atribf = root.get(atributo.getName());
                    Predicate where = criteriaBuilder.like(atribf, value);
                    condicoes.add(where);
                }
            } else if (atributo.getType().equals(Date.class)) {
                atributo.setAccessible(true);
                Date value = (Date) atributo.get(obj);
                if (!(null == value)) {
                    Path<String> atribf = root.get(atributo.getName());
                    Predicate where = criteriaBuilder.equal(atribf, value);
                    condicoes.add(where);
                }
            } else if (atributo.getType().equals(Integer.class)) {
                atributo.setAccessible(true);
                Integer value = (Integer) atributo.get(obj);
                if (!(null == value)) {
                    Path<Integer> atribf = root.get(atributo.getName());
                    Predicate where = criteriaBuilder.equal(atribf, value);
                    condicoes.add(where);
                }
            } else if ((atributo.getType().equals(Boolean.class)) || (atributo.getType().equals(boolean.class))) {
                atributo.setAccessible(true);
                Boolean value = (Boolean) atributo.get(obj);
                if (!(null == value)) {
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

    public List<T> findByProperty(String propertyName, Object value) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(classe);
        Root<T> root = criteriaQuery.from(classe);
        criteriaQuery.where(criteriaBuilder.equal(root.get(propertyName), value));
        return manager.createQuery(criteriaQuery).getResultList();
    }

    /**
     * Finds entities by a String property specifying a MatchMode. This search
     * is case insensitive.
     *
     * @param clazz the entity class.
     * @param propertyName the property name.
     * @param value the value to check against.
     * @param matchMode the match mode: EXACT, START, END, ANYWHERE.
     * @return
     */
    public List<T> findByProperty(String propertyName, String value, MatchMode matchMode) {
        //convert the value String to lowercase
        value = value.toUpperCase();
        if (MatchMode.START.equals(matchMode)) {
            value = value + "%";
        } else if (MatchMode.END.equals(matchMode)) {
            value = "%" + value;
        } else if (MatchMode.ANYWHERE.equals(matchMode)) {
            value = "%" + value + "%";
        }

        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(classe);
        Root<T> root = criteriaQuery.from(classe);
        criteriaQuery.where(criteriaBuilder.like(criteriaBuilder.upper(root.get(propertyName)), value));
        return manager.createQuery(criteriaQuery).getResultList();
    }
    
    
    
    public T XmlToObject(InputStream inputStream) throws Exception{
        JAXBContext jaxbContext = JAXBContext.newInstance(classe);        
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();        
        return (T) unmarshaller.unmarshal(inputStream);
    }
    
   
    public Writer ObjectToXML(T obj) throws Exception{
        JAXBContext jaxbContext = JAXBContext.newInstance(classe);
        Marshaller marshaller = jaxbContext.createMarshaller();
        StringWriter writer = new StringWriter();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(obj, writer);
        return writer;
    }
    
    
        public T getFirstOrNull(List<T> list) {
        if (list != null) {
            if (!(list.isEmpty())) {
                return list.get(0);
            }
        }
        return null;
    }

}

//https://rodrigouchoa.wordpress.com/2014/09/26/exemplo-dao-generico/