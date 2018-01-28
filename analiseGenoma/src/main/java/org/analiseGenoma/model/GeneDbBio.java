package org.analiseGenoma.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "gene_dbbio")
@IdClass(GeneDbBioPK.class)

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GeneDbBio implements Serializable{
    @Id
    
    @ManyToOne
    @XmlTransient
    private Gene gene;
    @Id
    @ManyToOne
    private DbBio dbBio;
    private String dbIdentifier;
    private String url;

    public Gene getGene() {
        return gene;
    }

    public void setGene(Gene gene) {
        this.gene = gene;
    }

    public DbBio getDbBio() {
        return dbBio;
    }

    public void setDbBio(DbBio dbBio) {
        this.dbBio = dbBio;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDbIdentifier() {
        return dbIdentifier;
    }

    public void setDbIdentifier(String dbIdentifier) {
        this.dbIdentifier = dbIdentifier;
    }

    public GeneDbBio() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.gene);
        hash = 53 * hash + Objects.hashCode(this.dbBio);
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
        final GeneDbBio other = (GeneDbBio) obj;
        if (!Objects.equals(this.gene, other.gene)) {
            return false;
        }
        if (!Objects.equals(this.dbBio, other.dbBio)) {
            return false;
        }
        return true;
    }

    
    @Override
    public String toString() {
        return "GeneDbBio{" + "gene=" + gene + ", dbBio=" + dbBio + ", dbIdentifier=" + dbIdentifier + ", url=" + url + '}';
    }
    
    
}
