package org.analiseGenoma.service;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.VarianteDao;
import org.analiseGenoma.model.Variante;

@Named
public class VarianteService extends Service<Variante>{
    @Inject
    private CromossomoService cromossomoService;
    @Inject
    private GeneService geneService;
    //@Inject private VcfService vcfService;
    @Inject private UmdPredictorService umdPredictorService;
    @Inject private ZygosityService zygosityService;
    
    public VarianteService() {
        super(Variante.class);
    }
    private VarianteDao getDao(){
        return ((VarianteDao) dao);
    }

    @Override
    @Transactional
    public void adicionar(Variante variante) {
        //if(variante.getVcf() != null)
            //vcfService.atualizar(variante.getVcf());
        if(variante.getCromossomo() !=null)
            cromossomoService.atualizar(variante.getCromossomo());
        if(variante.getGene() != null)
            geneService.atualizar(variante.getGene());
//        if(variante.getUmdPredictor() != null)
//            umdPredictorService.atualizar(variante.getUmdPredictor());
        if(variante.getZygosity() != null)
            zygosityService.atualizar(variante.getZygosity());
        
        super.adicionar(variante); 
    }
    
   
    
    
    
}
