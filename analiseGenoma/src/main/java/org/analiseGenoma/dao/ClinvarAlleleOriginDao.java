package org.analiseGenoma.dao;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.ClinvarAlleleOrigin;
public class ClinvarAlleleOriginDao extends DAO<ClinvarAlleleOrigin> {
	public ClinvarAlleleOriginDao() {
        super(ClinvarAlleleOrigin.class);
    }
    public List<ClinvarAlleleOrigin> findByName(String name) {
        List<ClinvarAlleleOrigin> list = null;
        try {
            Query query = manager.createQuery("SELECT i FROM ClinvarAlleleOrigin i WHERE i.name like :name");
            query.setParameter("name", name);
            list = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return list;
    }
}
