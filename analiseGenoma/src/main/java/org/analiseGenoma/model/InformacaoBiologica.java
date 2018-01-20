package org.analiseGenoma.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "informacao_biologica")
public class InformacaoBiologica implements Serializable{        
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_informacao_biologica")
    @Id
    private Long id;    
    @OneToOne
    @JoinColumn(name = "bancobiologico_id")
    private DbBio bdBio;
    @OneToOne
    @JoinColumn(name = "gene_id")
    private Gene gene;
    @OneToOne
    @JoinColumn(name = "patologia_id")
    private Disease patologia;     
    private String util;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DbBio getBdBio() {
        return bdBio;
    }

    public void setBdBio(DbBio bdBio) {
        this.bdBio = bdBio;
    }

    public Gene getGene() {
        return gene;
    }

    public void setGene(Gene gene) {
        this.gene = gene;
    }

    public Disease getPatologia() {
        return patologia;
    }

    public void setPatologia(Disease patologia) {
        this.patologia = patologia;
    }

    public String getUtil() {
        return util;
    }

    public void setUtil(String util) {
        this.util = util;
    }
    
    

    @Override
    public String toString() {
        return "InformacaoBiologica{" + "id=" + id + ", bdBio=" + bdBio + ", gene=" + gene + ", patologia=" + patologia + '}';
    }
            
}
