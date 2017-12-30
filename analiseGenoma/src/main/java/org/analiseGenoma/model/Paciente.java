package org.analiseGenoma.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name = "paciente")
public class Paciente implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente")
    private Long id;
    @NotEmpty
    @NotNull
    @Column(nullable = false)  
    //@PatientUniqueName(message = "Name already exists")
    //@Obrigatorio(message = "You need informe the name")
    private String nome;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Past
    private Date dataNascimento;
    @ManyToOne
    @JoinColumn(name = "etnia_id")
    private Etnia etnia;
    @Column(columnDefinition="text")
    private String observacao;    
    private Character gender;
    private String secondId;    
    @ManyToOne
    @JoinColumn(name = "father_id")
    private Paciente father;
    @ManyToOne
    @JoinColumn(name = "mother_id")
    private Paciente mother;

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
//        if(etnia == null){
//            etnia = new Etnia();
//        }
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

    public Paciente getFather() {
        return father;
    }

    public void setFather(Paciente father) {
        this.father = father;
    }

    public Paciente getMother() {
        return mother;
    }

    public void setMother(Paciente mother) {
        this.mother = mother;
    }
    
    @Override
    public String toString() {
        return "Paciente{" + "id=" + id + ", nome=" + nome + ", dataNascimento=" + dataNascimento + ", etnia=" + etnia + ", observacao=" + observacao + '}';
    }
    
    
}
