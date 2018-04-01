package org.analiseGenoma.sessionbean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.analiseGenoma.model.Filtro;


@Named(value = "filterSB")
@SessionScoped
public class FilterSB implements Serializable {
    private Filtro filter;

    @PostConstruct
    public void init() {
        filter = new Filtro();
    }

    public Filtro getFilter() {
        return filter;
    }

    public void setFilter(Filtro filter) {
        this.filter = filter;
    }

    public void reset() {
        this.filter = new Filtro();
    }
    
}