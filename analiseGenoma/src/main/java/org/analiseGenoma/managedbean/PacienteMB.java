package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import org.analiseGenoma.managedbean.util.RequestParam;
import org.analiseGenoma.model.Etnia;
import org.analiseGenoma.model.Paciente;
import org.analiseGenoma.model.Vcf;
import org.analiseGenoma.service.EtniaService;
import org.analiseGenoma.service.PacienteService;
import org.analiseGenoma.service.VcfService;

@Named(value = "pacienteMB")
@RequestScoped
public class PacienteMB implements Serializable {

    @Inject
    private FacesContext context;
    @Inject
    private PacienteService pacienteService;
    @Inject
    private VcfService vcfService;
    private Paciente paciente;
    private long idEtnia;
    private String nome;
    @Inject
    private EtniaService etniaService;
    private List<Paciente> pacientes;
    @Inject
    @RequestParam
    private String id;
    private String gender;

    private List<Vcf> vcfs;

    @PostConstruct
    public void init() {
        paciente = new Paciente();
        pacientes = pacienteService.buscar();

        if (id != null) {
            if (!id.equals("")) {
                System.out.println("Pesquisando paciente com o o id " + id);
                paciente = pacienteService.buscarId(Long.valueOf(id));
                if (paciente != null) {
                    if (paciente.getEtnia() != null) {
                        idEtnia = paciente.getEtnia().getId();
                    }
                    vcfs = vcfService.buscarPacienteId(paciente.getId());
                }
            }
        }
    }

    public PacienteMB() {
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String adicionar() {
        paciente.setEtnia(etniaService.buscarPorId(idEtnia));
        paciente.setGender(gender.charAt(0));
        pacienteService.adicionar(paciente);
        this.limpar();
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Cadastrado com sucesso"));
        return "paciente_novo.xhtml?faces-redirect=true";
    }

    public void limpar() {
        paciente = new Paciente();
    }

    public List<SelectItem> getSelectEtnias() {
        List<SelectItem> etnias = new ArrayList<SelectItem>();
        for (Etnia e : etniaService.buscar()) {
            etnias.add(new SelectItem(e.getId(), e.getSigla() + " - " + e.getNome()));
        }
        return etnias;
    }

    public long getIdEtnia() {
        return idEtnia;
    }

    public void setIdEtnia(long idEtnia) {
        this.idEtnia = idEtnia;
    }

    public void pesquisar() {
        System.out.println("Pesquisando...." + nome);
        pacientes = pacienteService.buscarNome(nome);
        System.out.println("Tamanho: " + pacientes.size());
        //nome = nome + " - " + pacientes.size();
        //return "paciente_pesquisar.xhtml";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String detalhe(Paciente paciente) {
        this.paciente = paciente;
        return "paciente_detalhe.xhtml";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void salvar() {
        System.out.println("-----> " + paciente.toString());
        paciente.setEtnia(etniaService.buscarPorId(idEtnia));
        pacienteService.atualizar(paciente);
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Atualizado com sucesso"));
        //return "paciente_pesquisar.xhtml?faces-redirect=true";
    }

    //stuf
    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public List<Vcf> getVcfs() {
        return vcfs;
    }

    public void setVcfs(List<Vcf> vcfs) {
        this.vcfs = vcfs;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
    

}
