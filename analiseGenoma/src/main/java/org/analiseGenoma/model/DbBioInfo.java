package org.analiseGenoma.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "dbbioinfo")
public class DbBioInfo implements Serializable{    
    @Id
    @ManyToOne
    private Disease disease;
    @Id
    @ManyToOne
    private DbBio dbBio;
    @OneToMany
    private Set<Gene> genes;
    private String infoIdentifier;
    private String url;

    public DbBioInfo() {
    }

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

    public Set<Gene> getGenes() {
        return genes;
    }

    public void setGenes(Set<Gene> genes) {
        this.genes = genes;
    }

    public void addGene(Gene gene){
        if(genes == null)
            genes = new HashSet<>();
        genes.add(gene);
    }


    public String getInfoIdentifier() {
        return infoIdentifier;
    }

    public void setInfoIdentifier(String infoIdentifier) {
        this.infoIdentifier = infoIdentifier;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.disease);
        hash = 41 * hash + Objects.hashCode(this.dbBio);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DbBioInfo other = (DbBioInfo) obj;
        if (!Objects.equals(this.disease, other.disease)) {
            return false;
        }
        if (!Objects.equals(this.dbBio, other.dbBio)) {
            return false;
        }
        return true;
    }

    
    
    
}