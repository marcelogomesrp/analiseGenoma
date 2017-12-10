package org.analiseGenoma.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "filtro")
public class Filtro  implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_filtro")
    private Long id;
    @OneToOne
    @JoinColumn(name = "analise_id")
    private Analise analise;
    //@OneToMany
    @ManyToMany
    private List<Gene> genes;
    //@OneToMany
    @ManyToMany
    private List<Cromossomo> cromossomos;
    private Double qualidadeMin;
    private Double qualidadeMax;
    //@OneToMany
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Impact> impacto;
    //@OneToMany
    @ManyToMany
    private List<InformacaoBiologica> infBiologica;

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

    public List<Gene> getGenes() {
        return genes;
    }

    public void setGenes(List<Gene> genes) {
        this.genes = genes;
    }

    public List<Cromossomo> getCromossomos() {
        return cromossomos;
    }

    public void setCromossomos(List<Cromossomo> cromossomos) {
        this.cromossomos = cromossomos;
    }

    public Double getQualidadeMin() {
        return qualidadeMin;
    }

    public void setQualidadeMin(Double qualidadeMin) {
        this.qualidadeMin = qualidadeMin;
    }

    public Double getQualidadeMax() {
        return qualidadeMax;
    }

    public void setQualidadeMax(Double qualidadeMax) {
        this.qualidadeMax = qualidadeMax;
    }

    public List<Impact> getImpacto() {
        return impacto;
    }

    public void setImpacto(List<Impact> impacto) {
        this.impacto = impacto;
    }

    public List<InformacaoBiologica> getInfBiologica() {
        return infBiologica;
    }

    public void setInfBiologica(List<InformacaoBiologica> infBiologica) {
        this.infBiologica = infBiologica;
    }

    @Override
    public String toString() {
        return "Filtro{" + "id=" + id + ", analise=" + analise + ", genes=" + genes + ", cromossomos=" + cromossomos + ", qualidadeMin=" + qualidadeMin + ", qualidadeMax=" + qualidadeMax + ", impacto=" + impacto + ", infBiologica=" + infBiologica + '}';
    }
    
    

    
}