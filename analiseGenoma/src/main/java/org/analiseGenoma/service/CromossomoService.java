package org.analiseGenoma.service;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.model.Cromossomo;

public class CromossomoService implements Serializable{
    @Inject
    private CromossomoDao cromossomoDao;
    @Transactional
    public void adicionar(Cromossomo cromossomo){
        cromossomoDao.adicionar(cromossomo);
    }
    
    @Transactional
    public void atualizar(Cromossomo cromossomo) {
        cromossomoDao.atualizar(cromossomo);
    }
    
    public List<Cromossomo> buscar(){
        return cromossomoDao.buscar();
    }

    public Cromossomo buscarPorId(Long id) {
        return cromossomoDao.buscarPorId(id);
    }

    public Cromossomo buscarPorNome(String nome) {
        return cromossomoDao.buscarPorNome(nome);
    }
    
    @Transactional
    public Cromossomo buscarOuCriar(String nome){
        return cromossomoDao.buscarOuCriar(nome);
    }
}
