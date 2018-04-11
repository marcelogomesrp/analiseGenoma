package org.analiseGenoma.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "variante_revisada")
public class VarianteRevisada implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_varianterevisada")
    private Long id; 
    @ManyToOne
    private Variante variant;
    @ManyToOne
    private User revisor;
    private Integer patogenic;
    @Column(columnDefinition = "text")
    private String note;
    @ManyToOne
    private Analise analise;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Variante getVariant() {
        return variant;
    }

    public void setVariant(Variante variant) {
        this.variant = variant;
    }

    public User getRevisor() {
        return revisor;
    }

    public void setRevisor(User revisor) {
        this.revisor = revisor;
    }

    public Integer getPatogenic() {
        return patogenic;
    }

    public void setPatogenic(Integer patogenic) {
        this.patogenic = patogenic;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Analise getAnalise() {
        return analise;
    }

    public void setAnalise(Analise analise) {
        this.analise = analise;
    }
    
    
    
}