package org.analiseGenoma.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "analise")
public class Analise  implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_analise")
    private Long id;
    private String nome;
    @ManyToOne
    @JoinColumn(name = "patologia_id")
    private Disease patologia;
    @Column(columnDefinition="text")
    private String observacao;
    private String estado;
    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;
    @ManyToOne
    @JoinColumn(name = "vcf_id")
    private Vcf vcf;
    @ManyToOne
    @JoinColumn(name = "pacientecontrole_id")
    private Paciente controle;
    //@ManyToOne
    //@JoinColumn(name = "vcfControle_id")
    @OneToMany
    private List<Vcf> vcfsCorrelatos;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Usuario> revisores; 
    @ManyToOne
    private Vcf vcfFather;
    @ManyToOne
    private Vcf vcfMother;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Disease getPatologia() {
        return patologia;
    }

    public void setPatologia(Disease patologia) {
        this.patologia = patologia;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Vcf getVcf() {
        return vcf;
    }

    public void setVcf(Vcf vcf) {
        this.vcf = vcf;
    }

    public List<Vcf> getVcfsCorrelatos() {
        if(vcfsCorrelatos == null){
            vcfsCorrelatos = new ArrayList<>();
        }
        return vcfsCorrelatos;
    }

    public void setVcfsCorrelatos(List<Vcf> vcfsCorrelatos) {
        this.vcfsCorrelatos = vcfsCorrelatos;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Paciente getControle() {
        return controle;
    }

    public void setControle(Paciente controle) {
        this.controle = controle;
    }

    public Set<Usuario> getRevisores() {
        return revisores;
    }

    public void setRevisores(Set<Usuario> revisores) {
        this.revisores = revisores;
    }

    public Vcf getVcfFather() {
        return vcfFather;
    }

    public void setVcfFather(Vcf vcfFather) {
        this.vcfFather = vcfFather;
    }

    public Vcf getVcfMother() {
        return vcfMother;
    }

    public void setVcfMother(Vcf vcfMother) {
        this.vcfMother = vcfMother;
    }


    
    
    @Override
    public String toString() {
        return "Analise{" + "id=" + id + ", nome=" + nome + ", patologia=" + patologia + ", observacao=" + observacao + ", estado=" + estado + ", paciente=" + paciente + ", vcf=" + vcf + ", controle=" + controle + '}';
    }
    
    
    
}
