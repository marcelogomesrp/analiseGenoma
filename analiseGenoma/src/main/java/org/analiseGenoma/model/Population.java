package org.analiseGenoma.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
//import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

//http://www.internationalgenome.org/category/population/
@Entity
@Table(name = "population")
public class Population implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_population")
    private Long id;
    //@NotEmpty
//    @NotEmpty(message = "not a well-formed email address")
    @NotNull
    @Column(unique = true)
    private String code;
    //@NotNull
    private String description;
    @ManyToOne
    @JoinColumn(name="superpopulation_id")
    private Population superPopulation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Population getSuperPopulation() {
        return superPopulation;
    }

    public void setSuperPopulation(Population superPopulation) {
        this.superPopulation = superPopulation;
    }

}
