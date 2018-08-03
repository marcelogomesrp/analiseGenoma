package org.analiseGenoma.service;

import java.util.HashSet;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.DbBioInfoDao;
import org.analiseGenoma.dao.VarianteRevisadaGestorDao;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.DbBioInfo;
import org.analiseGenoma.model.Variante;
import org.analiseGenoma.model.VarianteRevisadaGestor;

@Named
public class VarianteRevisadaGestorService extends Service<VarianteRevisadaGestor> {

    @Inject
    private DbBioInfoDao bioInfoDao;
    
    public VarianteRevisadaGestorService() {
        super(VarianteRevisadaGestor.class);
    }

    private VarianteRevisadaGestorDao getDao() {
        return ((VarianteRevisadaGestorDao) dao);
    }

    public List<VarianteRevisadaGestor> findByVarianteRevisor(Variante variante, Analise analise) {
        return this.getDao().findByVarianteRevisor(variante, analise);
    }

//    @Override
//    @Transactional
//    public void merge(VarianteRevisadaGestor varianteRevisadaGestor) {
//        super.merge( varianteRevisadaGestor); 
//        DbBioInfo info =  null;
//        if(info == null){
//            info = new DbBioInfo();
//            info.setGenes(new HashSet<>());
//            info.getGenes().add(varianteRevisadaGestor.getVariant().getGene());
//            
//        }
//        
//    }

    public List<VarianteRevisadaGestor> findByAnalise(Analise analise) {
        return this.getDao().findByProperty("analise", analise);
    }
    
    

}
