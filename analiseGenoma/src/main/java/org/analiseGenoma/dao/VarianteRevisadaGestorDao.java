package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.Variante;
import org.analiseGenoma.model.VarianteRevisada;
import org.analiseGenoma.model.VarianteRevisadaGestor;

public class VarianteRevisadaGestorDao extends DAO<VarianteRevisadaGestor> {

    public VarianteRevisadaGestorDao() {
        super(VarianteRevisadaGestor.class);
    }

    public List<VarianteRevisadaGestor> findByVarianteRevisor(Variante variante, Analise analise) {
        List<VarianteRevisadaGestor> list = null;
        try {
            Query query = manager.createQuery("SELECT vr FROM VarianteRevisadaGestor vr WHERE vr.analise = :analise and vr.variant = :variante");
            query.setParameter("analise", analise);
            query.setParameter("variante", variante);
            list = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return list;
    }

}
