package org.analiseGenoma.service;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.PacienteDao;
import org.analiseGenoma.model.Paciente;

@Named
public class PacienteService implements Serializable{
    @Inject
    private PacienteDao pacienteDao;

    @PostConstruct
    public void init() {
    }

    @Transactional
    public void adicionar(Paciente paciente){
        pacienteDao.adicionar(paciente);
    }
    
    public List<Paciente> buscar(){
        return pacienteDao.buscar();
    }

    public List<Paciente> buscarNome(String nome) {
        return pacienteDao.buscarNome(nome);
    }

    public Paciente buscarId(Long id) {
        return pacienteDao.buscarPorId(id);
    }

    @Transactional
    public void atualizar(Paciente paciente) {
        pacienteDao.atualizar(paciente);
    }
}
