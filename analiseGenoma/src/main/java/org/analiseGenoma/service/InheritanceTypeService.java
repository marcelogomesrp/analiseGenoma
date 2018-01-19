package org.analiseGenoma.service;

import java.io.Serializable;
import javax.inject.Named;
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
    


}