package org.analiseGenoma.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.analiseGenoma.model.Patient;

public class PatientDao extends DAO<Patient> {

    public PatientDao() {
        super(Patient.class);
    }

    public List<Patient> buscarNome(String nome) {
        try {
            //Query query = manager.createQuery("SELECT p FROM Paciente p WHERE p.nome like :nome");
            Query query = manager.createQuery("SELECT p FROM Patient p WHERE p.name like :nome");
            query.setParameter("nome", nome);
            List<Patient> pacientes = query.getResultList();
            return pacientes;
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
            return null;
        }
    }
    
    
    public List<Patient> findByExample(Patient p) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Patient> criteriaQuery = criteriaBuilder.createQuery(Patient.class);
        Root<Patient> root = criteriaQuery.from(Patient.class);
        List<Predicate> condicoes = new ArrayList<>();
        if(!(null == p.getId())){
            Path<Long> atributoId = root.get("id");
            Predicate whereId = criteriaBuilder.equal(atributoId, p.getId());
            condicoes.add(whereId);
        }        
        if(!(null == p.getName() || "".equals(p.getName()))){
            Path<String> atributoSigla = root.get("name");
            Predicate where = criteriaBuilder.like(atributoSigla, p.getName());
            condicoes.add(where);
        }
        if(!(null == p.getGender() || "".equals(p.getGender()))){
            Path<String> atributoSigla = root.get("gender");
            Predicate where = criteriaBuilder.equal(atributoSigla, p.getGender());
            condicoes.add(where);
        }
        if(! (null == p.getPopulation()) ){
            Path<String> atributo = root.get("etnia");
            Predicate where = criteriaBuilder.equal(atributo, p.getPopulation());
            condicoes.add(where);
        }
        
        if(!(p.getBirth() == null)){
            Path<Date> atributo = root.get("birth");
            Predicate where = criteriaBuilder.equal(atributo, p.getBirth());
            condicoes.add(where);
        }
        if(!(null == p.getSecondId() || "".equals(p.getSecondId() ))){
            Path<String> atributo = root.get("secondId");
            Predicate where = criteriaBuilder.equal(atributo, p.getSecondId());
            condicoes.add(where);
        }        
        
        Predicate[] condicoesArray = condicoes.toArray(new Predicate[condicoes.size()]);
        Predicate todasCondicoes = criteriaBuilder.and(condicoesArray);
        criteriaQuery.where(todasCondicoes);
        TypedQuery<Patient> query = manager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    
}
