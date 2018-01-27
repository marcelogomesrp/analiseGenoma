package org.analiseGenoma.model;

import java.io.Serializable;
import java.util.Objects;


public class GeneSymbolSynonymPK implements Serializable {
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id_genenamesynonym")
    private Long id;
    //@ManyToOne
    private Gene gene;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Gene getGene() {
        return gene;
    }

    public void setGene(Gene gene) {
        this.gene = gene;
    }

    public GeneSymbolSynonymPK() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.gene);
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
        final GeneSymbolSynonymPK other = (GeneSymbolSynonymPK) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.gene, other.gene)) {
            return false;
        }
        return true;
    }
    
    
    
    

}
