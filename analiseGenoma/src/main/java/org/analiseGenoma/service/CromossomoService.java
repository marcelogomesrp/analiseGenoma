package org.analiseGenoma.service;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.AgeDao;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.DAO;
import org.analiseGenoma.model.Age;
import org.analiseGenoma.model.Cromossomo;

public class CromossomoService extends Service<Cromossomo> implements Serializable{

    public CromossomoService() {
        super(Cromossomo.class);
    }

    private CromossomoDao getDao() {
        return ((CromossomoDao) dao);
    }
    
    
    @Transactional
    public void adicionar(Cromossomo cromossomo){
        getDao().persist(cromossomo);
    }
    
    @Transactional
    public void atualizar(Cromossomo cromossomo) {
        getDao().merge(cromossomo);
    }
    
    public List<Cromossomo> buscar(){
        return getDao().find();
    }

    public Cromossomo buscarPorId(Long id) {
        return getDao().findById(id);
    }

    public Cromossomo buscarPorNome(String nome) {
        return getDao().buscarPorNome(nome);
    }
    
    @Transactional
    public Cromossomo buscarOuCriar(String nome){
        return getDao().buscarOuCriar(nome);
    }

    public List<Cromossomo> findByName(String name) throws Exception {
//        Cromossomo c = new Cromossomo();
//        c.setNome(name);
//        return getDao().findByExample(c);
        return getDao().findByProperty("nome", name, DAO.MatchMode.START);
    }
}
