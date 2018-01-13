package org.analiseGenoma.service;

import java.util.List;
import org.analiseGenoma.dao.AnaliseLaudoDao;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.AnaliseLaudo;

public class AnaliseLaudoService extends Service<AnaliseLaudo> {

    public AnaliseLaudoService() {
        super(AnaliseLaudo.class);
    }

    private AnaliseLaudoDao getDao() {
        return ((AnaliseLaudoDao) dao);
    }
    
    public AnaliseLaudo find(Analise analise){
        List<AnaliseLaudo> list = getDao().find(analise);
        if(list.size() == 1){
            return list.get(0);
        }
        return null;
    }
    
}
