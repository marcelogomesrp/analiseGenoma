package org.analiseGenoma.sessionbean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.analiseGenoma.model.Patient;

@Named(value = "patientSB")
@SessionScoped
public class PatientSB implements Serializable {

    private Patient patient;

    @PostConstruct
    public void init() {
        patient = new Patient();
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void reset() {
        patient = new Patient();
    }
    
    

}
