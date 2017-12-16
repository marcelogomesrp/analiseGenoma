package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.inject.Named;
//import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
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
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

@Named(value = "pacienteMB")
@ViewScoped
//@RequestScoped
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
    private UploadedFile vcfUploadedFile;
    private Vcf vcf;
    private String father;
    private String mother;

    private List<Vcf> vcfs;

    @PostConstruct
    public void init() {
        System.out.println("Pagina pacienteMB instanciada novamente");
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
        if (father != null) {
            if (!father.isEmpty()) {
                List<Paciente> list = pacienteService.findMenByName(father);
                if (list.size() == 1) {
                    paciente.setFather(list.get(0));
                }
            }
        }
        if (mother != null) {
            if (!mother.isEmpty()) {
                List<Paciente> list = pacienteService.findMenByName(mother);
                if (list.size() == 1) {
                    paciente.setMother(list.get(0));
                }
            }
        }
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

    public void viewAddVcf(Long id) {
        paciente = pacienteService.buscarId(id);
        vcf = new Vcf();
        Map<String, Object> options = new HashMap<>();
        options.put("modal", true);
        options.put("width", 800);
        options.put("height", 600);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        options.put("resizable", false);

        Map<String, List<String>> params = new HashMap<String, List<String>>();
        List<String> values = new ArrayList<String>();
        values.add(id.toString());
        params.put("id", values);
        RequestContext.getCurrentInstance().openDialog("viewAddVcf", options, params);
        paciente = new Paciente();
    }

    public void onViewAddVcf(SelectEvent event) {
        String msg = (String) event.getObject();
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(msg));

    }

    public void uploadVcf() {
        String msg = "Erro ao realizar o upload";
        if (vcfUploadedFile != null) {
            Runnable r = () -> {
                pacienteService.importVcf(vcfUploadedFile.getContents(), vcf);
            };
            Thread t = new Thread(r);
            t.start();
        } else {
            msg = "Erro ao importar o arquivo";
        }
        RequestContext.getCurrentInstance().closeDialog(msg);
    }

    public UploadedFile getVcfUploadedFile() {
        return vcfUploadedFile;
    }

    public void setVcfUploadedFile(UploadedFile vcfUploadedFile) {
        this.vcfUploadedFile = vcfUploadedFile;
    }

    public Vcf getVcf() {
        return vcf;
    }

    public void setVcf(Vcf vcf) {
        this.vcf = vcf;
    }

    public int qtdVcf(Paciente p) {
        return vcfService.buscarPacienteId(p.getId()).size();
    }

    public List<Vcf> vcfs(Paciente p) {
        return vcfService.buscarPacienteId(p.getId());
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public List<String> fatherComplete(String query) {
//        return pacienteService.findFatherByName(query).forEach(p -> results.add(p.getNome()));                                
        return pacienteService.findMenByName(query + "%")
                .stream()
                .map(p -> p.getNome())
                .collect(Collectors.toList());
    }

    public List<String> motherComplete(String query) {
        return pacienteService.findWomansByName(query + "%")
                .stream()
                .map(p -> p.getNome())
                .collect(Collectors.toList());
    }

}
