package org.analiseGenoma.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "patologia")
public class Patologia implements Serializable{        
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_patologia")
    @Id
    private Long id;
    private String nome;
    private String cid;

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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }



    @Override
    public String toString() {
        return "Patologia{" + "id=" + id + ", nome=" + nome + ", cid=" + cid + '}';
    }    
    
}