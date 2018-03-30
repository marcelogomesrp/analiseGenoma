package org.analiseGenoma.convert;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.model.Patient;
import org.analiseGenoma.model.Population;
import org.analiseGenoma.service.PatientService;

@Named
public class PacientConverter implements Converter {
    
    @Inject
    private PatientService service;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {

        //if (value != null && value.trim().length() > 0) {
        if (value != null && value.trim().length() > 0 && (!"select one".equals(value))) {
            try {
                Long id = Long.valueOf(value);
                Patient obj = service.findById(id);
                return obj;
            } catch (NumberFormatException ex) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Inheritance."));
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        try {
            if (object != null) {
                return String.valueOf(((Population) object).getId());
            }
        } catch (Exception ex) {

        }
        return null;
    }
}
