package org.analiseGenoma.service;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.AgeDao;
import org.analiseGenoma.dao.DbBioDao;
import org.analiseGenoma.model.Age;
import org.analiseGenoma.model.DbBio;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.Disease;

public class BancoBiologicoService implements Serializable{
    @Inject
    private DbBioDao bdDao;
    @Transactional
    public void adicionar(DbBio bancoBiologico){
        bdDao.persist(bancoBiologico);
    }
    
    @Transactional
    public void atualizar(DbBio bancoBiologico) {
        bdDao.merge(bancoBiologico);
    }
    
    public List<DbBio> buscar(){
        return bdDao.find();
    }

    public DbBio buscarPorId(Long id) {
        return bdDao.findById(id);
    }


    
}
/*

extends Service<Age> implements Serializable{

    public AgeService() {
        super(Age.class);
    }

    private AgeDao getDao() {
        return ((AgeDao) dao);
    }
    
    public List<Age> findByAge(int age){
        return getDao().findByAge(age);
    }

*/