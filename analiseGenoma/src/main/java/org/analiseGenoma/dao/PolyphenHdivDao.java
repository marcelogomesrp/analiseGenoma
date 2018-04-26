package org.analiseGenoma.dao;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.PolyphenHdiv;
public class PolyphenHdivDao extends DAO<PolyphenHdiv> {
	public PolyphenHdivDao() {
        super(PolyphenHdiv.class);
    }
    public List<PolyphenHdiv> findByName(String name) {
        List<PolyphenHdiv> list = null;
        try {
            Query query = manager.createQuery("SELECT i FROM PolyphenHdiv i WHERE i.name like :name");
            query.setParameter("name", name.toUpperCase());
            list = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return list;
    }
}
