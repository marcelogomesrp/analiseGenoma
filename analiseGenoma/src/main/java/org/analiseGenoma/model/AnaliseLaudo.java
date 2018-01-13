package org.analiseGenoma.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "analise_laudo")
public class AnaliseLaudo implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_analiselaudo")
    private Long id;
    @OneToOne
    private Analise analise;
    @ManyToOne
    private Patologia patologia;
    @ManyToMany
    List<Gene> genes;
    @Column(columnDefinition = "text")
    private Boolean correlato;
    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Analise getAnalise() {
        return analise;
    }

    public void setAnalise(Analise analise) {
        this.analise = analise;
    }

    public Patologia getPatologia() {
        return patologia;
    }

    public void setPatologia(Patologia patologia) {
        this.patologia = patologia;
    }

    public List<Gene> getGenes() {
        return genes;
    }

    public void setGenes(List<Gene> genes) {
        this.genes = genes;
    }

    public Boolean getCorrelato() {
        return correlato;
    }

    public void setCorrelato(Boolean correlato) {
        this.correlato = correlato;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
}
