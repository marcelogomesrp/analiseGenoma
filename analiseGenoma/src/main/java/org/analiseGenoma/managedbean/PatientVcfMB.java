package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
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
        System.out.println("iniciado");
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
//            vcfService.importar(uploadedFile.getContents(), vcf);
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
