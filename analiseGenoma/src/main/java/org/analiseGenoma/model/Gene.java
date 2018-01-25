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
    private String name;
    //@Column(unique = true)
    private String symbol; 
    private String dbIdentifier;
    @ManyToOne
    @JoinColumn(name = "synonymou_id")
    private Gene synonymou;
    private String url;
    @ManyToOne
    @JoinColumn(name = "dbbio_id")    
    private DbBio dbbio;

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
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Gene getSynonymou() {
        return synonymou;
    }

    public void setSynonymou(Gene synonymou) {
        this.synonymou = synonymou;
    }

    public String getDbIdentifier() {
        return dbIdentifier;
    }

    public void setDbIdentifier(String dbIdentifier) {
        this.dbIdentifier = dbIdentifier;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public DbBio getDbbio() {
        return dbbio;
    }

    public void setDbbio(DbBio dbbio) {
        this.dbbio = dbbio;
    }

    @Override
    public String toString() {
        return "Gene{" + "id=" + id + ", name=" + name + ", symbol=" + symbol + ", dbIdentifier=" + dbIdentifier + ", synonymou=" + synonymou + ", url=" + url + ", dbbio=" + dbbio + '}';
    }

}
