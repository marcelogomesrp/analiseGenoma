package org.analiseGenoma.dao;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.ClinvarAccession;

public class ClinvarAccessionDao extends DAO<ClinvarAccession> {
	public ClinvarAccessionDao() {
        super(ClinvarAccession.class);
    }
    public List<ClinvarAccession> findByName(String name) {
        List<ClinvarAccession> list = null;
        try {
            Query query = manager.createQuery("SELECT i FROM ClinvarAccession i WHERE i.name like :name");
            query.setParameter("name", name);
            list = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return list;
    }
}
