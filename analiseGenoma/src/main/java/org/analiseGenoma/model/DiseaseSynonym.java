package org.analiseGenoma.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "disease_synonym")
public class DiseaseSynonym implements Serializable {
    @Id
    @ManyToOne
    private Disease disease;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_diseasesynonym")
    @Id
    private Long id;
    private String name;

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim().toUpperCase();
    }
    
    
}
