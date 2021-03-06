package org.analiseGenoma.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "zygosity")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Zygosity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zygosity")
    private Long id;
    @Column(unique = true)
    private String name;

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
        this.name = name.toUpperCase();
    }

    @Override
    public String toString() {
        return "Zygosity{" + "id=" + id + ", name=" + name + '}';
    }
        
}
