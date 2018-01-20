package org.analiseGenoma.service;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.ejb.Asynchronous;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.DbBioDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.dao.InformacaoBiologicaDao;
import org.analiseGenoma.dao.PatologiaDao;
import org.analiseGenoma.model.DbBio;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.InformacaoBiologica;
import org.analiseGenoma.model.Disease;

public class InformacaoBiologicaService implements Serializable {

    @Inject
    private InformacaoBiologicaDao informacaoBiologicaDao;
    @Inject
    private PatologiaDao patologiaDao;
    @Inject
    private GeneService geneService;
    @Inject
    private DbBioDao bancoBiologicoDao;

    @Inject
    private InformacaoBiologicaServiceExtend infoBioServiceExtend;

    @Inject
    private PatologiaService patologiaService;

    @Transactional
    public void adicionar(InformacaoBiologica informacaoBiologica) {
        informacaoBiologicaDao.persist(informacaoBiologica);
    }

    @Transactional
    public void atualizar(InformacaoBiologica informacaoBiologica) {
        informacaoBiologicaDao.merge(informacaoBiologica);
    }

    public List<InformacaoBiologica> buscar() {
        return informacaoBiologicaDao.find();
    }

    public InformacaoBiologica buscarPorId(Long id) {
        return informacaoBiologicaDao.findById(id);
    }
    
    public InformacaoBiologica buscaBdGene(Long bdBioId, Long geneId){
        return informacaoBiologicaDao.buscarPorId(bdBioId, geneId);
    }

    //@Asynchronous 
    @Transactional
    //@Asynchronous
    public void importar(String idBd, byte[] contents) {
        if (contents.length > 0) {
            try {
                Runnable r = () -> {
                    DbBio bdBio = bancoBiologicoDao.findById(new Long(idBd));
                    Scanner scanner = new Scanner(new String(contents))
                            .useDelimiter("\t|\\n");
                    while (scanner.hasNext()) {

                        String patologia = scanner.next();
                        String cid = scanner.next();
                        String genes = scanner.next();
                        String outros = scanner.next();

                        Disease p = patologiaService.buscarAddNome(patologia);

                        String[] listGenes = genes.replaceAll(" ", "").split(",");
                        Gene mainGene = null;
                        for (String geneSimbolo : listGenes) {
                            Gene g = geneService.buscarAddSimbolo(geneSimbolo, mainGene);
                            if (mainGene == null) {
                                mainGene = g;
                            }

                            InformacaoBiologica info = new InformacaoBiologica();
                            info.setBdBio(bdBio);
                            info.setPatologia(p);
                            info.setGene(g);
                            info.setUtil(outros);
                            infoBioServiceExtend.BuscarAdd(info);
                            //informacaoBiologicaDao.adicionar(info);
                            //https://emmanuelneri.com.br/2016/04/03/abrindo-novas-transacoes-dentro-de-metodos-transacionais/
                            //aqui marcelo
                            //erro
                        }

                    }
                };
                Thread t = new Thread(r);
                t.start();

            } catch (Exception ex) {
                System.out.println("Erro: " + ex.getMessage());
            }
        }
    }

    private void adicionarPatologia(Disease p) {
        try {
            patologiaDao.persist(p);
        } catch (Exception ex) {
            System.out.println("Erro add: " + ex.getMessage());
        }
    }

    public List<Gene> buscarGenePorPatologia(Disease p) {
        return informacaoBiologicaDao.buscarGenePorPatologia(p);
    }

}

/*

    //@Asynchronous 
    @Transactional
    //@Asynchronous
    public void importar(String idBd, byte[] contents) {
        if (contents.length > 0) {
            try {
                BancoBiologico bdBio = bancoBiologicoDao.buscarPorId(new Long(idBd));
                Scanner scanner = new Scanner(new String(contents))
                        .useDelimiter("\t|\\n");
                while (scanner.hasNext()) {
                    
                    String patologia = scanner.next();
                    String cid = scanner.next();
                    String genes = scanner.next();
                    String outros = scanner.next();
                    
                    Patologia p = patologiaService.buscarAddNome(patologia);
                    String[] listGenes = genes.replaceAll(" ", "").split(",");                    
                    Gene mainGene = null;
                    for(String geneSimbolo:listGenes){
                        Gene g = geneService.buscarAddSimbolo(geneSimbolo, mainGene);
                        if(mainGene == null){
                            mainGene = g;
                        }
                        
                        InformacaoBiologica info = new InformacaoBiologica();
                        info.setBdBio(bdBio);
                        info.setPatologia(p);
                        info.setGene(g);
                        infoBioServiceExtend.BuscarAdd(info);
                        //informacaoBiologicaDao.adicionar(info);
                        //https://emmanuelneri.com.br/2016/04/03/abrindo-novas-transacoes-dentro-de-metodos-transacionais/
                        //aqui marcelo 
                        //erro
                    }
                    
                    //System.out.println("---");
                }
            } catch (Exception ex) {
                System.out.println("Erro: " + ex.getMessage());
            }
        }
    }
 */
