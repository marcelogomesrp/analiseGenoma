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
        cromossomoDao.persist(cromossomo);
    }
    
    @Transactional
    public void atualizar(Cromossomo cromossomo) {
        cromossomoDao.merge(cromossomo);
    }
    
    public List<Cromossomo> buscar(){
        return cromossomoDao.find();
    }

    public Cromossomo buscarPorId(Long id) {
        return cromossomoDao.findById(id);
    }

    public Cromossomo buscarPorNome(String nome) {
        return cromossomoDao.buscarPorNome(nome);
    }
    
    @Transactional
    public Cromossomo buscarOuCriar(String nome){
        return cromossomoDao.buscarOuCriar(nome);
    }

    public List<Cromossomo> findByName(String name) throws Exception {
        Cromossomo c = new Cromossomo();
        c.setNome(name);
        return cromossomoDao.findByExample(c);
    }
}
