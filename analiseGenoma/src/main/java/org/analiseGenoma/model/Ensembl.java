package org.analiseGenoma.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "ensembl")
public class Ensembl implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ensembl")
    private Long id;
    @Column(unique = true)
    private String idEnsembl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdEnsembl() {
        return idEnsembl;
    }

    public void setIdEnsembl(String idEnsembl) {
        this.idEnsembl = idEnsembl;
    }
    
    public String getUrl(){
        if(getIdEnsembl() == null)
            return null;
        return "www.ensembl.org/id/ENST00000261772" + getIdEnsembl();
    }
}