package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.model.DbBio;
import org.analiseGenoma.service.DbBioService;

@Named(value = "dbBioMB")
@RequestScoped
public class DbBioMB implements Serializable {

    @Inject
    private DbBioService dbBioService;
    @Inject
    private FacesContext context;
    private List<DbBio> dbBios;
    private DbBio dbBio;

    @PostConstruct
    public void init() {
        dbBios = dbBioService.find();
        dbBio = new DbBio();
    }

    public List<DbBio> getDbbios() {
        return dbBios;
    }

    public void setDbbios(List<DbBio> dbbios) {
        this.dbBios = dbbios;
    }

    public DbBio getDbBio() {
        return dbBio;
    }

    public void setDbBio(DbBio dbBio) {
        this.dbBio = dbBio;
    }

    public void add(){
        dbBioService.persiste(dbBio);
        context.addMessage(null, new FacesMessage("It's done") );
        dbBio = new DbBio();
        dbBios = dbBioService.find();
        
    }
    
    
    
    


    
}
