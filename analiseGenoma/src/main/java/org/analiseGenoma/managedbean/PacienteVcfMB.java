package org.analiseGenoma.managedbean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.managedbean.util.RequestParam;
import org.analiseGenoma.model.Paciente;
import org.analiseGenoma.model.Vcf;
import org.analiseGenoma.service.PacienteService;
import org.analiseGenoma.service.VcfService;
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

    public String adicionar() {
        System.out.println("Adicionando o vcf");
        vcf.setPaciente(pacienteService.buscarId(paciente.getId()));        
        vcfService.adicionar(vcf);
        if (uploadedFile != null) {
            System.out.println("Fancendo o upload :D");
            Runnable r = () -> {
                vcfService.importar(uploadedFile.getContents(), vcf);
            };
            Thread t = new Thread(r);
            t.start();

            //vcfService.importar(uploadedFile.getContents(), vcf);
        } else {
            System.out.println("Marcelo: " + "Opa nao veio nada no upload");
        }

        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Cadastrado com sucesso"));
        return "paciente_pesquisar.xhtml?faces-redirect=true";

    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

}
