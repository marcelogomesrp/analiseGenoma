package org.analiseGenoma.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "disease_dbbio")
public class DiseaseDbBio implements Serializable {
    @Id
    @ManyToOne
    private Disease disease;
    @Id
    @ManyToOne
    private DbBio dbBio;
    private String dbIdentifier;
    private String url;

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public DbBio getDbBio() {
        return dbBio;
    }

    public void setDbBio(DbBio dbBio) {
        this.dbBio = dbBio;
    }

    public String getDbIdentifier() {
        return dbIdentifier;
    }

    public void setDbIdentifier(String dbIdentifier) {
        this.dbIdentifier = dbIdentifier;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    

}
