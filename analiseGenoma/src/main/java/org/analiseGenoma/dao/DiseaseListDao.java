package org.analiseGenoma.dao;

import javax.inject.Named;
import org.analiseGenoma.dao.xml.DAOXML;
import org.analiseGenoma.model.DiseaseList;

@Named
public class DiseaseListDao extends DAOXML<DiseaseList> {

    public DiseaseListDao() {
        super(DiseaseList.class);
    }
}
