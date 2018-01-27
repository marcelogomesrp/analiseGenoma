package org.analiseGenoma.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "gene_symbol_synonym")
@IdClass(GeneSymbolSynonymPK.class)
@XmlRootElement
public class GeneSymbolSynonym implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Id
    @ManyToOne
    private Gene gene;
    private String symbol;

    public GeneSymbolSynonym() {
    }

    public GeneSymbolSynonym(String symbol) {
        this.symbol = symbol;
    }

    public GeneSymbolSynonym(Gene gene, String symbol) {
        this.gene = gene;
        this.symbol = symbol;
    }
    
    

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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.id);
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
        final GeneSymbolSynonym other = (GeneSymbolSynonym) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }



    

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    

}
