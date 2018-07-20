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
import javax.validation.constraints.Past;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "patient")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)

public class Patient implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pacient")
    private Long id;
    private String name;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Past
    private Date birth;
    @ManyToOne
    @JoinColumn(name = "population_id")
    private Population population;
    @Column(columnDefinition="text")
    private String note;    
    private Character gender;
    private String secondId;    
    @ManyToOne
    @JoinColumn(name = "father_id")
    private Patient father;
    @ManyToOne
    @JoinColumn(name = "mother_id")
    private Patient mother;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase().trim();
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Population getPopulation() {
        return population;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public Patient getFather() {
        return father;
    }

    public void setFather(Patient father) {
        this.father = father;
    }

    public Patient getMother() {
        return mother;
    }

    public void setMother(Patient mother) {
        this.mother = mother;
    }
    
    @Override
    public String toString() {
        return "Paciente{" + "id=" + id + ", nome=" + name + ", dataNascimento=" + birth + ", etnia=" + population + ", observacao=" + note + '}';
    }
    
    
}
