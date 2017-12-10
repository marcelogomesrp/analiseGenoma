package org.analiseGenoma.service;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.PatologiaDao;
import org.analiseGenoma.model.Patologia;

public class PatologiaService implements Serializable{
    @Inject
    private PatologiaDao patologiaDao;
    @Transactional
    public void adicionar(Patologia patologia){
        patologiaDao.adicionar(patologia);
    }
    
    //https://emmanuelneri.com.br/2016/04/03/abrindo-novas-transacoes-dentro-de-metodos-transacionais/
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    void adicionar2(Patologia patologia) {
        patologiaDao.adicionar(patologia);
    }
    
    
    @Transactional
    public void atualizar(Patologia patologia) {
        patologiaDao.atualizar(patologia);
    }
    
    public List<Patologia> buscar(){
        return patologiaDao.buscar();
    }

    public Patologia buscarPorId(Long id) {
        return patologiaDao.buscarPorId(id);
    }

    public List<Patologia> buscarNome(String nome) {
        return patologiaDao.buscarLikeNome(nome);
    }

    public List<Patologia>  buscarCid(String cid) {
        return patologiaDao.buscarCid(cid);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Patologia buscarAddNome(String nome) {
        Patologia p = patologiaDao.buscarNome(nome);        
        if(p == null){
            p = new Patologia();
            p.setNome(nome);
            patologiaDao.adicionar(p);            
        }
        return p;
    }    



    
}
