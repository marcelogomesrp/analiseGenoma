package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.Paciente;

public class PacienteDao extends DAO<Paciente> {

    public PacienteDao() {
        super(Paciente.class);
    }

    public List<Paciente> buscarNome(String nome) {
        try {
            Query query = manager.createQuery("SELECT p FROM Paciente p WHERE p.nome like :nome");
            query.setParameter("nome", nome);
            List<Paciente> pacientes = query.getResultList();
            return pacientes;
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
            return null;
        }
    }
}
