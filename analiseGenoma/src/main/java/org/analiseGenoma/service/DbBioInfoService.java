package org.analiseGenoma.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.DbBioInfoDao;
import org.analiseGenoma.model.DbBio;
import org.analiseGenoma.model.DbBioInfo;
import org.analiseGenoma.model.Disease;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.Variante;
import org.analiseGenoma.model.VarianteRevisadaGestor;
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

    public DbBioInfo findByIDbIdDisease(Long idDb, Long IdDisease) {
        return getDao().findByIDbIdDisease(idDb, IdDisease);
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
            if (gene == null) {
                gene = new Gene();
                gene.setSymbol(ln.getField(1).trim().toUpperCase());
            }
            //info.setGenes(genes);
            info.addGene(gene);
            info.setInfoIdentifier(ln.getField(2));
            info.setUrl(ln.getField(3));
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
        if (infoBd == null) {
            try {
                System.out.println("objA: " + info.toString());
                System.out.println("So gene: " + info.getGenes().toArray()[0].toString());
                getDao().merge(info);
            } catch (Exception ex) {
                System.out.println("Erro: " + ex.getMessage());
                System.out.println("obj: " + info.toString());
            }
        } else {
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
    public DbBioInfo findByIDbIdDiseaseIdGene(Long idDb, Long idDisease, Long geneId) {
        return getDao().findByIDbIdDiseaseIdGene(idDb, idDisease, geneId);
    }

    public List<DbBioInfo> findComplete() {
        return getDao().findComplete();
    }

    @Transactional
    public void persiste(List<VarianteRevisadaGestor> variantes, Disease patologia) {
        List<VarianteRevisadaGestor> v1 = variantes.stream().filter(v -> v.getPatogenic() == 5).collect(Collectors.toList());
        List<Gene> genes = v1.stream().map(v -> v.getVariant().getGene()).distinct().filter(v -> Objects.nonNull(v)).collect(Collectors.toList());
        DbBioInfo info = this.findByDsease(patologia);
        //informacao_biologica
        //DbBioInfo info = new DbBioInfo();
        if (info.getDbBio() == null) {
            info.setGenes(new HashSet<>(genes));
            info.setDisease(patologia);
            info.setDbBio(dbBioService.findById(1L));
            info.setUrl("Pathogenic");
            this.persiste(info);
        }else{
            for(Gene g:genes){
                info.getGenes().add(g);
            }
            info.setUrl("Pathogenic");
            this.merge(info);
        }
        /*
        //4 inico
        List<VarianteRevisadaGestor> v4 = variantes.stream().filter(v -> v.getPatogenic() == 4).collect(Collectors.toList());
        List<Gene> genes4 = v4.stream().map(v -> v.getVariant().getGene()).distinct().filter(v -> Objects.nonNull(v)).collect(Collectors.toList());
        DbBioInfo info4 = this.findByDsease(patologia);
        if (info4.getDbBio() == null) {
            info4.setGenes(new HashSet<>(genes));
            info4.setDisease(patologia);
            info4.setDbBio(dbBioService.findById(1L));
            info4.setUrl("Likely pathogenic");
            this.persiste(info4);
        }else{
            for(Gene g:genes4){
                info4.getGenes().add(g);
            }
            info4.setUrl("Likely pathogenic");
            this.merge(info4);
        }
        //4 fim
        */
        
        
        System.out.println("aqui");

        /*
        v1.forEach((tmp) -> {
            vs.add(tmp.getVariant());
        });
         */
//= v1.stream()
        //.map(v -> v.getVariant()).collect(Collectors.toList());
        //.filter(v -> Objects.nonNull(v))
        //.collect(Collectors.toList());
        //genes = v1.stream().map(v -> v.getVariant().getGene()).distinct().filter(v -> Objects.nonNull(v)).collect(Collectors.toList() );
        /*
        List<Gene> genes = 
                variantes.stream()                
                .map(v -> v.getVariant().getGene())                
                .distinct()
                .filter(v -> Objects.nonNull(v))
                .filter(v -> v.get)
                .collect(Collectors.toList() );                
                
        DbBioInfo info = new DbBioInfo();
         */
    }

    public DbBioInfo findByDsease(Disease patologia) {
        return getDao().findByDsease(patologia);
    }

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
