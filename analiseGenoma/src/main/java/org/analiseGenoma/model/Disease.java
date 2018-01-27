package org.analiseGenoma.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "disease")

@XmlRootElement(name = "disease")
@XmlAccessorType(XmlAccessType.FIELD)

public class Disease implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_disease")
    @Id
    private Long id;
    private String name;
    private String icd;
    @Column(columnDefinition = "text")
    private String description;
    @ManyToOne
    @JoinColumn(name = "inheritance_id")
    private InheritanceType inheritanceType;
    @ManyToOne
    @JoinColumn(name = "age_id")
    private Age age;
    private Double prevalence;

    @OneToMany
    @XmlElementWrapper(name="genes")
    private Set<Gene> genes;
    @OneToMany(mappedBy = "disease")
    private Set<DbBioInfo> dbBioInfos;
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
        //this.icd = icd.toUpperCase().replaceAll("\\W", "");        
        this.icd = icd.toUpperCase().trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase().trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description.toUpperCase().trim();
    }

//
//    public void setUrl(String url) {
//        if(!url.matches("^$|^htt")){            
//            StringBuilder sb = new StringBuilder();
//            url = sb.append("http://").append(url).toString(); 
//        }
//        this.url = url;
//    }
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

    public Double getPrevalence() {
        return prevalence;
    }

    public void setPrevalence(Double prevalence) {
        this.prevalence = prevalence;
    }

    public void setPrevalence(String prevalence) {
        try {
            Double n = Double.parseDouble(prevalence);
            this.setPrevalence(n);
        } catch (Exception ex) {
            System.out.println("************************************************************ Erro na conversao da prevalence: " + ex.getMessage());
        }
    }

//    public Set<Gene> getGenes() {
////        if (genes == null) {
////            genes = new HashSet<>();
////            if (dbBioInfos != null) {
////                for (DbBioInfo info : this.getDbBioInfos()) {
////                    genes.addAll(info.getGenes());
////                }
////            }
////        }
//        return genes;
//    }
//
//    public void setGenes(Set<Gene> genes) {
//        this.genes = genes;
//    }
//
//    public Set<DbBioInfo> getDbBioInfos() {
//        return dbBioInfos;
//    }
//
//    public void setDbBioInfos(Set<DbBioInfo> dbBioInfos) {
//        this.dbBioInfos = dbBioInfos;
//}
    @Override
    public String toString() {
        return "Patologia{" + "id=" + id + ", nome=" + name + ", icd=" + icd + '}';
    }

}
