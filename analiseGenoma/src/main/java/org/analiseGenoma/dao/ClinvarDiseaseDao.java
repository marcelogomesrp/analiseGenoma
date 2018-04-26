package org.analiseGenoma.dao;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.ClinvarDisease;

public class ClinvarDiseaseDao extends DAO<ClinvarDisease> {
	public ClinvarDiseaseDao() {
        super(ClinvarDisease.class);
    }
    public List<ClinvarDisease> findByName(String name) {
        List<ClinvarDisease> list = null;
        try {
            Query query = manager.createQuery("SELECT i FROM ClinvarDisease i WHERE i.name like :name");
            query.setParameter("name", name.toUpperCase());
            list = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return list;
    }
}
