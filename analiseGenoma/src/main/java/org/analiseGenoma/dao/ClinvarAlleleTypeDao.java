package org.analiseGenoma.dao;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.ClinvarAlleleType;
public class ClinvarAlleleTypeDao extends DAO<ClinvarAlleleType> {
	public ClinvarAlleleTypeDao() {
        super(ClinvarAlleleType.class);
    }
    public List<ClinvarAlleleType> findByName(String name) {
        List<ClinvarAlleleType> list = null;
        try {
            Query query = manager.createQuery("SELECT i FROM ClinvarAlleleType i WHERE i.name like :name");
            query.setParameter("name", name);
            list = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return list;
    }
}
