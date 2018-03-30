package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.model.Patient;
import org.analiseGenoma.model.Population;
import org.analiseGenoma.service.PatientService;
import org.analiseGenoma.service.PopulationService;
import org.analiseGenoma.sessionbean.PatientSB;
import org.primefaces.context.RequestContext;

@Named(value = "patientMB")
@ViewScoped
public class PatientMB implements Serializable {

    @Inject
    private FacesContext context;
    @Inject
    private PatientSB patientSB;
    @Inject
    private PopulationService populationService;
    @Inject
    private PatientService patientService;
    
    private boolean disabledValidation;
    private CrudMode crudMode;
    private List<Patient> patients;

    @PostConstruct
    public void init() {
        this.reset();

    }
    
    public void reset(){
        defCrudModeRead();
        patients = new ArrayList<>();
        this.patientSB.reset();
    }

    public PatientSB getPatientSB() {
        return patientSB;
    }

    public void setPatientSB(PatientSB patientSB) {
        this.patientSB = patientSB;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
    
    

    public List<SelectItem> getSelectPopulations() {
        List<SelectItem> populations = new ArrayList<>();
        try {

            for (Population p : populationService.find()) {
                //TODO: etnias.add(new SelectItem(e.getId(), e.getSigla() + " - " + e.getNome()));
                populations.add(new SelectItem(p, p.getDescription()));
            }
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        return populations;
    }

    public void save() {
        try {
            Patient tmp = patientService.FindByNameAndBirth(patientSB.getPatient());
            if (tmp == null) {
                patientService.persiste(patientSB.getPatient());
                context.getExternalContext()
                        .getFlash().setKeepMessages(true);
                context.addMessage(null, new FacesMessage("it successfully saved"));
                this.viewAddVcf();
                this.reset();
            }else{
                context.getExternalContext()
                        .getFlash().setKeepMessages(true);
                context.addMessage(null, new FacesMessage("Patient already exists"));
            }
            //patientSB.reset();

        } catch (Exception ex) {
            context.getExternalContext()
                    .getFlash().setKeepMessages(true);
            context.addMessage(null, new FacesMessage(ex.getMessage()));
        }
    }
    
    public void find() {
        //paciente.setEtnia(etniaService.findById(idEtnia));
//        if (gender != null) {
//            paciente.setGender(gender.charAt(0));
//        }
//        pacientes = pacienteService.findByExample(paciente);
        patients = patientService.findByExample(patientSB.getPatient());
        
    }
    
    public void viewAddVcf() {
        Map<String, Object> options = new HashMap<>();
        options.put("modal", true);
        options.put("width", 600);
        options.put("height", 400);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        options.put("resizable", false);

        Map<String, List<String>> params = new HashMap<String, List<String>>();
        List<String> values = new ArrayList<String>();
        RequestContext.getCurrentInstance().openDialog("viewAddVcf", options, params);
    }

    
    public void novo() {
        try {
            patientSB.reset();
            defCrudModeUpdate();
            //paciente = new Patient();
            //idEtnia = 0;
            //gender = "";
            //father = "";
            //mother = "";
            //population = new Population();
        } catch (Exception ex) {
            System.out.println("Erro no novo: " + ex.getMessage());
        }
    }
    
    public void search(){
        defCrudModeFind();
    }
    
    public void defCrudModeFind(){
        crudMode = CrudMode.Find;
    }
            
    public void defCrudModeUpdate() {
        crudMode = CrudMode.Update;
        this.disabledValidation = false;
    }
    
    public void defCrudModeRead(){
        crudMode = CrudMode.Read;
        this.disabledValidation = true;
    }
    
    public boolean getCrudModeRead(){
        return crudMode == CrudMode.Read;
    }
    
    public boolean getCrudModeUpdate(){
        return crudMode == CrudMode.Update;
    }
    
    public boolean getCrudModeFind(){
        return crudMode == CrudMode.Find;
    }
    
    public boolean getHasPacitient(){
        return !patients.isEmpty();
    }
    
    public boolean getShowFields(){
        return (getCrudModeFind() || getCrudModeUpdate());
    }
    
    
    
    
}
