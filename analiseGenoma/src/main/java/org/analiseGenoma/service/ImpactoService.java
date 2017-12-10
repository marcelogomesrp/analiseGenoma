package org.analiseGenoma.service;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.ImpactDao;
import org.analiseGenoma.model.Impact;

@Named
public class ImpactoService implements Serializable {

    @Inject
    private ImpactDao impactDao;

    @PostConstruct
    public void init() {
    }

    @Transactional
    public void adicionar(Impact impacto) {
        impactDao.adicionar(impacto);
    }

    @Transactional
    public void atualizar(Impact impacto) {
        impactDao.atualizar(impacto);
    }

    public List<Impact> buscar() {
        return impactDao.buscar();
    }

    public Impact buscarPorId(Long id) {
        return impactDao.buscarPorId(id);
    }

    public List<Impact> buscarNome(String nome) {
        return impactDao.findByName(nome);
    }

    @Transactional
    public Impact getImpacto(Impact impacto) {
        List<Impact> list = this.buscarNome(impacto.getName());
        if(list.size() == 1){
            return list.get(0);
        }else{
            this.adicionar(impacto);
            return impacto;
        }
    }

    public Impact buscarPorNome(String nome) {
        List<Impact> impactos =  impactDao.findByName(nome);
        if(impactos.size() == 1)
            return impactos.get(0);
        return null;
    }

  
}
