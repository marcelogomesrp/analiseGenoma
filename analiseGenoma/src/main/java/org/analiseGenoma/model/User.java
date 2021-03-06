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

//https://github.com/algaworks/curso-jpa-hibernate/blob/master/Aulas/Cache com Infinispan

@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class User implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_user")
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private Boolean manager;
    private Boolean reviser;
    private Boolean administrator;
    private int rowsInTable;

    public User() {
        this.administrator = false;
        this.manager = false;
        this.reviser = false;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isManager() {
        return this.manager == null ? false : this.manager;
       // return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public boolean isReviser() {
        return this.reviser == null ? false : this.reviser;
        //return reviser;
    }

    public void setReviser(boolean reviser) {
        this.reviser = reviser;
    }

    public boolean isAdministrator() {
        return this.getAdministrator();
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }

    public int getRowsInTable() {
        return rowsInTable;
    }

    public void setRowsInTable(int rowsInTable) {
        this.rowsInTable = rowsInTable;
    }

    public Boolean getAdministrator() {
        return this.administrator == null ? false : this.administrator;
    }


    
    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nome=" + name + ", email=" + email + ", password=" + password + ", phone=" + phone + ", gestor=" + manager + ", revisor=" + reviser + ", administrador=" + administrator + '}';
    }
    
}


