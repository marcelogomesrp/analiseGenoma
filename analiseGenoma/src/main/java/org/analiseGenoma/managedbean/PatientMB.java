package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.model.Patient;
import org.analiseGenoma.model.Population;
import org.analiseGenoma.service.PatientService;
import org.analiseGenoma.service.PopulationService;
import org.analiseGenoma.sessionbean.PatientSB;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

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
//        if(patientSB.getPatient() != null){
//            if(patientSB.getPatient().getId() == null){
//                this.reset();    
//            }
//        }
        this.reset();

    }

    public void reset() {
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
                //this.reset();
            } else {
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

    public void search() {
        defCrudModeFind();
    }

    public void defCrudModeFind() {
        crudMode = CrudMode.Find;
    }

    public void defCrudModeUpdate() {
        crudMode = CrudMode.Update;
        this.disabledValidation = false;
    }

    public void defCrudModeRead() {
        crudMode = CrudMode.Read;
        this.disabledValidation = true;
    }

    public boolean getCrudModeRead() {
        return crudMode == CrudMode.Read;
    }

    public boolean getCrudModeUpdate() {
        return crudMode == CrudMode.Update;
    }

    public boolean getCrudModeFind() {
        return crudMode == CrudMode.Find;
    }

    public boolean getHasPacitient() {
        return !patients.isEmpty();
    }

    public boolean getShowFields() {
        return (getCrudModeFind() || getCrudModeUpdate());
    }

    public List<String> fatherComplete(String query) {
        try {
            List<String> pais = patientService.findMenByName(query + "%")
                    .stream()
                    .map(p -> p.getId() + ":" + p.getName() + ":" + p.getBirth())
                    .collect(Collectors.toList());
            return pais;
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        return new ArrayList<>();
    }

    public List<String> motherComplete(String query) {
        try {
            List<String> pais = patientService.findWomansByName(query + "%")
                    .stream()
                    .map(p -> p.getId() + ":" + p.getName() + ":" + p.getBirth())
                    .collect(Collectors.toList());
            return pais;
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        return new ArrayList<>();
    }

    public void validateFather(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        if (disabledValidation) {
            return;
        }
        String nome = (String) o;
        if ((nome == null) || ("".equals(nome))) {
            return;
        }
        Long id = Long.valueOf(nome.split(":")[0].trim());
        Patient p = patientService.findById(id);
        if (p != null) {
            return;
        }
        FacesMessage message
                = new FacesMessage("Father not found");
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(message);
    }

    public void validateMother(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
//        if (disabledValidation) {
//            return;
//        }
//        String nome = (String) o;
//        if ((nome == null) || ("".equals(nome))) {
//            return;
//        }
//        Long id = Long.valueOf(nome.split(":")[0].trim());
//        Patient p = patientService.findById(id);
//        if (p != null) {
//            return;
//        }
//        FacesMessage message
//                = new FacesMessage("Mother not found");
//        message.setSeverity(FacesMessage.SEVERITY_ERROR);
//        throw new ValidatorException(message);
    }

    public void onViewAddVcf(SelectEvent event) {
        String msg = (String) event.getObject();
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(msg));
        this.reset();

    }

}
