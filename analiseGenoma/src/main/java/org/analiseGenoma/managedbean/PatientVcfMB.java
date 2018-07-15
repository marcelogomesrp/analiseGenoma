package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.managedbean.util.RequestParam;
import org.analiseGenoma.model.Patient;
import org.analiseGenoma.model.Vcf;
import org.analiseGenoma.model.VcfStatus;
import org.analiseGenoma.service.PatientService;
import org.analiseGenoma.service.VcfService;
import org.analiseGenoma.sessionbean.PatientSB;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

@Named(value = "pacienteVcfMB")
@RequestScoped
public class PatientVcfMB implements Serializable {
    @Inject
    private FacesContext context;
    @Inject
    private PatientService pacienteService;
    @Inject
    private VcfService vcfService;
    private UploadedFile uploadedFile;
    @Inject
    private PatientSB patientSB;
    private Vcf vcf;
    @Inject
    @RequestParam
    private String id;

    @PostConstruct
    public void init() {
        System.out.println("PatientVcfMB.init");
//        patient = new Patient();
//        vcf = new Vcf();
//        if (id != null) {
//            if (!id.equals("")) {
//                patient = pacienteService.buscarId(Long.valueOf(id));
//            }
//        }
    }

//    public Patient getPaciente() {
//        if (patient == null) {
//            patient = new Patient();
//        }
//        return patient;
//    }

//    public void setPaciente(Patient paciente) {
//        this.patient = paciente;
//    }

    public Vcf getVcf() {
        System.out.println("PatientVcfMB.getVcf");
        if (vcf == null) {
            vcf = new Vcf();
        }
        return vcf;
    }

    public void setVcf(Vcf vcf) {
        this.vcf = vcf;
    }

    public PatientSB getPatientSB() {
        return patientSB;
    }

    public void setPatientSB(PatientSB patientSB) {
        this.patientSB = patientSB;
    }

    
    
    @Transactional
    public void adicionar() {
        String msg = "it's done successfully";
        System.out.println("Adicionando o vcf");
        //vcf.setPaciente(pacienteService.buscarId(patient.getId()));       
        vcf.setPaciente(patientSB.getPatient());
        vcf.setStatus(VcfStatus.importando);
        vcfService.adicionar(vcf);
        if (uploadedFile != null) {
            System.out.println("Fancendo o upload :D");
            //aqui marcelo
            //vcfService.importar(uploadedFile.getContents(), vcf);
            Runnable r = () -> {
                vcfService.importar(uploadedFile.getContents(), vcf);
            };
            Thread t = new Thread(r);
            t.start();
        } else {
            System.out.println("Marcelo: " + "Opa nao veio nada no upload");
        }        
        RequestContext.getCurrentInstance().closeDialog(msg);  
    }
    
    public void adicionarLote(){
        /*
        vcf.setPaciente(patientSB.getPatient());
        vcf.setStatus(VcfStatus.importando);
        vcfService.adicionar(vcf);*/
        String arquivo = new String(uploadedFile.getContents(), StandardCharsets.UTF_8);
        
        System.out.println("inicio");
        try {
            JobOperator jobOperator = BatchRuntime.getJobOperator();
            Properties props = new Properties();
            
           props.setProperty("nome", "nome_passado");
           props.setProperty("pacientId", patientSB.getPatient().getId().toString());
           //props.setProperty("arquivo", "1	701779	GAATA>G	RP11-206L10.5		-	Heterozygous	17 / 49	PASS	n.-1478_-1475delTATT	-	rs201234755	-	lincRNA	upstream_gene_variant	MODIFIER		-	-			-	-	-	-	-	-	-	transcript	ENST00000417659	-	-	-	0/1	66	0.500	1968.73	-	no	no	no	no	-	-	-	-	-");
           props.setProperty("arquivo", arquivo);
            System.out.println(jobOperator.start("meuJob", props));

        } catch (Exception ex) {
            System.out.println("Erro no vai2: " + ex.getMessage());
        }
        System.out.println("fim");
        
    }
    
    public void cancelar(){
        String msg = "";
        RequestContext.getCurrentInstance().closeDialog(msg); 
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
    
    public void viewAddVcf(Long id) {
        //patient = pacienteService.buscarId(id);
        vcf = new Vcf();
        Map<String, Object> options = new HashMap<>();
        options.put("modal", true);
        options.put("width", 400);
        options.put("height", 300);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");

        options.put("resizable", false);
        RequestContext.getCurrentInstance().openDialog("viewAddVcf", options, null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    

}
