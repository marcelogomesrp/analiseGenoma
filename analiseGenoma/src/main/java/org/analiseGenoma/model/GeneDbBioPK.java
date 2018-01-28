package org.analiseGenoma.model;

import java.io.Serializable;
import java.util.Objects;

public class GeneDbBioPK implements Serializable{
    private Gene gene;
    private DbBio dbBio;

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

    public GeneDbBioPK() {
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.gene);
        hash = 97 * hash + Objects.hashCode(this.dbBio);
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
        final GeneDbBioPK other = (GeneDbBioPK) obj;
        if (!Objects.equals(this.gene, other.gene)) {
            return false;
        }
        if (!Objects.equals(this.dbBio, other.dbBio)) {
            return false;
        }
        return true;
    }
    
    
    
}
