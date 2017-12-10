package org.analiseGenoma.dao;

public class ImpactoDao {}
//extends DAO<Impact> {
/*
    public ImpactoDao() {
        super(Impact.class);
    }

    public List<Impact> buscarNome(String nome) {
        List<Impact> list = null;
        try {
            Query query = manager.createQuery("SELECT i FROM Impacto i WHERE i.nome like :nome");
            query.setParameter("nome", nome);
            list = query.getResultList();            
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());            
        }
        return list;
    }

    public List<Impact> buscarPorAnalise(Long analiseId) {
        List<Impact> list = null;
        try {
            Query query = manager.createQuery("SELECT DISTINCT v.impacto from Variante v WHERE v.vcf.id in (SELECT a.vcf.id FROM Analise a WHERE a.id = :analiseId)");
            query.setParameter("analiseId", analiseId);
            list = query.getResultList();

        } catch (NoResultException ex) {
            System.out.println("Opa deu erro na busca de de impacto por analise ::" + ex.getMessage());
        }
        return list;
    }*/
    
//}
