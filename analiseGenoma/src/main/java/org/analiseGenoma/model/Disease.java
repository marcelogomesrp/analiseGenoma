package org.analiseGenoma.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "disease")
public class Disease implements Serializable{        
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_disease")
    @Id
    private Long id;
    private String name;
    private String icd;
    @Column(columnDefinition = "text")
    private String description;
    private String dbIdentifier;
    @ManyToOne
    private BancoBiologico dbbio;
    private String url;
    @ManyToOne
    @JoinColumn(name = "inheritance_id")
    private InheritanceType inheritanceType;    
    
    @JoinColumn(name = "age_id")
    @ManyToOne
    private Age age;
    
            

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIcd() {
        return icd;
    }

    public void setIcd(String icd) {
        this.icd = icd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDbIdentifier() {
        return dbIdentifier;
    }

    public void setDbIdentifier(String dbIdentifier) {
        this.dbIdentifier = dbIdentifier;
    }

    public BancoBiologico getDbbio() {
        return dbbio;
    }

    public void setDbbio(BancoBiologico dbbio) {
        this.dbbio = dbbio;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public InheritanceType getInheritanceType() {
        return inheritanceType;
    }

    public void setInheritanceType(InheritanceType inheritanceType) {
        this.inheritanceType = inheritanceType;
    }

    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }
    
    @Override
    public String toString() {
        return "Patologia{" + "id=" + id + ", nome=" + name + ", icd=" + icd + '}';
    }    
    
}