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
import javax.faces.component.UIComponent;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
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
    private boolean disabledValidation;
    private CrudMode crudMode;

    private List<Vcf> vcfs;

    @PostConstruct
    public void init() {
        this.defCrudModeRead();
        System.out.println("Pagina pacienteMB instanciada novamente");
        paciente = new Paciente();
        pacientes = pacienteService.buscar();

        if (id != null) {
            if (!id.equals("")) {
                this.defCrudModeUpdate();
                System.out.println("Pesquisando paciente com o o id " + id);
                paciente = pacienteService.buscarId(Long.valueOf(id));
                if (paciente != null) {
                    if (paciente.getEtnia() != null) {
                        if (paciente.getEtnia().getId() != null) {
                            idEtnia = paciente.getEtnia().getId();
                        }
                    }
                    if(paciente.getFather() != null){
                        father = paciente.getFather().getNome();
                    }
                    if(paciente.getMother() != null){
                        mother = paciente.getMother().getNome();
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
//        if (isValid()) {
//        context.addMessage("formulario:nome", new FacesMessage(FacesMessage.SEVERITY_ERROR, "error message", "error message"));
//        return "paciente_novo.xhtml";

//            try {
        if (father != null) {
            if (!father.isEmpty()) {
                List<Paciente> list = pacienteService.findMenByName(father);
                if (list.size() == 1) {
                    paciente.setFather(list.get(0));
                }else{
                    //da erro pois o nome do pai informado nao e valido
                }
            }
        }
        if (mother != null) {
            if (!mother.isEmpty()) {
                List<Paciente> list = pacienteService.findWomansByName(mother);
                if (list.size() == 1) {
                    paciente.setMother(list.get(0));
                    
                }
            }
        }
        paciente.setEtnia(etniaService.buscarPorId(idEtnia));
        if (gender != null) {
            paciente.setGender(gender.charAt(0));
        }
        String msg = "";
        if (paciente.getId() == null) {
//            insert = true;
            pacienteService.adicionar(paciente);
            this.viewAddVcf(paciente.getId());
            msg = "Patient successfully registered";
        } else {
            pacienteService.atualizar(paciente);
            msg = "Patient successfully updated";
        }
        this.limpar();
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(msg));
        return "paciente.xhtml?faces-redirect=true";
//            } catch (Exception ex) {
//                context.getExternalContext()
//                        .getFlash().setKeepMessages(true);
//                context.addMessage(null, new FacesMessage("Ocorreu erros ao realizar o cadastro " + ex.getMessage()));
//                return "paciente.xhtml";
//            }

//        }else{
//            context.addMessage("formulario:nome", new FacesMessage(FacesMessage.SEVERITY_ERROR, "error message", "Nome do paciente ja cadastrado"));
//            context.addMessage("formulario:secondId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "error message", "Second Id do paciente ja cadastrado"));
//            return "paciente_novo.xhtml";
//        }
    }

    public void limpar() {
        paciente = new Paciente();
        gender = "";
        idEtnia = 0L;
        father = "";
        mother = "";
                
        pacientes = pacienteService.buscar();
//        if (id != null) {
//            if (!id.equals("")) {
//                paciente = pacienteService.buscarId(Long.valueOf(id));
//                if (paciente != null) {
//                    if (paciente.getEtnia() != null) {
//                        idEtnia = paciente.getEtnia().getId();
//                    }
//                    vcfs = vcfService.buscarPacienteId(paciente.getId());
//                }
//            }
//        }
        this.defCrudModeRead();
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
        System.out.println("----> Abrindo o view");
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
        try {
            List<String> pais = pacienteService.findMenByName(query + "%")
                    .stream()
                    .map(p -> p.getNome())
                    .collect(Collectors.toList());
            return pais;
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        return new ArrayList<>();
    }

    public List<String> motherComplete(String query) {
        return pacienteService.findWomansByName(query + "%")
                .stream()
                .map(p -> p.getNome())
                .collect(Collectors.toList());
    }

    private boolean isValid() {
        return pacienteService.buscarNome(paciente.getNome()).isEmpty();
    }

    public void validateSecond(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        String nome = (String) o;
        List<Paciente> list = pacienteService.buscarNome(nome);
        if (list == null) {
            return;
        }
        if (list.isEmpty()) {
            return;
        }
        FacesMessage message
                = new FacesMessage("Second erro !!! :D");
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(message);
    }
  
    public void find() {
        paciente.setEtnia(etniaService.buscarPorId(idEtnia));
        if (gender != null) {
            paciente.setGender(gender.charAt(0));
        }
        pacientes = pacienteService.findByExample(paciente);
    }

    public void novo() {
        defCrudModeUpdate();
        paciente = new Paciente();
        idEtnia = 0;
        gender = "";
        father = "";
        mother = "";
    }
    
    
    public void validateFather(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        if(disabledValidation)
            return;
        String nome = (String) o;
        if( (nome == null) || ("".equals(nome)) ){
            return;            
        }
        List<Paciente> list = pacienteService.findMenByName(nome);
        if(list.size() == 1){
            return;
        }
        FacesMessage message
                = new FacesMessage("Father not found");
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(message);
    }    

    public void validateMother(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        if(disabledValidation)
            return;
        String nome = (String) o;
        if( (nome == null) || ("".equals(nome)) ){
            return;            
        }
        List<Paciente> list = pacienteService.findWomansByName(nome);
        if(list.size() == 1){
            return;
        }
        FacesMessage message
                = new FacesMessage("Mother not found");
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(message);
    }  
    
    public void validateName(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        if(disabledValidation)
            return;
        String nome = (String) o;
        List<Paciente> list = pacienteService.buscarNome(nome);
        if(list.isEmpty()){
            return;
        }
        if(list.size() == 1){
            if(paciente.getId() == list.get(0).getId()){
                return;
            }
        }
        FacesMessage message
                = new FacesMessage("Name already exists");
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(message);
    } 

    public boolean isDisabledValidation() {
        return disabledValidation;
    }

    public void setDisabledValidation(boolean disabledValidation) {
        this.disabledValidation = disabledValidation;
    }

    public boolean isCrudModeRead() {
        return crudMode.equals(CrudMode.Read);
    }

    public void defCrudModeRead() {
        disabledValidation = true;
        crudMode = CrudMode.Read;
    }

    public boolean isCrudModeUpdate() {
        return crudMode.equals(CrudMode.Update);
    }

    public void defCrudModeUpdate() {

        crudMode = CrudMode.Update;
        this.disabledValidation = false;
    }

    public boolean isCrudModeFind() {
        return crudMode.equals(CrudMode.Find);
    }

    public void defCrudModeFind() {
        crudMode = CrudMode.Find;
    }

    public void defCreateMode() {
        this.defCrudModeUpdate();
    }

}
