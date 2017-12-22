package org.analiseGenoma.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
//import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.analiseGenoma.model.validator.EtniaUniqueSigla;






@Entity
@Table(name = "etnia")
public class Etnia implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_etnia")
    private Long id;
    //@NotEmpty
//    @NotEmpty(message = "not a well-formed email address")
    @NotNull
    @Column(unique = true)    
    private String sigla;
    @NotNull
    private String nome;   
    @NotNull
    private String origem;

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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    @Override
    public String toString() {
        return "Etnia{" + "id=" + id + ", nome=" + nome + ", sigla=" + sigla + ", origem=" + origem + '}';
    }


    
    
    
}
