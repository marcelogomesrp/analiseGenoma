package org.analiseGenoma.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "gene_dbbio")
public class GeneDbBio implements Serializable{
    @Id
    @ManyToOne
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

    @Override
    public String toString() {
        return "GeneDbBio{" + "gene=" + gene + ", dbBio=" + dbBio + ", dbIdentifier=" + dbIdentifier + ", url=" + url + '}';
    }
    
    
}
