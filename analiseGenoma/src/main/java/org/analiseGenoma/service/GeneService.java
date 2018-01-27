package org.analiseGenoma.service;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.dao.GeneNameSynonymDao;
import org.analiseGenoma.dao.GeneSymbolSynonymDao;
import org.analiseGenoma.model.DbBio;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.GeneNameSynonym;
import org.analiseGenoma.model.GeneSymbolSynonym;
import org.analiseGenoma.service.util.CSVReader;
import org.analiseGenoma.service.util.Line;

@Named
public class GeneService extends Service<Gene> implements Serializable {

    public GeneService() {
        super(Gene.class);
    }

    private GeneDao getDao() {
        return ((GeneDao) dao);
    }

    @Inject
    private GeneSymbolSynonymDao geneSymbolSynonymDao;
    @Inject
    private GeneNameSynonymDao geneNameSynonymDao;
    
    
    @Inject
    private DbBioService dbBioService;

    public Gene findBySymbolOrCreate(String symbol) {
        List<Gene> genes = getDao().findByProperty("symbol", symbol);
        Gene gene;
        if (genes.size() == 1) {
            gene = genes.get(0);
//            while (gene.getSynonymou() != null) {
//                gene = gene.getSynonymou();
//            }
        } else {

            gene = new Gene();
            gene.setSymbol(symbol);
            this.persiste(gene);
        }
        return gene;
    }

    public List<Gene> find2() {
        return getDao().find();
    }


    //@Transactional
    public void upload(byte[] contents, Long idBd) {
        if (contents.length > 0) {
            Runnable r = () -> {
                this.importar(contents, idBd);
                //this.importar(contents);
            };
            Thread t = new Thread(r);
            t.start();
        }
    }
    

    
    //@Transactional(Transactional.TxType.REQUIRES_NEW)
    //@Transactional
    public void importar(byte[] contents, Long idBd) {
        CSVReader csv = new CSVReader(contents);
        DbBio db = dbBioService.findById(idBd);
        for (Line ln : csv.getFile()) {
            if (ln.getSize() >= 1) {
                Gene g = new Gene();
                //g.setDbbio(db);
                g.setName(ln.getField(0));
                if (ln.getSize() >= 2) {
                    g.setSymbol(ln.getField(1));
                }
                Gene master = this.findMasterBySymbol(g.getSymbol());
//                if(master!= null){
//                    g.setSynonymou(master);
//                }
//                g.setSynonymou(this.findMasterBySymbol(g.getSymbol()));
                this.persiste(g);
            }
        }
    }
    
    /*
    sobrescrevi este metodo, pois estava dando erro na transação
    */
    @Transactional
    @Override
    public void persiste(Gene g){
        //dbBioService.merge(g.getDbbio());        
        getDao().persist(g);
        GeneSymbolSynonym gs = new GeneSymbolSynonym();
        gs.setSymbol(g.getSymbol());
        gs.setGene(g);
        //gs.setId(1L);
        geneSymbolSynonymDao.persist(gs);
        System.out.println("inserido o gene: " + g.toString());
    }
    @Transactional
    //public void persiste(Gene gene, List<String> symbolSynonyms) {
    public void persiste(Gene gene, List<String> symbolSynonyms, List<String> nameSynonyms) {        
        getDao().persist(gene);
        GeneSymbolSynonym gs = new GeneSymbolSynonym(gene, gene.getSymbol());
        geneSymbolSynonymDao.persist(gs);
        GeneNameSynonym gn = new GeneNameSynonym(gene, gene.getName());
        geneNameSynonymDao.persist(gn);
        if(symbolSynonyms != null){
            for(String symbol: symbolSynonyms){
                gs = new GeneSymbolSynonym(gene, symbol);
                geneSymbolSynonymDao.persist(gs);
            }
        }
        if(nameSynonyms != null){
            for(String name: nameSynonyms){
                gn = new GeneNameSynonym(gene, name);
                geneNameSynonymDao.persist(gn);
            }
        }
    }

    private Gene findMasterBySymbol(String symbol) {
        List<Gene> genes = getDao().findByProperty("symbol", symbol);
        if(genes.isEmpty()){
            return null;
        }else{
            Gene g = genes.get(0);
//            while(g.getSynonymou() != null){
//                g = g.getSynonymou();
//            }
            return g;
        }
    }

    public Gene uploadXML(byte[] contents) {
        return this.importarXML(contents);
//        if (contents.length > 0) {
//            Runnable r = () -> {
//                this.importarXML(contents);
//            };
//            Thread t = new Thread(r);
//            t.start();
//        }
    }

    private Gene importarXML(byte[] contents) {
        try {
            Gene g = this.XmlToObject(new ByteArrayInputStream(contents));
            return g;
        } catch (Exception ex) {
            Logger.getLogger(GeneService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro na conversado do import");
        }
        return null;
    }

    
        
    

    


}
