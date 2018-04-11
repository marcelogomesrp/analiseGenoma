package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.Query;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.VariantSelected;
import org.analiseGenoma.model.Variante;

public class VariantSelectedDao extends DAO<VariantSelected> {

    public VariantSelectedDao() {
        super(VariantSelected.class);
    }
    
    
    public List<Variante> findVariantByAnalise(Analise analise){
        Query query = manager.createQuery("SELECT vs.variantes FROM VariantSelected vs WHERE vs.analise = :analise");
        query.setParameter("analise", analise);
        return query.getResultList();
    }


}