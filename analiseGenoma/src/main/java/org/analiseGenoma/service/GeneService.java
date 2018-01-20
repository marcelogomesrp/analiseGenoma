package org.analiseGenoma.service;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.DbBio;
import org.analiseGenoma.model.Gene;

public class GeneService implements Serializable {

    @Inject
    private GeneDao geneDao;
    @Inject
    private GeneServiceExtend geneServiceExtend;
    @Inject
    private BancoBiologicoService bdBioService;

    @Transactional
    public void adicionar(Gene gene) {
        geneDao.persist(gene);
    }

    @Transactional
    public void atualizar(Gene gene) {
        geneDao.merge(gene);
    }

    public List<Gene> buscar() {
        return geneDao.find();
    }

    public Gene buscarPorId(Long id) {
        return geneDao.findById(id);
    }

    public List<Gene> buscarNome(String nome) {
        return geneDao.buscarLikeNome(nome);
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
        this.geneDao.persist(g);
    }

    private Gene buscarSimbolo(String simbolo) {
        return geneDao.buscarSimbolo(simbolo);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Gene buscarAddSimbolo(String simbolo, Gene mainGene) {
        Gene gene = geneDao.buscarSimbolo(simbolo);
        if (gene == null) {
            gene = new Gene();
            gene.setSymbol(simbolo);
            gene.setSynonymou(mainGene);
            geneDao.persist(gene);
        }
        return gene;
    }

    public List<Gene> buscarLikeSimbolo(String simbolo) {
        return geneDao.buscarLikeSimbolo(simbolo);
    }

    public List<Gene> buscarAnalise(Long analiseId) {
        return geneDao.buscarAnalise(analiseId);
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

    @Transactional
    public void upload(byte[] contents, Long idBd) {
        if (contents.length > 0) {
            Runnable r = () -> {
                this.importar(contents, idBd);
            };
            Thread t = new Thread(r);
            t.start();
        }
    }

    public void importar(byte[] contents, Long idBd) {
        if (contents.length > 0) {
            try {
                DbBio dbBio = bdBioService.buscarPorId(idBd);
                
                Scanner scanner = new Scanner(new String(contents))
                        .useDelimiter("\t|\\n");
                while (scanner.hasNext()) {
                    Gene gene = new Gene();
                    gene.setDbbio(dbBio);
                    gene.setSymbol(scanner.next());
                    gene.setName(scanner.next());
                    String sinonimo = scanner.next();
                    //this.adicionar(gene);
                    geneServiceExtend.buscarSimboloAdd(gene, sinonimo);
                }
            }catch(Exception ex){}
        }
    }

}
