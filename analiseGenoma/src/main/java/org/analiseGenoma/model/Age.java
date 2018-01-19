package org.analiseGenoma.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "age")
public class Age implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_age")
    private Long id;
    private String description;
    private Integer beginning;
    private Integer finish;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getBeginning() {
        return beginning;
    }

    public void setBeginning(Integer beginning) {
        this.beginning = beginning;
    }

    public Integer getFinish() {
        return finish;
    }

    public void setFinish(Integer finish) {
        this.finish = finish;
    }

    @Override
    public String toString() {
        return "Age{" + "id=" + id + ", name=" + description + ", beginning=" + beginning + ", finish=" + finish + '}';
    }
    
}