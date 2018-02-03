package org.analiseGenoma.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.dao.GeneDbBioDao;
import org.analiseGenoma.dao.GeneListDao;
import org.analiseGenoma.dao.GeneSymbolSynonymDao;
import org.analiseGenoma.model.DbBio;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.GeneDbBio;
import org.analiseGenoma.model.GeneList;
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
    private GeneDbBioDao geneDbBioDao;
    @Inject
    private GeneListDao geneListDao;

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
//    @Transactional
//    @Override
//    public void persiste(Gene g) {
//        //dbBioService.merge(g.getDbbio());        
//        getDao().persist(g);
//        GeneSymbolSynonym gs = new GeneSymbolSynonym();
//        gs.setSymbol(g.getSymbol());
//        gs.setGene(g);
//        //gs.setId(1L);
//        geneSymbolSynonymDao.persist(gs);
//        System.out.println("inserido o gene: " + g.toString());
//    }
    @Transactional
    public void persiste(Gene gene, List<String> symbolSynonyms, List<String> nameSynonyms, GeneDbBio geneDbBio) throws Exception {
        if (symbolSynonyms == null) {
            symbolSynonyms = new ArrayList<>();
        }
        symbolSynonyms.add(gene.getSymbol());
        symbolSynonyms = symbolSynonyms.stream().distinct().collect(Collectors.toList());
        List<Gene> genes = this.findBySymbol(symbolSynonyms);
        if (genes != null) {
            if (genes.size() == 1) {
                gene = genes.get(0);
                for (String symbol : symbolSynonyms) {
                    if (this.findBySymbol(symbol) == null) {
                        geneSymbolSynonymDao.persist(new GeneSymbolSynonym(gene, symbol));
                    }
                }
            } else {
                throw new Exception("Gene aponta para dois ou mais genes diferentes");
            }
        } else {
            getDao().persist(gene);
            for (String symbol : symbolSynonyms) {
                geneSymbolSynonymDao.persist(new GeneSymbolSynonym(gene, symbol));
            }
        }

        if (geneDbBio.getDbBio() != null) {
            geneDbBio.setGene(gene);
            geneDbBio.setDbBio(dbBioService.findById(geneDbBio.getDbBio().getId()));
            //dbBioService.merge(geneDbBio.getDbBio());            
            geneDbBioDao.persist(geneDbBio);
        }

        //GeneSymbolSynonym gs;
//        if (geneDb == null) {
//            gs = new GeneSymbolSynonym(gene, gene.getSymbol());
//            geneSymbolSynonymDao.persist(gs);
//        }
//        Gene geneDb = this.findBySymbol(gene.getSymbol());
//        if (geneDb != null) {
//            symbolSynonyms.add(gene.getSymbol());
//            nameSynonyms.add(gene.getName());
//            gene = geneDb;
//        } else {
//            getDao().persist(gene);
//        }
//        geneDb = this.findBySymbol(gene.getSymbol());
//        GeneSymbolSynonym gs;
//        if (geneDb == null) {
//            gs = new GeneSymbolSynonym(gene, gene.getSymbol());
//            geneSymbolSynonymDao.persist(gs);
//        }
//
//        GeneNameSynonym gn = new GeneNameSynonym(gene, gene.getName());
//        geneNameSynonymDao.persist(gn);
//
//        for (String symbol : symbolSynonyms) {
//            geneDb = this.findBySymbol(symbol);
//            if (geneDb == null) {
//                gs = new GeneSymbolSynonym(gene, symbol);
//                geneSymbolSynonymDao.persist(gs);
//            }
//        }
//        for (String name : nameSynonyms) {
//            gn = new GeneNameSynonym(gene, name);
//            geneNameSynonymDao.persist(gn);
//        }
//        if (geneDbBio.getDbBio() != null) {
//            geneDbBio.setGene(gene);
//            geneDbBio.setDbBio(dbBioService.findById(geneDbBio.getDbBio().getId()));
//            //dbBioService.merge(geneDbBio.getDbBio());            
//            geneDbBioDao.persist(geneDbBio);
//        }
    }

    @Transactional
    private void persiste(GeneDbBio gd) {
        geneDbBioDao.persist(gd);
    }

    private Gene findMasterBySymbol(String symbol) {
        List<Gene> genes = getDao().findByProperty("symbol", symbol);
        if (genes.isEmpty()) {
            return null;
        } else {
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

    public InputStream findAsXML() {
        //Gene g = this.getFirstOrNull(getDao().find());
        Gene g = new Gene();
        g.setName("name");
        GeneList gl = new GeneList();
        gl.setGenes(new ArrayList<>());
        gl.getGenes().add(g);
        gl.setGenes(getDao().find());
        try {
            String s = geneListDao.ObjectToXML(gl).toString();
            InputStream is = new ByteArrayInputStream(s.getBytes());
            return is;
        } catch (Exception ex) {
            Logger.getLogger(GeneService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Gene findBySymbol(String symbol) {
//        List<GeneSymbolSynonym> geneSynonymous = geneSymbolSynonymDao.findByProperty("symbol", symbol);
//        List<Gene> genes = new ArrayList<>();
//        for(GeneSymbolSynonym gs: geneSynonymous){
//            genes.add(gs.getGene());
//        }
        return getDao().findBySymbol(symbol);

    }

    private List<Gene> findBySymbol(List<String> symbolList) {
        return getDao().findBySymbol(symbolList);
    }

    //@Transactional
    public void upload(byte[] contents, DbBio dbBio) {
        if (contents.length > 0) {
            //this.importData(contents);
            Runnable r = () -> {
                this.importData(contents, dbBio);
            };
            Thread t = new Thread(r);
            t.start();
        }
    }

    @Transactional
    @Override
    public void persiste(Gene g) {
        Gene geneDb = this.findBySymbol(g.getSymbol());
        Gene gene;
        if (geneDb != null) {
            gene = geneDb;
        } else {
            getDao().persist(g);
            GeneSymbolSynonym gs = new GeneSymbolSynonym();
            gs.setGene(g);
            gs.setSymbol(g.getSymbol());
            geneSymbolSynonymDao.persist(gs);
            gene = g;
        }
        Set<GeneSymbolSynonym> synonymous = g.getGeneSymbolSynonym();
        if (synonymous != null) {
            for (GeneSymbolSynonym gs : synonymous) {
                geneDb = this.findBySymbol(gs.getSymbol());
                if (geneDb == null) {
                    gs.setGene(gene);
                    geneSymbolSynonymDao.persist(gs);
                }
            }
        }

    }

    @Transactional
    public void persiste(Gene g, GeneDbBio gd) {
        Gene geneDb = this.findBySymbol(g.getSymbol());
        Gene gene;
        if (geneDb != null) {
            gene = geneDb;
        } else {
            getDao().persist(g);
            GeneSymbolSynonym gs = new GeneSymbolSynonym();
            gs.setGene(g);
            gs.setSymbol(g.getSymbol());
            geneSymbolSynonymDao.persist(gs);
            gene = g;
        }
        Set<GeneSymbolSynonym> synonymous = g.getGeneSymbolSynonym();
        if (synonymous != null) {
            for (GeneSymbolSynonym gs : synonymous) {
                geneDb = this.findBySymbol(gs.getSymbol());
                if (geneDb == null) {
                    gs.setGene(gene);
                    geneSymbolSynonymDao.persist(gs);
                }
            }
        }
        //aqui
        //esta dando erro de chave duplicada
        gd.setGene(gene);
        DbBio db = dbBioService.findById(gd.getDbBio().getId());
        gd.setDbBio(db);
        try {
            GeneDbBio geneDbbioBD = geneDbBioDao.findById(gd); //findByExample(gd);
            if (geneDbbioBD == null) {
                geneDbBioDao.persist(gd);
            } 
//            else {
//                if (list.isEmpty()) {
//                    geneDbBioDao.persist(gd);
//                }
//                //System.out.println("*************************************************** tamanho da lista: " + list.size());
//            }
        } catch (Exception ex) {
            System.out.println("---> " + gd.toString());
        }
    }

    private void importData(byte[] contents, DbBio dbBio) {
        CSVReader csv = new CSVReader(contents);
        int x = 0;
        for (Line ln : csv.getFile()) {
            Gene g = this.makeGene(ln);
            GeneDbBio gd = this.makeGeneDbBio(ln, dbBio);
            //this.persiste(g);
            this.persiste(g, gd);

            //this.persiste(gd);
        }

    }

    //HGNC ID,Approved Symbol,Approved Name,Previous Symbols,Synonyms
    //ln: Line{fields=[37133, A1BG-AS1, A1BG antisense RNA 1, NCRNA00181, A1BGAS, A1BG-AS, FLJ23569]}
    @Transactional
    private void importInfo(byte[] contents, DbBio dbBio) {
        CSVReader csv = new CSVReader(contents);
        for (Line ln : csv.getFile()) {
            Gene g = this.makeGene(ln);
            Gene geneBd = this.findBySymbol(g.getSymbol());
            if (geneBd == null) {
                try {
                    //this.persiste(g);
                    //getDao().persist(g);
                    this.persiste(g, null, null, null);
                } catch (Exception ex) {
                    Logger.getLogger(GeneService.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Erro ao persisti");
                }
                System.out.println("Gene persistido: " + g.toString());
            } else {
//                Set<GeneSymbolSynonym> tmp = g.getGeneSymbolSynonym();
//                if (tmp == null) {
//                    tmp = new HashSet<>();
//                }
//                g = geneBd;
//                g.setGeneSymbolSynonym(tmp);
                System.out.println("Gene Ja exisitia: " + g.toString());
            }
//            if (g.getGeneSymbolSynonym() != null) {
//                System.out.println("fazendo o for com tamanho: " + g.getGeneSymbolSynonym().size());
//
//                for (GeneSymbolSynonym gs : g.getGeneSymbolSynonym()) {
//                    GeneSymbolSynonym gn = new GeneSymbolSynonym();
//                    gn.setGene(g);
//                    gn.setSymbol(gs.getSymbol());                    
//                    geneSymbolSynonymDao.persist(gn);
//                    //geneSymbolSynonymDao.persist(gn);
//                }
//            }
            System.out.println("Fim do gene");
        }
    }

    private GeneDbBio makeGeneDbBio(Line ln, DbBio dbBio) {
        GeneDbBio gdb = new GeneDbBio();
        gdb.setDbBio(dbBio);
        gdb.setDbIdentifier(ln.getField(0));
        if (ln.getSize() > 5) {
            gdb.setUrl(ln.getField(5));
        }
        //gdb.setGene(this.findBySymbol(ln.getField(1)));
        return gdb;
    }

    private Gene makeGene(Line ln) {
        Gene g = new Gene();
        g.setSymbol(ln.getField(1));
        g.setName(ln.getField(2));
        if (ln.getSize() > 3) {
            if (!(ln.getField(3).isEmpty())) {
                String synonymous[] = ln.getField(3).split(",");
                for (String s : synonymous) {
                    g.addGeneSymbolSynonymous(s);
                }
            }
        }
        if (ln.getSize() > 4) {
            if (!(ln.getField(4).isEmpty())) {
                String synonymous[] = ln.getField(4).split(",");
                for (String s : synonymous) {
                    g.addGeneSymbolSynonymous(s);
                }
            }
        }

        return g;
    }
//
//    @Transactional
//    private void persiste(GeneSymbolSynonym gs) {
//        Gene g = this.findBySymbol(gs.getSymbol());
//        if (g == null) {
//            GeneSymbolSynonym gsn = new GeneSymbolSynonym();
//            gsn.setSymbol(gs.getSymbol());
//            gsn.setGene(this.findBySymbol(gs.getGene().getSymbol()));
//            System.out.println("Persistindo gs... " + gsn.toString());
//            geneSymbolSynonymDao.persist(gsn);
//            System.out.println("gs persistido " + gsn.toString());
//        }
//    }

}
