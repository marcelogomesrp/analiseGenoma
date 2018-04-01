package org.analiseGenoma.managedbean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.service.FiltroService;
import org.analiseGenoma.sessionbean.FilterSB;

@Named(value = "filterMB")
@ViewScoped
public class FilterMB implements Serializable {

    @Inject
    private FacesContext context;
    private CrudMode crudMode;
    @Inject
    private FilterSB filterSB;
    @Inject
    private FiltroService filterService;

    @PostConstruct
    public void init() {
        this.reset();
    }

    private void reset() {
        filterSB.reset();
        this.crudMode = CrudMode.Read;
    }

    public void add() {
        defCrudModeUpdate();
    }

    public void cancel() {
        this.reset();
    }

    public void save() {
        filterService.persiste(filterSB.getFilter());
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("it successfully saved"));
        this.reset();
    }

    public boolean getCrudModeRead() {
        return crudMode == CrudMode.Read;
    }

    public void defCrudModeRead() {
        crudMode = CrudMode.Read;
    }

    public boolean getCrudModeFind() {
        return crudMode == CrudMode.Find;
    }

    public void defCrudModeFind() {
        crudMode = CrudMode.Find;
    }

    public boolean getCrudModeUpdate() {
        return crudMode == CrudMode.Update;
    }

    public void defCrudModeUpdate() {
        crudMode = CrudMode.Update;
    }

    public boolean getShowFilds() {
        return getCrudModeUpdate() || getCrudModeFind();
    }

}
