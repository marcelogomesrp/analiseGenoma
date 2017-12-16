package org.analiseGenoma.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "vcf_metadata")
public class VcfMetadata implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vcfmetadata")
    private Long id;
    @OneToOne
    @JoinColumn(name = "vcf_id")    
    private Vcf vcf;
    private int qtdVariante;    
    @ManyToMany
    private Set<Cromossomo> cromossomos;
    @ElementCollection
    //@CollectionTable(name="vcfmetadata_referencias")
    private Set<String> referencias;
    @ElementCollection
    private Set<String> alterado;
    private Long positonMax;
    private Long positonMin;
    @ManyToMany
    private Set<Gene> genes;
    @ManyToMany
    private Set<UmdPredictor> umdPredictors;
    @ManyToMany
    private Set<Effect> effects;
    @ManyToMany
    private Set<Sift> sifts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vcf getVcf() {
        return vcf;
    }

    public void setVcf(Vcf vcf) {
        this.vcf = vcf;
    }

    public int getQtdVariante() {
        return qtdVariante;
    }

    public void setQtdVariante(int qtdVariante) {
        this.qtdVariante = qtdVariante;
    }

    public Set<Cromossomo> getCromossomos() {
        return cromossomos;
    }

    public void setCromossomos(Set<Cromossomo> cromossomos) {
        this.cromossomos = cromossomos;
    }

    public Long getPositonMax() {
        return positonMax;
    }

    public void setPositonMax(Long positonMax) {
        this.positonMax = positonMax;
    }

    public Long getPositonMin() {
        return positonMin;
    }

    public void setPositonMin(Long positonmin) {
        this.positonMin = positonmin;
    }

    public Set<String> getReferencias() {
        return referencias;
    }

    public void setReferencias(Set<String> referencias) {
        this.referencias = referencias;
    }

    public Set<String> getAlterado() {
        return alterado;
    }

    public void setAlterado(Set<String> alterado) {
        this.alterado = alterado;
    }

    public Set<Gene> getGenes() {
        return genes;
    }

    public void setGenes(Set<Gene> genes) {
        this.genes = genes;
    }

    public Set<UmdPredictor> getUmdPredictors() {
        return umdPredictors;
    }

    public void setUmdPredictors(Set<UmdPredictor> umdPredictors) {
        this.umdPredictors = umdPredictors;
    }

    public Set<Effect> getEffects() {
        return effects;
    }

    public void setEffects(Set<Effect> effects) {
        this.effects = effects;
    }

    public Set<Sift> getSifts() {
        return sifts;
    }

    public void setSifts(Set<Sift> sifts) {
        this.sifts = sifts;
    }
    
    
    
    
    
    
    
    
}