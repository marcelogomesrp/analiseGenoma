package org.analiseGenoma.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.DbBioInfoDao;
import org.analiseGenoma.model.DbBio;
import org.analiseGenoma.model.DbBioInfo;
import org.analiseGenoma.model.Disease;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.service.util.CSVReader;
import org.analiseGenoma.service.util.Line;

public class DbBioInfoService extends Service<DbBioInfo> implements Serializable {

    @Inject
    private DbBioService dbBioService;
    @Inject
    private DiseaseService diseaseService;
    @Inject
    private GeneService geneService;

    public DbBioInfoService() {
        super(DbBioInfo.class);
    }

    private DbBioInfoDao getDao() {
        return ((DbBioInfoDao) dao);
    }

    @Transactional
    public void uploadInfo(byte[] contents, DbBio bd) {
        if (contents.length > 0) {
            Runnable r = () -> {
                this.importar(contents, bd);
                //this.importar(contents);
            };
            Thread t = new Thread(r);
            t.start();
        }
    }

    @Transactional
    private void importar(byte[] contents, DbBio bd) {
        CSVReader csv = new CSVReader(contents);
        for (Line ln : csv.getFile()) {
            DbBioInfo info = new DbBioInfo();
            info.setDbBio(bd);
            Disease diseaseDb = diseaseService.findByName(ln.getField(0));
            info.setDisease(diseaseDb);
            if (info.getDisease() == null) {
                Disease d = new Disease();
                d.setName(ln.getField(0));
                diseaseService.persiste(d);
                info.setDisease(d);
            }
            //Set<Gene> genes = new HashSet<>();
            //Gene gene  = geneService.findBySymbolOrCreate(ln.getField(1).trim().toUpperCase());
            Gene gene = geneService.findBySymbol(ln.getField(1).trim().toUpperCase());
            if(gene == null){
                gene = new Gene();
                gene.setSymbol(ln.getField(1).trim().toUpperCase());
            }
            //info.setGenes(genes);
            info.addGene(gene);
            info.setInfoIdentifier(ln.getField(2));
            this.persiste(info);
        }
    }

    @Transactional
    @Override
    public void persiste(DbBioInfo info) {
        DbBio bd = dbBioService.findById(info.getDbBio().getId());
        info.setDbBio(bd);
        //dao.persist(info);
        DbBioInfo infoBd = getDao().findById(info);

        if (info.getGenes() != null) {
            for (Gene g : info.getGenes()) {
                if (g != null) {
                    if (g.getId() == null) {
                        geneService.persiste(g);
                    } else {
                        geneService.merge(g);
                    }
                }
            }
        }
// aqui marcelo estou inserindo o dbbioinfo
//                
        if(infoBd == null){
            try{
                System.out.println("objA: " + info.toString());
                System.out.println("So gene: " + info.getGenes().toArray()[0].toString());
                getDao().merge(info);
            }catch (Exception ex){
                System.out.println("Erro: " + ex.getMessage());
                System.out.println("obj: " + info.toString());
            }
        }
        else{
            //infoBd.addGene(info.getGenes());
            info.getGenes().forEach(g -> infoBd.addGene(g));
            getDao().merge(infoBd);
        }
//        
//        //getDao().persist(info);
    }

//    public Set<Gene> findGeneByDisease(Disease disease) {
//        //throw new UnsupportedOperationException("Not supported yet."); //To change bodDoy of generated methods, choose Tools | Templates.
//       return  getDao().findGeneByDisease(disease);
//    }

}


/*

    public DbBioInfo findById(DbBioInfo dbinfo) {
        DbBioInfoPK id = new DbBioInfoPK();
        //GeneDbBioPK id = new GeneDbBioPK();
        id.setDbBio(dbinfo.getDbBio());
        id.setDisease(dbinfo.getDisease());
        return manager.find(DbBioInfo.class, id);
    }
*/
