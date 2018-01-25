package org.analiseGenoma.service;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.DbBio;
import org.analiseGenoma.model.Gene;
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
    private GeneServiceExtend geneServiceExtend;
    @Inject
    private DbBioService dbBioService;

    public Gene findBySymbolOrCreate(String symbol) {
        List<Gene> genes = getDao().findByProperty("symbol", symbol);
        Gene gene;
        if (genes.size() == 1) {
            gene = genes.get(0);
            while (gene.getSynonymou() != null) {
                gene = gene.getSynonymou();
            }
        } else {

            gene = new Gene();
            gene.setSymbol(symbol);
            this.persiste(gene);
        }
        return gene;
    }

    /**
     * *
     */
    public List<Gene> find2() {
        return getDao().find();
    }

    @Transactional
    public void adicionar(Gene gene) {
        getDao().persist(gene);
    }

    @Transactional
    public void atualizar(Gene gene) {
        getDao().merge(gene);
    }

    public List<Gene> buscar() {
        return getDao().find();
    }

    public Gene buscarPorId(Long id) {
        return getDao().findById(id);
    }

    public List<Gene> buscarNome(String nome) {
        return getDao().buscarLikeNome(nome);
    }

    //@Transactional
    public void importar(byte[] contents) {
        if (contents.length > 0) {
            try {
                Scanner scanner = new Scanner(new String(contents))
                        .useDelimiter("\t|\\n");
                while (scanner.hasNext()) {
                    Gene gene = new Gene();
                    gene.setSymbol(scanner.next());
                    gene.setName(scanner.next());
                    String sinonimo = scanner.next();
                    //this.adicionar(gene);
                    geneServiceExtend.buscarSimboloAdd(gene, sinonimo);
                    /*
                    if(sinonimo != null){
                        Gene gSinonimo = new Gene();
                        gSinonimo.setNome(gene.getNome());
                        gSinonimo.setNovoGene(gene);
                        sinonimo = sinonimo.replaceAll(" ", "");
                        for(String simboloSinonimo : sinonimo.split(",")){
                            gSinonimo.setSimbolo(simboloSinonimo);
                            geneServiceExtend.buscarSimboloAdd(gSinonimo);
                        }
                    }*/

                    System.out.println("Gene: " + gene.toString());
                    //System.out.println("0---> " + scanner.next());
                    //System.out.println("1---> " + scanner.next());
                }

                /*
                File file = new File("/tmp/analise/" + "gene" + ".txt");
                OutputStream out = new FileOutputStream(file);
                out.write(contents);
                out.close();
                System.out.println("Conteudo ---> " + new String(contents));
                 */
            } catch (Exception ex) {
                System.out.println("Erro: " + ex.getMessage());
            }
        }
    }

    public Gene buscarNovoNome(String nome) {
        Gene gene = null;
        List<Gene> genes = this.buscarNome(nome);
        if (genes != null) {
            if (genes.size() > 0) {
                gene = genes.get(0);
            }
        }
        if (gene != null) {
            while (gene.getSynonymou() != null) {
                gene = this.buscarPorId(gene.getSynonymou().getId());
                //gene = this.buscarNome(nome).get(0);
            }
        }
        return gene;
    }

    public Gene buscarNovoSimbolo(String simbolo) {
        Gene gene = null;
        gene = this.buscarSimbolo(simbolo);
        if (gene != null) {
            while (gene.getSynonymou() != null) {
                gene = this.buscarPorId(gene.getSynonymou().getId());
            }
        }
        return gene;
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    void adicionar2(Gene g) {
        this.getDao().persist(g);
    }

    private Gene buscarSimbolo(String simbolo) {
        return getDao().buscarSimbolo(simbolo);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Gene buscarAddSimbolo(String simbolo, Gene mainGene) {
        Gene gene = getDao().buscarSimbolo(simbolo);
        if (gene == null) {
            gene = new Gene();
            gene.setSymbol(simbolo);
            gene.setSynonymou(mainGene);
            getDao().persist(gene);
        }
        return gene;
    }

    public List<Gene> buscarLikeSimbolo(String simbolo) {
        return getDao().buscarLikeSimbolo(simbolo);
    }

    public List<Gene> buscarAnalise(Long analiseId) {
        return getDao().buscarAnalise(analiseId);
    }

    @Transactional
    public void upload(byte[] contents) {
        if (contents.length > 0) {
            Runnable r = () -> {
                this.importar(contents);
            };
            Thread t = new Thread(r);
            t.start();
        }
    }

    //@Transactional
    public void upload(byte[] contents, Long idBd) {
        if (contents.length > 0) {
            Runnable r = () -> {
                this.importar(contents, idBd);
            };
            Thread t = new Thread(r);
            t.start();
        }
    }

    //@Transactional(Transactional.TxType.REQUIRES_NEW)
    //@Transactional
    public void importar(byte[] contents, Long idBd) {
        CSVReader csv = new CSVReader(contents);
        //DbBio db = dbBioService.findById(idBd);
        for (Line ln : csv.getFile()) {
            if (ln.getSize() >= 1) {
                Gene g = new Gene();
                //g.setDbbio(db);
                g.setName(ln.getField(0));
                if (ln.getSize() >= 2) {
                    g.setSymbol(ln.getField(1));
                }
//                Gene master = this.findMasterBySymbol(g.getSymbol());
//                if(master!= null){
//                    g.setSynonymou(master);
//                }
                //g.setSynonymou(this.findMasterBySymbol(g.getSymbol()));
                this.persiste(g);
            }
        }
    }
    
    //@Override
    @Transactional
    public void persiste2(Gene g){
        //dbBioService.merge(g.getDbbio());
        getDao().persist(g);
    }
    

//    public void importar_old(byte[] contents, Long idBd) {
//        if (contents.length > 0) {
//            try {
//                DbBio dbBio = dbBioService.findById(idBd);
//
//                Scanner scanner = new Scanner(new String(contents))
//                        .useDelimiter("\t|\\n");
//                while (scanner.hasNext()) {
//                    Gene gene = new Gene();
//                    gene.setDbbio(dbBio);
//                    gene.setSymbol(scanner.next());
//                    gene.setName(scanner.next());
//                    String sinonimo = scanner.next();
//                    //this.adicionar(gene);
//                    geneServiceExtend.buscarSimboloAdd(gene, sinonimo);
//                }
//            } catch (Exception ex) {
//            }
//        }
//    }

    private Gene findMasterBySymbol(String symbol) {
        List<Gene> genes = getDao().findByProperty("symbol", symbol);
        if(genes.isEmpty()){
            return null;
        }else{
            Gene g = genes.get(0);
            while(g.getSynonymou() != null){
                g = g.getSynonymou();
            }
            return g;
        }
    }

}
