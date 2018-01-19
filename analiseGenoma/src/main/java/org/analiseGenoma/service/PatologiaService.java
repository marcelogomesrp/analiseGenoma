package org.analiseGenoma.service;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.PatologiaDao;
import org.analiseGenoma.model.Disease;

public class PatologiaService implements Serializable{
    @Inject
    private PatologiaDao patologiaDao;
    @Transactional
    public void adicionar(Disease patologia){
        patologiaDao.adicionar(patologia);
    }
    
    //https://emmanuelneri.com.br/2016/04/03/abrindo-novas-transacoes-dentro-de-metodos-transacionais/
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    void adicionar2(Disease patologia) {
        patologiaDao.adicionar(patologia);
    }
    
    
    @Transactional
    public void atualizar(Disease patologia) {
        patologiaDao.atualizar(patologia);
    }
    
    public List<Disease> buscar(){
        return patologiaDao.buscar();
    }

    public Disease buscarPorId(Long id) {
        return patologiaDao.buscarPorId(id);
    }

    public List<Disease> buscarNome(String nome) {
        return patologiaDao.buscarLikeNome(nome);
    }

    public List<Disease>  buscarCid(String cid) {
        return patologiaDao.buscarCid(cid);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Disease buscarAddNome(String name) {
        Disease p = patologiaDao.buscarNome(name);        
        if(p == null){
            p = new Disease();
            p.setName(name);
            patologiaDao.adicionar(p);            
        }
        return p;
    }    



    
}
