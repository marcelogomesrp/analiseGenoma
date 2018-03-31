package org.analiseGenoma.service;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.PatientDao;
import org.analiseGenoma.model.Patient;
import org.analiseGenoma.model.Variante;
import org.analiseGenoma.model.Vcf;

@Named
public class PatientService extends Service<Patient> implements Serializable {

        
    public PatientService() {
        super(Patient.class);
    }

    private PatientDao getDao() {
        return ((PatientDao) dao);
    }
    
    @Inject
    private PatientDao pacienteDao;
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
    public void adicionar(Patient paciente) {
        pacienteDao.persist(paciente);
    }

    public List<Patient> buscar() {
        return pacienteDao.find();
    }

    
    public List<Patient> buscarNome(String nome) {
        return pacienteDao.buscarNome(nome);
    }

    public Patient buscarId(Long id) {
        return pacienteDao.findById(id);
    }

    @Transactional
    public void atualizar(Patient paciente) {
        pacienteDao.merge(paciente);
    }

    public void uploadVcf(byte[] contents) {

    }

    public void uploadVcf(byte[] contents, Patient paciente) {

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
//            Gene gene = geneService.buscarNovoSimbolo(linha[3]);
//            if (gene == null) {
//                gene = new Gene();
//                gene.setSymbol(linha[3]);
//                gene.setName(linha[4]);
////                gene.setSynonymou(null);
//                geneService.adicionar(gene);
//            }
//            variante.setGene(gene);
            variante.setUmdPredictor(umdPredictorService.findOrCreate(linha[5]));
            variante.setZygosity(zygosityService.findOrCreate(linha[6]));
            variante.setAllelicDeph(linha[7]);
            variante.setFilter(filterService.findOrCreate(linha[8]));
            varianteService.persiste(variante);
        }
    }

    public List<Patient> findMenByName(String query) {
        Patient p = new Patient();
        p.setName(query);
        p.setGender('m');
        List<Patient> pais = pacienteDao.findByExample(p);
        return pais;
    }
    
    public List<Patient> findWomansByName(String query) {
        Patient p = new Patient();
        p.setName(query);
        p.setGender('f');
        return pacienteDao.findByExample(p);
    }

    public List<Patient> findByExample(Patient paciente) {
        return pacienteDao.findByExample(paciente);
    }

    public Patient findById(Long id) {
        return pacienteDao.findById(id);
    }

//    @Override
//    public void persiste(Patient patient){
//        Patient p = this.FindByNameAndBirth(patient);
//        if(p == null){
//            super.persiste(patient); 
//        }
//    }

    public Patient FindByNameAndBirth(Patient patient) {
        Patient p = new Patient();
        p.setName(patient.getName());
        p.setBirth(p.getBirth());
        return this.getFirstOrNull(this.findByExample(p));
    }
    
    
    
}
