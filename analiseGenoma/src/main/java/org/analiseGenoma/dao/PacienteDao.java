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
import org.analiseGenoma.model.Paciente;

public class PacienteDao extends DAO<Paciente> {

    public PacienteDao() {
        super(Paciente.class);
    }

    public List<Paciente> buscarNome(String nome) {
        try {
            Query query = manager.createQuery("SELECT p FROM Paciente p WHERE p.nome like :nome");
            query.setParameter("nome", nome);
            List<Paciente> pacientes = query.getResultList();
            return pacientes;
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
            return null;
        }
    }
    
    
    public List<Paciente> findByExample(Paciente p) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Paciente> criteriaQuery = criteriaBuilder.createQuery(Paciente.class);
        Root<Paciente> root = criteriaQuery.from(Paciente.class);
        List<Predicate> condicoes = new ArrayList<>();
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
        if(! (null == p.getEtnia()) ){
            Path<String> atributo = root.get("etnia");
            Predicate where = criteriaBuilder.equal(atributo, p.getEtnia());
            condicoes.add(where);
        }
        
        if(!(p.getDataNascimento() == null)){
            Path<Date> atributo = root.get("dataNascimento");
            Predicate where = criteriaBuilder.equal(atributo, p.getDataNascimento());
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
        TypedQuery<Paciente> query = manager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    
}
