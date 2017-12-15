package org.analiseGenoma.service;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
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
    @Inject private TypeService typeService;
    @Inject private EffectService effectService;
    @Inject private ImpactService impactService;

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

    @Transactional
    public void importar(byte[] contents, Vcf vcf) {
        //este esta sendo usado
        String[] arquivo = new String(contents, StandardCharsets.UTF_8).split("\n");
        Variante variante = null;
        for(String ln:arquivo){
            variante = new Variante();
            variante.setVcf(vcf);
            String[] linha = ln.split("\t");
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
            variante.setHgvsC(linha[9]);
            variante.setHgvsP(linha[10]);
            variante.setIdSNP(linha[11]);
            variante.setExonIntron(linha[12]);
            variante.setType(typeService.findOrCreate(linha[13]));
            variante.setEffect(effectService.findOrCreate(linha[14]));
            variante.setImpact(impactService.findOrCreate(linha[15]));
            
            varianteService.adicionar(variante);
            
            
        }
        vcf.setStatus(VcfStatus.importado);
        this.atualizar(vcf);
    }
    
    public List<Variante> buscarVariante(Long idAnalise, Filtro filtro) {
        return varianteDao.buscarAnalise(idAnalise, filtro);
    }

    

}
