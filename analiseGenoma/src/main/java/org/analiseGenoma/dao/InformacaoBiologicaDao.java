package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.InformacaoBiologica;
import org.analiseGenoma.model.Patologia;

public class InformacaoBiologicaDao extends DAO<InformacaoBiologica> {

    public InformacaoBiologicaDao() {
        super(InformacaoBiologica.class);
    }
    
    public InformacaoBiologica buscarPorId(Long idBd, Long idGene) {
        try {
            Query query = manager.createQuery(
                    "SELECT i FROM InformacaoBiologica i "
                            + "WHERE i.bdBio.id  = :idBd "
                            + "and i.gene.id = :idGene"
            );
            query.setParameter("idBd", idBd);
            query.setParameter("idGene", idGene);
            List<InformacaoBiologica> list = query.getResultList();
            if(list.size() > 0){
                return list.get(0);
            }
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());            
        }
        return null;
    }

    public List<Gene> buscarGenePorPatologia(Patologia p) {
        Query query = manager.createQuery("SELECT i.gene FROM InformacaoBiologica i WHERE i.patologia.id = :patologiaId");
        query.setParameter("patologiaId", p.getId());
        List<Gene> genes = query.getResultList();
        return genes;
    }
}