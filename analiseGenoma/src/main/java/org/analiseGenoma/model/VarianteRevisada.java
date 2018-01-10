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
    private Usuario revisor;
    private boolean patogenic;
    @Column(columnDefinition = "text")
    private String note;

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

    public Usuario getRevisor() {
        return revisor;
    }

    public void setRevisor(Usuario revisor) {
        this.revisor = revisor;
    }

    public boolean isPatogenic() {
        return patogenic;
    }

    public void setPatogenic(boolean patogenic) {
        this.patogenic = patogenic;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
}