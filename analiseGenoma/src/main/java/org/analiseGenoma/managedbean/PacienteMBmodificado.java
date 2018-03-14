package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Named;
//import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import org.analiseGenoma.model.Paciente;
import org.analiseGenoma.service.PacienteService;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named(value = "pacienteNaoMB")
@ViewScoped
public class PacienteMBmodificado implements Serializable {

    @Inject
    private FacesContext context;
    @Inject
    private PacienteService pacienteService;
    private Paciente paciente;
    private Boolean disabledValidation;
    private Boolean crudModeUpdate;
    private int id;

    private List<Paciente> pacientes;

    @PostConstruct
    public void init() {
        try {
            disabledValidation = true;
            crudModeUpdate = true;
            id =1;
            System.out.println("Pagina pacienteMB instanciada novamente");
            paciente = new Paciente();
            pacientes = pacienteService.buscar();

        } catch (Exception ex) {
            System.out.println("Erro no init do PacienteMB");
        }
    }

    public PacienteMBmodificado() {
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
    }



    public String detalhe(Paciente paciente) {
        this.paciente = paciente;
        return "paciente_detalhe.xhtml";
    }


    public void salvar() {
        System.out.println("-----> " + paciente.toString());
        //paciente.setEtnia(etniaService.findById(idEtnia));
        pacienteService.atualizar(paciente);
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Atualizado com sucesso"));
        //return "paciente_pesquisar.xhtml?faces-redirect=true";
    }

    public void viewAddVcf(Long id) {
        System.out.println("----> Abrindo o view");
        paciente = pacienteService.buscarId(id);
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
        RequestContext.getCurrentInstance().closeDialog(msg);
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
  

    public void novo() {
        try {
            paciente = new Paciente();
            //idEtnia = 0;
        } catch (Exception ex) {
            System.out.println("Erro no novo: " + ex.getMessage());
        }
    }
    
    

    public void validateMother(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {

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


    public boolean isCrudModeRead() {
        try{

        }catch(Exception ex){
            System.out.println("erro isCrudModeRead: " + ex.getMessage());
        }
        return true;
    }

    public Boolean getDisabledValidation() {
        return disabledValidation;
    }

    public void setDisabledValidation(Boolean disabledValidation) {
        this.disabledValidation = disabledValidation;
    }

    public Boolean getCrudModeUpdate() {
        return crudModeUpdate;
    }

    public void setCrudModeUpdate(Boolean crudModeUpdate) {
        this.crudModeUpdate = crudModeUpdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }
    
    


    
    
}
