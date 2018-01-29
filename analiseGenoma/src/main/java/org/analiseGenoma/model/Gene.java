package org.analiseGenoma.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;



@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name = "gene")
public class Gene implements Serializable{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_gene")    
    private Long id;
    @Column
    private String name;
    @Column(unique = true)
    private String symbol; 
    
    @OneToMany(mappedBy = "gene")
    private Set<GeneNameSynonym> geneNameSynonym;

    @OneToMany(mappedBy = "gene")
    private Set<GeneSymbolSynonym> geneSymbolSynonym;
    
    @OneToMany(mappedBy = "gene")
    private Set<GeneDbBio> listDbBio;

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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    
    public void addGeneSymbolSynonymous(String symbol){
        if(getGeneSymbolSynonym() == null){
            setGeneSymbolSynonym(new HashSet<>());            
        }
        getGeneSymbolSynonym().add(new GeneSymbolSynonym(symbol));
    }


    public Set<GeneNameSynonym> getGeneNameSynonym() {
        return geneNameSynonym;
    }

    public void setGeneNameSynonym(Set<GeneNameSynonym> geneNameSynonym) {
        this.geneNameSynonym = geneNameSynonym;
    }

    public Set<GeneSymbolSynonym> getGeneSymbolSynonym() {
        return geneSymbolSynonym;
    }

    public void setGeneSymbolSynonym(Set<GeneSymbolSynonym> geneSymbolSynonym) {
        this.geneSymbolSynonym = geneSymbolSynonym;
    }

    public Set<GeneDbBio> getListDbBio() {
        return listDbBio;
    }

    public void setListDbBio(Set<GeneDbBio> listDbBio) {
        this.listDbBio = listDbBio;
    }


    
    

    @Override
    public String toString() {
        return "Gene{" + "id=" + id + ", name=" + name + ", symbol=" + symbol  + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final Gene other = (Gene) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    
    
}
