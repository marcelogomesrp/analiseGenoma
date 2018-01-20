package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.model.DbBioInfo;

@Named(value = "dbBioInfoMB")
@ViewScoped
public class DbBioInfoMB implements Serializable {

    @Inject
    private FacesContext context;
    private DbBioInfo dbBioInfo;
    private List<DbBioInfo> list;

    public DbBioInfo getDbBioInfo() {
        return dbBioInfo;
    }

    public void setDbBioInfo(DbBioInfo dbBioInfo) {
        this.dbBioInfo = dbBioInfo;
    }



    public List<DbBioInfo> getList() {
        return list;
    }

    public void setList(List<DbBioInfo> list) {
        this.list = list;
    }
    
    
    
    @PostConstruct
    public void init() {
        dbBioInfo = new DbBioInfo();
    }
}
