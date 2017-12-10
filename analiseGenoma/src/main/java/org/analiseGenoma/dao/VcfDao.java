package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.Paciente;
import org.analiseGenoma.model.Vcf;

public class VcfDao extends DAO<Vcf> {

    public VcfDao() {
        super(Vcf.class);
    }

    public List<Vcf> buscarPacienteId(Long id) {
        try {
            Query query = manager.createQuery("SELECT v FROM Vcf v WHERE v.paciente.id like :id");
            query.setParameter("id", id);
            List<Vcf> pacientes = query.getResultList();
            return pacientes;
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
            return null;
        }

    }
}