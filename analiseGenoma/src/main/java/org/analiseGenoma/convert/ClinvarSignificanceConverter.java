package org.analiseGenoma.convert;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.model.ClinvarSignificance;
import org.analiseGenoma.service.ClinvarSignificanceService;


@Named
public class ClinvarSignificanceConverter implements Converter {

    @Inject
    private ClinvarSignificanceService service;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {

        if (value != null && value.trim().length() > 0) {
            try {
                Long id = Long.valueOf(value);
                ClinvarSignificance i = service.findById(id);
                return i;
            } catch (NumberFormatException ex) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Inheritance."));
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        try {
            if(object!= null)
                return String.valueOf(((ClinvarSignificance) object).getId());
        } catch (Exception ex) {

        }
        return null;
    }

}