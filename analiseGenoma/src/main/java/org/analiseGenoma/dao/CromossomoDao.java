package org.analiseGenoma.dao;

import java.util.List;
import java.util.Set;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.Cromossomo;
import org.analiseGenoma.model.Filtro;

public class CromossomoDao extends DAO<Cromossomo> {

    public CromossomoDao() {
        super(Cromossomo.class);
    }

    public Cromossomo buscarOuCriar(String nome) {
        try {
            Query query = manager.createQuery("SELECT c FROM Cromossomo c WHERE c.nome = :nome");
            query.setParameter("nome", nome);
            List<Cromossomo> list = query.getResultList();
            if (list.size() == 1) {
                return list.get(0);
            } else {
                Cromossomo cromossomo = new Cromossomo();
                cromossomo.setNome(nome);
                this.persist(cromossomo);
                return cromossomo;
            }
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
            return null;
        }
    }

    public List<Cromossomo> buscarPorAnalise(Long analiseId) {
        List<Cromossomo> list = null;
        try {
            Query query = manager.createQuery("SELECT DISTINCT v.cromossomo from Variante v WHERE v.vcf.id in (SELECT a.vcf.id FROM Analise a WHERE a.id = :analiseId)");
            query.setParameter("analiseId", analiseId);
            list = query.getResultList();

        } catch (NoResultException ex) {
            System.out.println("Opa deu erro na busca de genes::" + ex.getMessage());
        }
        return list;
    }

    public Cromossomo buscarPorNome(String nome) {
        try {
            List<Cromossomo> list = null;
            Query query = manager.createQuery("SELECT c FROM Cromossomo c WHERE c.nome = :nome");
            query.setParameter("nome", nome);
            list = query.getResultList();
            if (list.size() == 1) {
                return list.get(0);
            }
        } catch (NoResultException ex) {
            System.out.println("Erro na busca de cromossomo: " + ex.getMessage());
        }
        return null;
    }



}
