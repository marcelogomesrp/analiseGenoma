package org.analiseGenoma.service;

import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.DiseaseDao;
import org.analiseGenoma.model.Disease;

@Named
public class DiseaseService extends Service<Disease> implements Serializable {

    public DiseaseService() {
        super(Disease.class);
    }

    private DiseaseDao getDao() {
        return ((DiseaseDao) dao);
    }



    @Transactional
    public void adicionar(Disease patologia) {
        getDao().persist(patologia);
    }

    //https://emmanuelneri.com.br/2016/04/03/abrindo-novas-transacoes-dentro-de-metodos-transacionais/
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    void adicionar2(Disease patologia) {
        getDao().persist(patologia);
    }

    @Transactional
    public void atualizar(Disease patologia) {
        getDao().merge(patologia);
    }

    public List<Disease> buscar() {
        return getDao().find();
    }

    public Disease buscarPorId(Long id) {
        return getDao().findById(id);
    }

    public List<Disease> buscarNome(String nome) {
        return getDao().buscarLikeNome(nome);
    }

    public List<Disease> buscarCid(String cid) {
        return getDao().buscarCid(cid);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Disease buscarAddNome(String name) {
        Disease p = getDao().buscarNome(name);
        if (p == null) {
            p = new Disease();
            p.setName(name);
            getDao().persist(p);
        }
        return p;
    }

}
