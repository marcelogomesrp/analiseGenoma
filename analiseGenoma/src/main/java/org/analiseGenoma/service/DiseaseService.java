package org.analiseGenoma.service;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.DAO;
import org.analiseGenoma.dao.DiseaseDao;
import org.analiseGenoma.model.DbBio;
import org.analiseGenoma.model.Disease;
import org.analiseGenoma.service.util.CSVReader;
import org.analiseGenoma.service.util.Line;

@Named
public class DiseaseService extends Service<Disease> implements Serializable {
    
    @Inject
    private DbBioService dbBioService;
    @Inject
    private InheritanceTypeService inheritanceService;

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

    public List<Disease> findLikeName(String name) {
        return getDao().findByProperty("name", name, DAO.MatchMode.START);
    }

    @Transactional
    public void upload(byte[] contents, Long idDbbio) {
        if (contents.length > 0) {
            Runnable r = () -> {
                this.importar(contents, idDbbio);
            };
            Thread t = new Thread(r);
            t.start();
        }
    }
//Name, ICD, Description, DbIdentifier, Inheritance
    private void importar(byte[] contents, Long idDbbio) {
        CSVReader csv = new CSVReader(contents);
        DbBio db = dbBioService.findById(idDbbio);
        for (Line ln : csv.getFile()) {
            if (ln.getSize() >= 1) {
                Disease d = new Disease();
                d.setDbbio(db);
                d.setName(ln.getField(0));
                if(ln.getSize() >= 2){
                    d.setIcd(ln.getField(1));                    
                }
                if(ln.getSize() >= 3){
                    d.setDescription(ln.getField(2));
                }
                if(ln.getSize() >= 4){
                    d.setDbIdentifier(ln.getField(3));
                }
                if(ln.getSize() >= 5){
                    d.setInheritanceType(inheritanceService.findOrCreate(ln.getField(4)));
                }
                               
                this.findOrCreate(d);
            }
        }
    }

    @Transactional
    private Disease findOrCreate(Disease disease) {
        Disease d = null;
        if(disease.getIcd() != null){
            d = this.findByICD(disease.getIcd());
        }
        if(d == null){
            d = this.findByName(disease.getName());
        }
        if(d == null){
            this.persiste(disease);
            return disease;
        }else{
            if(d.getDbbio() == disease.getDbbio()){
                return d;
            }else{                
                disease.setSynonymous(d);
                this.persiste(disease);
                return disease;
            }
            
        }
    }

    private Disease findByICD(String icd) {
        return this.getFirstOrNull(getDao().findByProperty("icd", icd));
    }
    


    private Disease findByName(String name) {
        return this.getFirstOrNull(getDao().findByProperty("name", name));
    }

}
