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


@Entity
@Table(name = "gene")
public class Gene implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_gene")
    private Long id;
    @Column
    private String nome;
    @Column(unique = true)
    private String simbolo; 
    @ManyToOne
    @JoinColumn(name = "novogene_id")
    private Gene novoGene;

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

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public Gene getNovoGene() {
        return novoGene;
    }

    public void setNovoGene(Gene novoGene) {
        this.novoGene = novoGene;
    }

    @Override
    public String toString() {
        return "Gene{" + "id=" + id + ", nome=" + nome + ", simbolo=" + simbolo + ", novoGene=" + novoGene + '}';
    }
        
        
}
