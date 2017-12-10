package org.analiseGenoma.service;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.FiltroDao;
import org.analiseGenoma.dao.VarianteDao;
import org.analiseGenoma.dao.VcfDao;
import org.analiseGenoma.model.Filtro;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.Impact;
import org.analiseGenoma.model.Variante;
import org.analiseGenoma.model.Vcf;
import org.analiseGenoma.model.VcfStatus;

@Named
public class VcfService implements Serializable {

    @Inject
    private VcfDao vcfDao;
    @Inject
    private VarianteDao varianteDao;
    @Inject
    private GeneService geneService;
    @Inject
    private CromossomoDao cromossomoDao;
    @Inject
    private CromossomoService cromossomoService;

    @Inject
    private ImpactoService impactoService;
    @Inject
    private VarianteService varianteService;
    @Inject
    private FiltroDao filtroDao;
    
    @Inject private UmdPredictorService umdPredictorService;
    @Inject private ZygosityService zygosityService;
    @Inject private FilterService filterService;

    @PostConstruct
    public void init() {
    }

    @Transactional
    public void adicionar(Vcf vcf) {
        vcf.setStatus(VcfStatus.importando);
        vcf.setDataImportacao(new Date());
        vcfDao.adicionar(vcf);
    }

    @Transactional
    public void atualizar(Vcf vcf) {
        vcfDao.atualizar(vcf);
    }

    public List<Vcf> buscar() {
        return vcfDao.buscar();
    }

    public Vcf buscarId(Long id) {
        return vcfDao.buscarPorId(id);
    }

    public List<Vcf> buscarPacienteId(Long id) {
        return vcfDao.buscarPacienteId(id);
    }

    public List<Variante> buscarVariante(Long idAnalise) {        
        return varianteDao.buscarAnalise(idAnalise);
    }
    
    public List<Variante> buscarVariante(Long idAnalise, Long idFiltro){
       // acertar a busca
       Filtro filtro = filtroDao.buscarPorId(idFiltro);
       
       return varianteDao.buscarAnalise(idAnalise);
    }

    
    public void importar(byte[] contents, Vcf vcf) {
        System.out.println("importar :D");
        //Stream<String> lines = Files.lines(tempFile.toPath(), StandardCharsets.UTF_8);
        String[] arquivo = new String(contents, StandardCharsets.UTF_8).split("\n");
        //System.out.println("--> " + str);
        Variante variante = null;
        for(String ln:arquivo){
            variante = new Variante();
            String[] linha = ln.split("\t");
            System.out.println("---------> " + variante.toString());            
            variante.setCromossomo(cromossomoService.buscarOuCriar(linha[0]));
            variante.setPosition(linha[1]);
            String[] referenceAlternate = linha[2].split(">");            
            variante.setReferencia(referenceAlternate[0]);
            variante.setAlterado(referenceAlternate[1]);
            Gene gene = geneService.buscarNovoSimbolo(linha[3]);
            if(gene == null){
                gene = new Gene();
                gene.setSimbolo(linha[3]);
                gene.setNome(linha[4]);
                gene.setNovoGene(null);
                geneService.adicionar(gene);
            }
            variante.setGene(gene);
            variante.setUmdPredictor(umdPredictorService.findOrCreate(linha[5]));
            variante.setZygosity(zygosityService.findOrCreate(linha[6]));
            variante.setAllelicDeph(linha[7]);
            variante.setFilter(filterService.findOrCreate(linha[8]));
            varianteService.adicionar(variante);
        }
        //vcf.setStatus(VcfStatus.importado);
        //this.atualizar(vcf);
    }
  /*  
public void importarDisabled(byte[] contents, Vcf vcf) {
        System.out.println("importar :D");
        //Stream<String> lines = Files.lines(tempFile.toPath(), StandardCharsets.UTF_8);
        String[] arquivo = new String(contents, StandardCharsets.UTF_8).split("\n");
        //System.out.println("--> " + str);
        Variante variante = null;
        for(String ln:arquivo){
            variante = new Variante();
            //System.out.println("---------> " + ln);
            String[] linha = ln.split("\t");
            //Cromossomo cromossomo = cromossomoDao.buscarOuCriar(linha[0]);
            Cromossomo cromossomo = cromossomoService.buscarOuCriar(linha[0]);
            variante.setCromossomo(cromossomo);
            variante.setPosition(linha[1]);
            Gene gene = geneService.buscarNovoSimbolo(linha[2]);
            if(gene == null){
                gene = new Gene();
                gene.setSimbolo(linha[2]);
                gene.setNome(linha[3]);
                gene.setNovoGene(null);
                geneService.adicionar(gene);
            }
            variante.setGene(gene);
            String[] referenceAlternate = linha[5].split(">");            
            variante.setReferencia(referenceAlternate[0]);
            variante.setAlterado(referenceAlternate[1]);
//            variante.setFiltro(linha[6]);
            variante.setIdSNP(linha[9]);
            Impact impacto;
            impacto = impactoService.buscarPorNome(linha[14]);
            if(impacto == null){
                impacto = new Impact();
                //impacto.setNome(linha[14]);
                impactoService.adicionar(impacto);
            }            
            variante.setImpact(impacto);
            variante.setVcf(vcf);
            System.out.println("---------> " + variante.toString());
            varianteService.adicionar(variante);
        }
    }
    
*/
    
    
    
    /*
    public void importar(byte[] contents, Vcf vcf) {
        System.out.println("O Importar esta indo...");
        PrintWriter writer = null;
        File tempFile = null;
        try {
            tempFile = File.createTempFile("tempfile-old", ".tmp");
            writer = new PrintWriter(new FileWriter(tempFile));
            writer.println(new String(contents));
        } catch (IOException ex) {
            System.out.println("Ops erro ao gravar o arquivo ");
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
        
        try {
            Stream<String> lines = Files.lines(tempFile.toPath(), StandardCharsets.UTF_8);
            for (String line : (Iterable<String>) lines::iterator) {
                if (!line.startsWith("#")) {
                    System.out.println("Linha:::::" + line);
                    String[] linha = line.split("\t");
                    Variante variante = new Variante();
                    //variante = new Variante();
                    variante.setVcf(vcf);
                    //variante.setCromossomo(String.valueOf(linha.length));
                    //#CHROM	POS	ID	REF	ALT	QUAL	FILTER	INFO	FORMAT	3823
                    if(linha.length > 1){
                        Cromossomo cromossomo = cromossomoDao.buscarOuCriar(linha[0]);
                        variante.setCromossomo(cromossomo);
                    }
                    if(linha.length > 2)
                        variante.setPosition(linha[1]);
                    if(linha.length > 3)
                        variante.setIdSNP(linha[2]);
                    if(linha.length > 4)
                        variante.setReferencia(linha[3]);
                    if(linha.length > 5)
                        variante.setAlterado(linha[4]);
                    if(linha.length > 6)
                        variante.setQualidade(linha[5]);
                    if(linha.length > 7)
                        variante.setFiltro(linha[6]);
                    
                    if(linha.length > 8){
                        String[] info = linha[7].split("\\|");
                        
                        if (info.length > 3) {                        
                            Impacto impacto = new Impacto();
                            impacto.setNome(info[2]);                            
                            variante.setImpacto(impactoService.getImpacto(impacto));
                        }
                        
                    
                    if (info.length > 4) {                       
                        Gene gene = geneService.buscarNovoSimbolo(info[3]);
                        if (gene == null) {
                            gene = new Gene();
                            gene.setNome(info[3]);
                            gene.setSimbolo(info[3]);
                            geneService.adicionar(gene);
                        }
                        variante.setGene(gene);
                    }
                    }
                    System.out.println("Variante que seria inserida:");
                    System.out.println("-->" + variante.toString());
                    
                    //varianteDao.adicionar(variante);
                    //coloquei este teste pq estava inserindo uma linha com tudo null
                    if(linha.length > 2)
                        varianteService.adicionar(variante);
                    
                }
            }
        } catch (Exception ex) {
            System.out.println("Ops erro ao fazer parser: " + ex.getMessage() + " -->" + ex.getCause().toString());
        }
    }
*/

    @Transactional
    public void importar2(byte[] contents, Vcf vcf) {
        if (contents.length > 0) {
            try {
                Scanner scanner = new Scanner(new String(contents))
                        .useDelimiter("\t|\\n");
                while (scanner.hasNext()) {
                    //1CHROM	2POS	3ID	4REF	5ALT	6QUAL	7FILTER	8INFO	9FORMAT	10 4125
                    Variante linha = new Variante();
                    linha.setVcf(vcf);
                    //comentando na mudanca do cromossomo
                    //linha.setCromossomo(scanner.next());
                    linha.setPosition(scanner.next());
                    linha.setIdSNP(scanner.next());
                    linha.setReferencia(scanner.next());
                    linha.setAlterado(scanner.next());
                    linha.setQualidade(scanner.next());
                    //linha.setFiltro(scanner.next());
                    //if(!linha.getCromossomo().startsWith("#")) {
                    String[] info = scanner.next().split("\\|");
                    if (info.length > 3) {
                        //linha.setImpacto(info[2]);
                        Impact impacto = new Impact();
//                        impacto.setNome(info[2]);
                        linha.setImpact(impactoService.getImpacto(impacto));
                    }
                    if (info.length > 4) {
                        //Gene gene = geneService.buscarNovoNome(info[3]);
                        Gene gene = geneService.buscarNovoSimbolo(info[3]);
                        if (gene == null) {
                            gene = new Gene();
                            gene.setNome(info[3]);
                            gene.setSimbolo(info[3]);
                            geneService.adicionar(gene);
                        }
                        linha.setGene(gene);
                    }
                    System.out.println("->format:" + scanner.next());
                    System.out.println("->paciente:" + scanner.next());

                    varianteDao.adicionar(linha);
                    //}
                }
            } catch (Exception ex) {
                System.out.println("Erro: " + ex.getMessage());
            }
        }

    }

    public List<Variante> buscarVariante(Long idAnalise, Filtro filtro) {
        return varianteDao.buscarAnalise(idAnalise, filtro);
    }

    

}
