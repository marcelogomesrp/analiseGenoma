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
    private DbBio dbbio;
    private String url;
    @ManyToOne
    @JoinColumn(name = "inheritance_id")
    private InheritanceType inheritanceType;        
    @ManyToOne
    @JoinColumn(name = "age_id")
    private Age age;
    @ManyToOne
    @JoinColumn(name = "synonymous_id")
    private Disease synonymous;
    private Double prevalence;
    
            

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
        this.icd = icd.toUpperCase().replaceAll("\\W", "");        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description.toUpperCase();
    }

    public String getDbIdentifier() {
        return dbIdentifier;
    }

    public void setDbIdentifier(String dbIdentifier) {
        this.dbIdentifier = dbIdentifier.toUpperCase();
    }

    public DbBio getDbbio() {
        return dbbio;
    }

    public void setDbbio(DbBio dbbio) {
        this.dbbio = dbbio;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if(!url.matches("^$|^htt")){            
            StringBuilder sb = new StringBuilder();
            url = sb.append("http://").append(url).toString(); 
        }
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

    public Disease getSynonymous() {
        return synonymous;
    }

    public void setSynonymous(Disease synonymous) {
        this.synonymous = synonymous;
    }

    public Double getPrevalence() {
        return prevalence;
    }

    public void setPrevalence(Double prevalence) {
        this.prevalence = prevalence;
    }
    public void setPrevalence(String prevalence) {
        try{
            Double n = Double.parseDouble(prevalence);
            this.setPrevalence(n);            
        }catch(Exception ex){
            System.out.println("************************************************************ Erro na conversao da prevalence: " + ex.getMessage());
        }
    }
    
    
    @Override
    public String toString() {
        return "Patologia{" + "id=" + id + ", nome=" + name + ", icd=" + icd + '}';
    }    
    
}