package org.analiseGenoma.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "vcf")
public class Vcf implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vcf")
    private Long id;
    private String nome;
    private int idadeDoPaciente;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataImportacao;
    @OneToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;
    @Enumerated(EnumType.STRING)
    private VcfStatus status;
    @OneToMany(mappedBy = "vcf")
    private List<Variante> variantes;

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

    public int getIdadeDoPaciente() {
        return idadeDoPaciente;
    }

    public void setIdadeDoPaciente(int idadeDoPaciente) {
        this.idadeDoPaciente = idadeDoPaciente;
    }

    public Date getDataImportacao() {
        return dataImportacao;
    }

    public void setDataImportacao(Date dataImportacao) {
        this.dataImportacao = dataImportacao;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public VcfStatus getStatus() {
        return status;
    }

    public void setStatus(VcfStatus status) {
        this.status = status;
    }

    public List<Variante> getVariantes() {
        return variantes;
    }

    public void setVariantes(List<Variante> variantes) {
        this.variantes = variantes;
    }
    
    
}
