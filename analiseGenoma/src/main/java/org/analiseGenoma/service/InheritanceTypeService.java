package org.analiseGenoma.service;

import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.DAO;
import org.analiseGenoma.dao.InheritanceTypeDao;
import org.analiseGenoma.model.InheritanceType;



@Named
public class InheritanceTypeService extends Service<InheritanceType> implements Serializable{

    public InheritanceTypeService() {
        super(InheritanceType.class);
    }

    private InheritanceTypeDao getDao() {
        return ((InheritanceTypeDao) dao);
    }
    
    public List<InheritanceType> findLikeType(String query){
        return getDao().findByProperty("type", query, DAO.MatchMode.START);
    }

    public InheritanceType findByType(String type) {
        List<InheritanceType> list = getDao().findByProperty("type", type);        
        if(list.size() == 1)
            return list.get(0);
        return null;
    }

    @Transactional
    public InheritanceType findOrCreate(String type) {
        InheritanceType i = this.getFirstOrNull( getDao().findByProperty("type", type) );
        if(i == null){
            i = new InheritanceType();
            i.setType(type);
            this.persiste(i);
        }
        return i;
    }


}