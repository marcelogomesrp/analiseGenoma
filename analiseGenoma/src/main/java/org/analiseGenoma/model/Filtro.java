package org.analiseGenoma.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
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
public class Filtro  implements Serializable, Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_filtro")
    private Long id;
    @OneToOne
    @JoinColumn(name = "analise_id")
    private Analise analise;
    
    @Column(name = "by_gene")
    private boolean byGene;    
    @Column(name = "by_gene_analyse")
    private boolean geneAnalyse;
    
    //@OneToMany
    @ManyToMany  //(fetch = FetchType.EAGER)
    private Set<Gene> genes;
    //@OneToMany
    @Column(name = "by_chromosome")
    private boolean byChromosome;
    @ManyToMany
    private Set<Cromossomo> cromossomos;
    

//    private Double qualidadeMin;
//    private Double qualidadeMax;
    
    @Column(name = "by_position")
    private boolean byPosition;
    private Long positionMin;
    private Long positionMax;
    private String name;
    
    //@OneToMany
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Impact> impacto;
    //@OneToMany
    @ManyToMany
    private List<InformacaoBiologica> infBiologica;
    @ManyToMany
    private Set<UmdPredictor> umdPredictors;
    
    @Column(name = "by_reference")
    private boolean byReference;    
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> referencias;
    
       
    
    @ManyToMany
    private Set<Effect> effects;

    @ManyToMany
    private Set<Zygosity> zygosities;
    
    
    
    
    private Double prevalenceMin;
    private Double prevalenceMax;
    
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

    public Set<Zygosity> getZygosities() {
        return zygosities;
    }

    public void setZygosities(Set<Zygosity> zygosities) {
        this.zygosities = zygosities;
    }

    public Set<Cromossomo> getCromossomos() {
        return cromossomos;
    }

    public void setCromossomos(Set<Cromossomo> cromossomos) {
        this.cromossomos = cromossomos;
    }


//    public Double getQualidadeMin() {
//        return qualidadeMin;
//    }
//
//    public void setQualidadeMin(Double qualidadeMin) {
//        this.qualidadeMin = qualidadeMin;
//    }
//
//    public Double getQualidadeMax() {
//        return qualidadeMax;
//    }
//
//    public void setQualidadeMax(Double qualidadeMax) {
//        this.qualidadeMax = qualidadeMax;
//    }

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

    public Long getPositionMin() {
        return positionMin;
    }

    public void setPositionMin(Long positionMin) {
        this.positionMin = positionMin;
    }

    public Long getPositionMax() {
        return positionMax;
    }

    public void setPositionMax(Long positionMax) {
        this.positionMax = positionMax;
    }

    public Set<String> getReferencias() {
        return referencias;
    }

    public void setReferencias(Set<String> referencias) {
        this.referencias = referencias;
    }

    public Set<Effect> getEffects() {
        return effects;
    }

    public void setEffects(Set<Effect> effects) {
        this.effects = effects;
    }

    public Double getPrevalenceMin() {
        return prevalenceMin;
    }

    public void setPrevalenceMin(Double prevalenceMin) {
        this.prevalenceMin = prevalenceMin;
    }

    public Double getPrevalenceMax() {
        return prevalenceMax;
    }

    public void setPrevalenceMax(Double prevalenceMax) {
        this.prevalenceMax = prevalenceMax;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    public boolean isGeneAnalyse() {
        return geneAnalyse;
    }

    public void setGeneAnalyse(boolean geneAnalyse) {
        this.geneAnalyse = geneAnalyse;
    }

    public boolean isByChromosome() {
        return byChromosome;
    }

    public void setByChromosome(boolean byChromosome) {
        this.byChromosome = byChromosome;
    }

    public boolean isByPosition() {
        return byPosition;
    }

    public void setByPosition(boolean byPosition) {
        this.byPosition = byPosition;
    }

    public boolean isByGene() {
        return byGene;
    }

    public void setByGene(boolean byGene) {
        this.byGene = byGene;
    }

    public boolean isByReference() {
        return byReference;
    }

    public void setByReference(boolean byReference) {
        this.byReference = byReference;
    }
    
    
    
    
    

    @Override
    public Filtro clone() throws CloneNotSupportedException {
        Filtro cloned = new Filtro();
        cloned.setId(id);
        cloned.setName(name);
        cloned.setAnalise(analise);
        cloned.setCromossomos(new HashSet<>(cromossomos));
        cloned.setPositionMin(positionMin);
        cloned.setPositionMax(positionMax);
        cloned.setGeneAnalyse(geneAnalyse);
        cloned.setGenes(new HashSet<>(genes));
        cloned.setUmdPredictors(new HashSet<>(umdPredictors));
        cloned.setEffects(new HashSet<>(effects));
        cloned.setByChromosome(byChromosome);
        cloned.setByPosition(byPosition);
        
        return cloned;
    }
    
     



    
    
    @Override
    public String toString() {
        return "Filtro{" + "id=" + id + ", analise=" + analise + ", genes=" + genes + ", cromossomos=" + cromossomos + ", qualidadeMin=" + "qualidadeMin" + ", qualidadeMax=" + "qualidadeMax" + ", impacto=" + impacto + ", infBiologica=" + infBiologica + '}';
    }
    
    

    
}