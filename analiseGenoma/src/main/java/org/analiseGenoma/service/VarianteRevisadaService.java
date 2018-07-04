package org.analiseGenoma.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.dao.VarianteRevisadaDao;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.User;
import org.analiseGenoma.model.Variante;
import org.analiseGenoma.model.VarianteRevisada;
import org.analiseGenoma.model.Vcf;

@Named
public class VarianteRevisadaService extends Service<VarianteRevisada>{
    @Inject private GeneDao geneDao;
    @Inject private CromossomoDao cromossomoDao;
    
    public VarianteRevisadaService() {
        super(VarianteRevisada.class);
    }
    
    private VarianteRevisadaDao getDao(){
        return ((VarianteRevisadaDao) dao);
    }
    
    public List<VarianteRevisada> findByName(String name){
        return this.getDao().findByName(name);
    }
    public List<VarianteRevisada> findByAnaliseRevisor(Vcf vcf, User revisor) {
        return this.getDao().findByAnaliseRevisor(vcf, revisor);
    }

    public List<VarianteRevisada> findByVarianteRevisor(Variante variante, User revisor) {
        return this.getDao().findByVarianteRevisor(variante, revisor);
    }
    
    public List<VarianteRevisada> findByVarianteRevisor(String idRevisor, Long idVariant) { //String idRevisor, Long idVariant
        return this.getDao().findByVarianteRevisor(idRevisor, idVariant);
    }
}
