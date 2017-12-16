package org.analiseGenoma.service;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.PacienteDao;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.Paciente;
import org.analiseGenoma.model.Variante;
import org.analiseGenoma.model.Vcf;

@Named
public class PacienteService implements Serializable {

    @Inject
    private PacienteDao pacienteDao;
    @Inject
    private UmdPredictorService umdPredictorService;
    @Inject
    private ZygosityService zygosityService;
    @Inject
    private FilterService filterService;

    @Inject
    private GeneService geneService;
    @Inject
    private CromossomoService cromossomoService;
    @Inject
    private ImpactoService impactoService;
    @Inject
    private VarianteService varianteService;

    @PostConstruct
    public void init() {
    }

    @Transactional
    public void adicionar(Paciente paciente) {
        pacienteDao.adicionar(paciente);
    }

    public List<Paciente> buscar() {
        return pacienteDao.buscar();
    }

    public List<Paciente> buscarNome(String nome) {
        return pacienteDao.buscarNome(nome);
    }

    public Paciente buscarId(Long id) {
        return pacienteDao.buscarPorId(id);
    }

    @Transactional
    public void atualizar(Paciente paciente) {
        pacienteDao.atualizar(paciente);
    }

    public void uploadVcf(byte[] contents) {

    }

    public void uploadVcf(byte[] contents, Paciente paciente) {

    }

    public void importVcf(byte[] contents, Vcf vcf) {
        String[] arquivo = new String(contents, StandardCharsets.UTF_8)
                .split("\n");
        Variante variante = null;
        for (String ln : arquivo) {
            variante = new Variante();
            String[] linha = ln.split("\t");
            System.out.println("---------> " + variante.toString());
            variante.setCromossomo(cromossomoService.buscarOuCriar(linha[0]));
            variante.setPosition(linha[1]);
            String[] referenceAlternate = linha[2].split(">");
            variante.setReferencia(referenceAlternate[0]);
            variante.setAlterado(referenceAlternate[1]);
            Gene gene = geneService.buscarNovoSimbolo(linha[3]);
            if (gene == null) {
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
    }

    public List<Paciente> findMenByName(String query) {
        Paciente p = new Paciente();
        p.setNome(query);
        p.setGender('m');
        return pacienteDao.findByExample(p);
    }
    
    public List<Paciente> findWomansByName(String query) {
        Paciente p = new Paciente();
        p.setNome(query);
        p.setGender('f');
        return pacienteDao.findByExample(p);
    }
}
