package org.analiseGenoma.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import org.analiseGenoma.model.Cromossomo;
import org.analiseGenoma.model.Effect;
import org.analiseGenoma.model.Filtro;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.UmdPredictor;

public class FiltroDao extends DAO<Filtro> {
    
    @Inject private CromossomoDao cromossomoDao;

    public FiltroDao() {
        super(Filtro.class);
    }

//    @Override
//    public Filtro findById(Long id) {
//        //return super.findById(id); //To change body of generated methods, choose Tools | Templates.
//         CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
//        CriteriaQuery<Filtro> criteriaQuery = criteriaBuilder.createQuery(Filtro.class);
//        Root<Filtro> root = criteriaQuery.from(Filtro.class);
//        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));
//        
//        root.fetch("cromossomos", JoinType.LEFT);
//        root.fetch("genes", JoinType.LEFT);
//        root.fetch("umdPredictors", JoinType.LEFT);
//        root.fetch("effects", JoinType.LEFT);
//        root.fetch("zygosities", JoinType.LEFT);
//        
//        //return manager.createQuery(criteriaQuery).getResultList();
//        return manager.createQuery(criteriaQuery).getSingleResult();
//    }
    
    @Override
    public Filtro findById(Long id) {
        //return super.findById(id); //To change body of generated methods, choose Tools | Templates.
         CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Filtro> criteriaQuery = criteriaBuilder.createQuery(Filtro.class);
        Root<Filtro> root = criteriaQuery.from(Filtro.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));
        
//        root.fetch("cromossomos", JoinType.LEFT);
//        root.fetch("genes", JoinType.LEFT);
//        root.fetch("umdPredictors", JoinType.LEFT);
//        root.fetch("effects", JoinType.LEFT);
//        root.fetch("zygosities", JoinType.LEFT);
        
        //return manager.createQuery(criteriaQuery).getResultList();
        Filtro retorno = manager.createQuery(criteriaQuery).getSingleResult();
        
        return retorno;
    }
    
    
    
    //JOIN FETCH o.items i WHERE o.id = :id");

    public Filtro buscarPorAnalise(Long idAnalise) {
        try {
            //Query query = manager.createQuery("SELECT f FROM Filtro f JOIN FETCH f.genes WHERE f.analise.id = :idAnalise ");
            //Query query = manager.createQuery("SELECT f FROM Filtro f WHERE f.analise.id = :idAnalise ");
            //Query query = manager.createQuery("SELECT f FROM Filtro f JOIN FETCH f.umdPredictors WHERE f.analise.id = :idAnalise");
            Query query = manager.createQuery("SELECT f FROM Filtro f LEFT JOIN FETCH f.umdPredictors WHERE f.analise.id = :idAnalise");
            query.setParameter("idAnalise", idAnalise);
            
            Filtro filtro =  (Filtro) query.getSingleResult();
            //filtro.getCromossomos().size();
            return filtro;
        } catch (Exception ex) {
            System.out.println("Erro:: " + ex.getMessage());
            return null;
        }
    }

    public Set<Gene> buscarGene(Long idFiltro) {
        Query query = manager.createQuery("SELECT f.genes FROM Filtro f WHERE f.id = :idFiltro");
        query.setParameter("idFiltro", idFiltro);        
        List<Gene> genes = query.getResultList();
        return new HashSet<>(genes);
    }
    
    public Set<Effect> buscarEffect(Long idFiltro) {
        Query query = manager.createQuery("SELECT f.effects FROM Filtro f WHERE f.id = :idFiltro");
        query.setParameter("idFiltro", idFiltro);        
        List<Effect> effects = query.getResultList();
        return new HashSet<>(effects);
    }

    public Set<Cromossomo> buscarCromossomos(Long idFiltro) {
        Query query = manager.createQuery("SELECT f.cromossomos FROM Filtro f WHERE f.id = :idFiltro");
        query.setParameter("idFiltro", idFiltro);
        List<Cromossomo> list = query.getResultList();
        return new HashSet(list);
    }
    
//    @Override
//    public void adicionar(Filtro filtro) {
//        if(filtro.getCromossomos() != null){
//            for(Cromossomo f:filtro.getCromossomos()){
//                cromossomoDao.atualizar(f);
//            }
//        }
//        super.adicionar(filtro);
//    }

    public Set<Cromossomo> findCromossomoFromFiltro(Filtro filtro) {
        Query query = manager.createQuery("SELECT f.cromossomos FROM Filtro f WHERE f.id = :idFiltro");
        query.setParameter("idFiltro", filtro.getId());
        List<Cromossomo> list = query.getResultList();
        return new HashSet(list);
    }

    public Set<UmdPredictor> findUmdPerdictors(Filtro filtro) {
        Query query = manager.createQuery("SELECT f.umdPredictors FROM Filtro f WHERE f.id = :idFiltro");
        query.setParameter("idFiltro", filtro.getId());
        List<UmdPredictor> list = query.getResultList();
        return new HashSet(list);
    }
    


}