
package org.analiseGenoma.service;

import java.io.Serializable;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.InformacaoBiologicaDao;
import org.analiseGenoma.model.InformacaoBiologica;

public class InformacaoBiologicaServiceExtend implements Serializable {

    @Inject private InformacaoBiologicaDao informacaoBiologicaDao;
    
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public InformacaoBiologica BuscarAdd(InformacaoBiologica info){
        informacaoBiologicaDao.adicionar(info);
        return info;
    }
}
