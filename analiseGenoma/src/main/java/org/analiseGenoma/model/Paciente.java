package org.analiseGenoma.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "paciente")
public class Paciente implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente")
    private Long id;
    private String nome;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataNascimento;
    @OneToOne
    @JoinColumn(name = "etnia_id")
    private Etnia etnia;
    @Column(columnDefinition="text")
    private String observacao;    
    private Character gender;
    private String secondId;

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

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Etnia getEtnia() {
        if(etnia == null){
            etnia = new Etnia();
        }
        return etnia;
    }

    public void setEtnia(Etnia etnia) {
        this.etnia = etnia;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public String getSecondId() {
        return secondId;
    }

    public void setSecondId(String secondId) {
        this.secondId = secondId;
    }
    
    
    @Override
    public String toString() {
        return "Paciente{" + "id=" + id + ", nome=" + nome + ", dataNascimento=" + dataNascimento + ", etnia=" + etnia + ", observacao=" + observacao + '}';
    }
    
    
}
