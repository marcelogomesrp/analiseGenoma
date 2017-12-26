package org.analiseGenoma.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.Cromossomo;
import org.analiseGenoma.model.Filtro;
import org.analiseGenoma.model.Gene;

public class FiltroDao extends DAO<Filtro> {
    
    @Inject private CromossomoDao cromossomoDao;

    public FiltroDao() {
        super(Filtro.class);
    }
    //JOIN FETCH o.items i WHERE o.id = :id");

    public Filtro buscarPorAnalise(Long idAnalise) {
        try {
            //Query query = manager.createQuery("SELECT f FROM Filtro f JOIN FETCH f.genes WHERE f.analise.id = :idAnalise ");
            //Query query = manager.createQuery("SELECT f FROM Filtro f WHERE f.analise.id = :idAnalise ");
            Query query = manager.createQuery("SELECT f FROM Filtro f JOIN FETCH f.umdPredictors WHERE f.analise.id = :idAnalise");
            query.setParameter("idAnalise", idAnalise);
            Filtro filtro =  (Filtro) query.getSingleResult();
            //filtro.getCromossomos().size();
            return filtro;
        } catch (NoResultException ex) {
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
    


}