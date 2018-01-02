package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.Usuario;

public class UsuarioDao extends DAO<Usuario> {

    public UsuarioDao() {
        super(Usuario.class);
    }

    public List<Usuario> buscarPorRevisor() {
        try {
            Query query = manager.createQuery("SELECT u FROM Usuario u WHERE u.revisor = true");
            return (List<Usuario>) query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
            return null;
        }
    }

    public List<Usuario> findRevisorByName(String name) {
        try {
            Query query = manager.createQuery("SELECT u FROM Usuario u WHERE u.revisor = true and u.nome = :nome");
             query.setParameter("nome", name);
            return (List<Usuario>) query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
            return null;
        }
    }

    


}