package org.analiseGenoma.dao;

import javax.inject.Named;
import org.analiseGenoma.model.GeneList;


@Named
public class GeneListDao extends org.analiseGenoma.dao.xml.DAOXML<GeneList> {

    public GeneListDao() {
        super(GeneList.class);
    }
}