package org.analiseGenoma.service;

import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import org.analiseGenoma.dao.ImpactoDao;
import org.analiseGenoma.model.Impact;

@Named
public class ImpactoServiceTeste {}

/*
extends Service<Impact> implements Serializable {

    public ImpactoServiceTeste() {
        super(Impact.class);
    }
    private ImpactoDao getDao(){
        return ((ImpactoDao) dao);
    }
    
    
    public List<Impact> buscarNome(String nome) {                
        return getDao().buscarNome(nome);
    }

  
}
*/