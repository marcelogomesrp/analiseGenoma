package org.analiseGenoma.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Query;
import org.analiseGenoma.model.Age;

public class AgeDao extends DAO<Age> implements Serializable{

    public AgeDao() {
        super(Age.class);
    }
    
    public List<Age> findByAge(int age){
        Query query = manager.createQuery("SELECT a FROM Age a WHERE :age BETWEEN a.beginning AND a.finish", Age.class);
        query.setParameter("age", age);
        return query.getResultList();
    }
    
    
}
