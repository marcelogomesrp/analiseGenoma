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
import org.analiseGenoma.model.Paciente;
import org.analiseGenoma.model.Vcf;
import org.analiseGenoma.model.VcfStatus;
import org.analiseGenoma.service.PacienteService;
import org.analiseGenoma.service.VcfService;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

@Named(value = "pacienteVcfMB")
@RequestScoped
public class PacienteVcfMB implements Serializable {

    @Inject
    private FacesContext context;
    @Inject
    private PacienteService pacienteService;
    @Inject
    private VcfService vcfService;
    private UploadedFile uploadedFile;
    private Paciente paciente;
    private Vcf vcf;
    @Inject
    @RequestParam
    private String id;

    @PostConstruct
    public void init() {
        paciente = new Paciente();
        vcf = new Vcf();
        if (id != null) {
            if (!id.equals("")) {
                paciente = pacienteService.buscarId(Long.valueOf(id));
            }
        }
    }

    public Paciente getPaciente() {
        if (paciente == null) {
            paciente = new Paciente();
        }
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Vcf getVcf() {
        if (vcf == null) {
            vcf = new Vcf();
        }
        return vcf;
    }

    public void setVcf(Vcf vcf) {
        this.vcf = vcf;
    }

    
    @Transactional
    public void adicionar() {
        String msg = "arquivo adicionado com sucesso";
        System.out.println("Adicionando o vcf");
        vcf.setPaciente(pacienteService.buscarId(paciente.getId()));       
        vcf.setStatus(VcfStatus.importando);
        vcfService.adicionar(vcf);
        if (uploadedFile != null) {
            System.out.println("Fancendo o upload :D");
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
        paciente = pacienteService.buscarId(id);
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
