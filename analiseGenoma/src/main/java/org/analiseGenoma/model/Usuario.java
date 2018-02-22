package org.analiseGenoma.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;


@Entity
@Table(name = "usuario")
public class Usuario implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_usuario")
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private Boolean gestor;
    private Boolean revisor;
    private Boolean administrador;
//    @Lob
//    //@Column(columnDefinition = "LONGBLOB")BYTEA
//    @Column(columnDefinition = "BYTEA")
//    private byte[] image;
    

    public Usuario() {
    }

    public Usuario(Long id, String nome, String email, String senha, String telefone, boolean gestor, boolean revisor, boolean administrador) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.gestor = gestor;
        this.revisor = revisor;
        this.administrador = administrador;
    }
    
    

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public boolean isGestor() {
        return gestor;
    }

    public void setGestor(boolean gestor) {
        this.gestor = gestor;
    }

    public boolean isRevisor() {
        return revisor;
    }

    public void setRevisor(boolean revisor) {
        this.revisor = revisor;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

//    public byte[] getImage() {
//        return image;
//    }
//
//    public void setImage(byte[] image) {
//        this.image = image;
//    }

    
    
    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", telefone=" + telefone + ", gestor=" + gestor + ", revisor=" + revisor + ", administrador=" + administrador + '}';
    }
    
}


