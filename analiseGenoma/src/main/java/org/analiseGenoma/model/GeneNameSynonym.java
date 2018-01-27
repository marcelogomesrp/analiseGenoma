package org.analiseGenoma.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "gene_name_synonym")
@IdClass(GeneNameSynonymPK.class)
public class GeneNameSynonym implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Id
    @ManyToOne
    private Gene gene;
    @GeneratedValue

    private String name;

    public GeneNameSynonym() {
    }

    public GeneNameSynonym(Gene gene, String name) {
        this.gene = gene;
        this.name = name;
    }

    public Gene getGene() {
        return gene;
    }

    public void setGene(Gene gene) {
        this.gene = gene;
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
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.gene);
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
        final GeneNameSynonym other = (GeneNameSynonym) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.gene, other.gene)) {
            return false;
        }
        return true;
    }

}
