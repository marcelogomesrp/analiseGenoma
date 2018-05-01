package org.analiseGenoma.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.Cromossomo;
import org.analiseGenoma.model.Effect;
import org.analiseGenoma.model.Filtro;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.UmdPredictor;
import org.analiseGenoma.model.Variante;
import org.analiseGenoma.model.Vcf;

public class VarianteDao extends DAO<Variante> {

    public VarianteDao() {
        super(Variante.class);
    }

    public List<Variante> findByVCF(Long vcfId) {
        List<Variante> variantes = null;
        try {
            Query query = manager.createQuery("SELECT v FROM Variante v WHERE v.vcf.id = :vcfId");
            query.setParameter("vcfId", vcfId);
            variantes = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return variantes;
    }

    public List<Variante> buscarAnalise(Long vcfId) {
        List<Variante> variantes = null;
        try {
            Query query = manager.createQuery("SELECT v FROM Variante v WHERE v.vcf.id = :vcfId");
            query.setParameter("vcfId", vcfId);
            variantes = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return variantes;
    }

    /*
    public List<Gene> buscarNome(String nome) {
        try {
            Query query = manager.createQuery("SELECT g FROM Gene g WHERE g.nome like :nome");
            query.setParameter("nome", nome);
            List<Gene> genes = query.getResultList();
            return genes;
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
            return null;
        }
    }*/
    private boolean listaHasItens(List list) {
        if (list == null) {
            return false;
        }
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean listaHasItens(Set set) {
        return this.listaHasItens(new ArrayList(set));
    }

    public List<Variante> findByAnaliseFiltro(Analise analise, Filtro filtro) {
        List<Variante> retorno  = new ArrayList<>();
        try{
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Variante> criteriaQuery = criteriaBuilder.createQuery(Variante.class);
        Root<Variante> root = criteriaQuery.from(Variante.class);
        List<Predicate> condicoes = new ArrayList<>();

        if (!(null == analise.getVcf().getId())) {
            Path<Long> atributoId = root.get("vcf");
            Predicate where = criteriaBuilder.equal(atributoId, analise.getVcf().getId());
            condicoes.add(where);
        }

        //if ( (!(null == filtro.getCromossomos())) && (!(filtro.getCromossomos().isEmpty())) ) {
        if (this.listaHasItens(filtro.getCromossomos())) {
            Expression<Cromossomo> parentExpression = root.get("cromossomo");
            Predicate where = parentExpression.in(filtro.getCromossomos());
            condicoes.add(where);
        }

        if (!(null == filtro.getPositionMin())) {
            Path<Long> atributo = root.get("position");
            Predicate where = criteriaBuilder.greaterThanOrEqualTo(atributo, filtro.getPositionMin());
            condicoes.add(where);
        }

        if (!(null == filtro.getPositionMax())) {
            Path<Long> atributo = root.get("position");
            Predicate where = criteriaBuilder.lessThanOrEqualTo(atributo, filtro.getPositionMax());
            condicoes.add(where);
        }
        //if(!(null == filtro.getGenes())){
        if (listaHasItens(filtro.getGenes())) {
            Expression<Gene> parentExpression = root.get("gene");
            Predicate where = parentExpression.in(filtro.getGenes());
            condicoes.add(where);
        }

        //if(!(null == filtro.getUmdPredictors())){
        if (listaHasItens(filtro.getUmdPredictors())) {
            Expression<UmdPredictor> parentExpression = root.get("umdPredictor");
            Predicate where = parentExpression.in(filtro.getUmdPredictors());
            condicoes.add(where);
        }

        if (listaHasItens(filtro.getEffects())) {
            Expression<Effect> parentExpression = root.get("effect");
            Predicate where = parentExpression.in(filtro.getEffects());
            condicoes.add(where);
        }
        
        if(listaHasItens(filtro.getReferencias())){
            Expression<String> expression = root.get("referencia");
            Predicate where = expression.in(filtro.getReferencias());
            condicoes.add(where);
        }
        
        if(listaHasItens(filtro.getChangeds() )){
            Expression<String> expression = root.get("alterado");
            Predicate where = expression.in(filtro.getChangeds()) ;
            condicoes.add(where);
        }
        
        if(listaHasItens(filtro.getZygosities() )){
            Expression<String> expression = root.get("zygosity");
            Predicate where = expression.in(filtro.getZygosities()) ;
            condicoes.add(where);
        }
        
        if(listaHasItens(filtro.getAlleciDeph1s())){
            Expression<Integer> expression = root.get("allelicDeph1");
            Predicate where = expression.in(filtro.getAlleciDeph1s()) ;
            condicoes.add(where);
        }
        
        if(listaHasItens(filtro.getAlleciDeph2s())){
            Expression<Integer> expression = root.get("allelicDeph2");
            Predicate where = expression.in(filtro.getAlleciDeph2s()) ;
            condicoes.add(where);
        }
        
        if(listaHasItens(filtro.getHgvscs())){
            Expression<String> expression = root.get("hgvsC");
            Predicate where = expression.in(filtro.getHgvscs()) ;
            condicoes.add(where);
        }

        Predicate[] condicoesArray = condicoes.toArray(new Predicate[condicoes.size()]);
        Predicate todasCondicoes = criteriaBuilder.and(condicoesArray);
        criteriaQuery.where(todasCondicoes);
        TypedQuery<Variante> query = manager.createQuery(criteriaQuery);
        retorno = query.getResultList();

//        while ((retorno.size() == 0) && (condicoes.size() > 0)) {
//            condicoes.remove(condicoes.size() - 1);
//            condicoesArray = condicoes.toArray(new Predicate[condicoes.size()]);
//            todasCondicoes = criteriaBuilder.and(condicoesArray);
//            criteriaQuery.where(todasCondicoes);
//            query = manager.createQuery(criteriaQuery);
//            retorno = query.getResultList();
//
//        }
        }catch(Exception ex){
            System.out.println("Erro:VarianteDao.findByAnaliseFiltro: " + ex.getMessage());
        }
        return retorno;

    }

    public List<Variante> buscarAnalise(Long vcfId, Filtro filtro) {
//        List<Predicate> condicoes = new ArrayList<>();
//        if(!(null == p.getId())){
//            Path<Long> atributoId = root.get("id");
//            Predicate whereId = criteriaBuilder.equal(atributoId, p.getId());
//            condicoes.add(whereId);
//        }  
        return null;
    }

    public List<Variante> buscarAnalise2(Long vcfId, Filtro filtro) {
        List<Variante> variantes = null;

        try {
            //Query query = manager.createQuery("SELECT v FROM Variante v WHERE v.vcf.id like :vcfId and v.gene in (:genes)");
            Query query = manager.createQuery("SELECT v FROM Variante v WHERE v.vcf.id like :vcfId and v.gene in (:genes) and v.cromossomo in (:cromossomos) and v.qualidade >= :qualiMin and v.qualidade <= :qualiMax and v.impacto in (:impactos)");
            query.setParameter("vcfId", vcfId);
            query.setParameter("genes", filtro.getGenes());
            query.setParameter("cromossomos", filtro.getCromossomos());
//            query.setParameter("qualiMin", filtro.getQualidadeMin());
//            query.setParameter("qualiMax", filtro.getQualidadeMax());
            //query.setParameter("impactos", filtro.getImpacto());
            query.setParameter("impacts", filtro.getImpacts());
            variantes = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return variantes;
    }

    public List<Variante> find(Analise analise) {
        List<Variante> variantes = null;
        try {
            Query query = manager.createQuery("SELECT v FROM Variante v WHERE v.vcf.id = :vcfId ");
            query.setParameter("vcfId", analise.getVcf().getId());
            variantes = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro DAO:: " + ex.getMessage());
        }
        return variantes;

    }

    public List<Variante> findByExample(Variante variante) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Variante> criteriaQuery = criteriaBuilder.createQuery(Variante.class);
        Root<Variante> root = criteriaQuery.from(Variante.class);
        List<Predicate> condicoes = new ArrayList<Predicate>();
        if (!(null == variante.getId())) {
            Path<Long> atributoId = root.get("id");
            Predicate whereId = criteriaBuilder.equal(atributoId, variante.getId());
            condicoes.add(whereId);
        }

        if (!(null == variante.getPosition() || "".equals(variante.getPosition()))) {
            Path<Long> atributo = root.get("position");
            Predicate where = criteriaBuilder.equal(atributo, variante.getPosition());
            condicoes.add(where);
        }

        if (!(null == variante.getCromossomo() || "".equals(variante.getCromossomo()))) {
            Path<Cromossomo> atributo = root.get("cromossomo");
            Predicate where = criteriaBuilder.equal(atributo, variante.getCromossomo());
            condicoes.add(where);
        }

        if (!(null == variante.getVcf() || "".equals(variante.getVcf()))) {
            Path<Vcf> atributo = root.get("vcf");
            Predicate where = criteriaBuilder.equal(atributo, variante.getVcf());
            condicoes.add(where);
        }

        Predicate[] condicoesArray = condicoes.toArray(new Predicate[condicoes.size()]);
        Predicate todasCondicoes = criteriaBuilder.and(condicoesArray);
        criteriaQuery.where(todasCondicoes);
        TypedQuery<Variante> query = manager.createQuery(criteriaQuery);
        return query.getResultList();
    }

}

/*

    public List<Variante> buscarAnalise(Long vcfId, Filtro filtro) {
        List<Variante> variantes = null;
        
        try {
            //Query query = manager.createQuery("SELECT v FROM Variante v WHERE v.vcf.id like :vcfId and v.gene in (:genes)");
            Query query = manager.createQuery("SELECT v FROM Variante v WHERE v.vcf.id like :vcfId and v.gene in (:genes) and v.cromossomo in (:cromossomos) and v.qualidade >= :qualiMin and v.qualidade <= :qualiMax");            
            query.setParameter("vcfId", vcfId);
            query.setParameter("genes", filtro.getGenes());
            query.setParameter("cromossomos", filtro.getCromossomos());
            query.setParameter("qualiMin", filtro.getQualidadeMin());
            query.setParameter("qualiMax", filtro.getQualidadeMax());
            variantes = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return variantes;
    }
 */
 /*
    public List<Variante> buscarAnalise(Long vcfId, Filtro filtro) {
        List<Variante> variantes = null;
        
        try {
            //Query query = manager.createQuery("SELECT v FROM Variante v WHERE v.vcf.id like :vcfId and v.gene in (:genes)");
            Query query = manager.createQuery("SELECT v FROM Variante v WHERE v.vcf.id like :vcfId and v.gene in (:genes) and v.cromossomo in (:cromossomos)");            
            query.setParameter("vcfId", vcfId);
            query.setParameter("genes", filtro.getGenes());
            query.setParameter("cromossomos", filtro.getCromossomos());
            variantes = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return variantes;
    }
 */
 /*public List<Variante> buscarAnalise(Long vcfId, Filtro filtro) {
        List<Variante> variantes = null;
        
        try {
           // String squery = "SELECT v FROM Variante v WHERE v.vcf.id like :vcfId and v.";
            //Query query = manager.createQuery(squery);
            //Query query = manager.createQuery("SELECT v FROM Variante v WHERE v.vcf.id like :vcfId and v.gene in (:genes) and v.cromossomo in (:cromossomos)");
            String squery = "SELECT v FROM Variante v WHERE v.vcf.id like :vcfId and v.gene in (:genes) ";
            //Query query = manager.createQuery("SELECT v FROM Variante v WHERE v.vcf.id like :vcfId and v.gene in (:genes)");
            
            if(filtro.getCromossomos() != null){
                squery = squery + " and v.cromossomo in (:cromossomos)";
            }
            Query query = manager.createQuery("SELECT v FROM Variante v WHERE v.vcf.id like :vcfId and v.gene in (:genes)");
            query.setParameter("vcfId", vcfId);
            query.setParameter("genes", filtro.getGenes());
            
            if(filtro.getCromossomos() != null){
                query.setParameter("cromossomos", filtro.getCromossomos());
            }
            
            variantes = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return variantes;
    }*/
