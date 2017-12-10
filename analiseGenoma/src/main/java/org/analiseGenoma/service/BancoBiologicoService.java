package org.analiseGenoma.service;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.BancoBiologicoDao;
import org.analiseGenoma.model.BancoBiologico;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.Patologia;

public class BancoBiologicoService implements Serializable{
    @Inject
    private BancoBiologicoDao bdDao;
    @Transactional
    public void adicionar(BancoBiologico bancoBiologico){
        bdDao.adicionar(bancoBiologico);
    }
    
    @Transactional
    public void atualizar(BancoBiologico bancoBiologico) {
        bdDao.atualizar(bancoBiologico);
    }
    
    public List<BancoBiologico> buscar(){
        return bdDao.buscar();
    }

    public BancoBiologico buscarPorId(Long id) {
        return bdDao.buscarPorId(id);
    }


    
}
