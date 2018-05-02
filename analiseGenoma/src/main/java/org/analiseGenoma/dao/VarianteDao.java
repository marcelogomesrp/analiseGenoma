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
        List<Variante> retorno = new ArrayList<>();
        try {
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
            if (filtro.isByChromosome()) {
                if (this.listaHasItens(filtro.getCromossomos())) {
                    Expression<Cromossomo> parentExpression = root.get("cromossomo");
                    Predicate where = parentExpression.in(filtro.getCromossomos());
                    condicoes.add(where);
                }
            }

            if (filtro.isByPosition()) {
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
            }

            //if(!(null == filtro.getGenes())){
            if (filtro.isByGene()) {
                if (listaHasItens(filtro.getGenes())) {
                    Expression<Gene> parentExpression = root.get("gene");
                    Predicate where = parentExpression.in(filtro.getGenes());
                    condicoes.add(where);
                }
            }

            //if(!(null == filtro.getUmdPredictors())){
            if (filtro.isByUmdPredictor()) {
                if (listaHasItens(filtro.getUmdPredictors())) {
                    Expression<UmdPredictor> parentExpression = root.get("umdPredictor");
                    Predicate where = parentExpression.in(filtro.getUmdPredictors());
                    condicoes.add(where);
                }
            }

            if (filtro.isByEffect()) {
                if (listaHasItens(filtro.getEffects())) {
                    Expression<Effect> parentExpression = root.get("effect");
                    Predicate where = parentExpression.in(filtro.getEffects());
                    condicoes.add(where);
                }
            }

            if (filtro.isByReference()) {
                if (listaHasItens(filtro.getReferencias())) {
                    Expression<String> expression = root.get("referencia");
                    Predicate where = expression.in(filtro.getReferencias());
                    condicoes.add(where);
                }
            }

            if (filtro.isByChanged()) {
                if (listaHasItens(filtro.getChangeds())) {
                    Expression<String> expression = root.get("alterado");
                    Predicate where = expression.in(filtro.getChangeds());
                    condicoes.add(where);
                }
            }

            if (filtro.isByZygocity()) {
                if (listaHasItens(filtro.getZygosities())) {
                    Expression<String> expression = root.get("zygosity");
                    Predicate where = expression.in(filtro.getZygosities());
                    condicoes.add(where);
                }
            }

            if (filtro.isByAllelicDeph1()) {
                if (listaHasItens(filtro.getAlleciDeph1s())) {
                    Expression<Integer> expression = root.get("allelicDeph1");
                    Predicate where = expression.in(filtro.getAlleciDeph1s());
                    condicoes.add(where);
                }
            }

            if (filtro.isByAllelicDeph2()) {
                if (listaHasItens(filtro.getAlleciDeph2s())) {
                    Expression<Integer> expression = root.get("allelicDeph2");
                    Predicate where = expression.in(filtro.getAlleciDeph2s());
                    condicoes.add(where);
                }
            }

            if (filtro.isByHgvsc()) {
                if (listaHasItens(filtro.getHgvscs())) {
                    Expression<String> expression = root.get("hgvsC");
                    Predicate where = expression.in(filtro.getHgvscs());
                    condicoes.add(where);
                }
            }

            if (filtro.isByHgvsp()) {
                if (listaHasItens(filtro.getHgvsps())) {
                    Expression<String> expression = root.get("hgvsP");
                    Predicate where = expression.in(filtro.getHgvsps());
                    condicoes.add(where);
                }
            }

            if (filtro.isByIdSNP()) {
                if (listaHasItens(filtro.getIdSnps())) {
                    Expression<String> expression = root.get("idSNP");
                    Predicate where = expression.in(filtro.getIdSnps());
                    condicoes.add(where);
                }
            }

            if (filtro.isByFilter()) {
                if (listaHasItens(filtro.getFilters())) {
                    Expression<String> expression = root.get("filter");
                    Predicate where = expression.in(filtro.getFilters());
                    condicoes.add(where);
                }
            }

            if (filtro.isByExonIntron()) {
                if (listaHasItens(filtro.getExonintrons())) {
                    Expression<String> expression = root.get("exonIntron");
                    Predicate where = expression.in(filtro.getExonintrons());
                    condicoes.add(where);
                }
            }

            if (filtro.isByType()) {
                if (listaHasItens(filtro.getTypies())) {
                    Expression<String> expression = root.get("type");
                    Predicate where = expression.in(filtro.getTypies());
                    condicoes.add(where);
                }
            }

            if (filtro.isByImpact()) {
                if (listaHasItens(filtro.getImpacts())) {
                    Expression<String> expression = root.get("impact");
                    Predicate where = expression.in(filtro.getImpacts());
                    condicoes.add(where);
                }
            }

            if (filtro.isByCinvarSignificance()) {
                if (listaHasItens(filtro.getClinvarsignificances())) {
                    Expression<String> expression = root.get("clinvarSignificance");
                    Predicate where = expression.in(filtro.getClinvarsignificances());
                    condicoes.add(where);
                }
            }

            if (filtro.isByClinvarDisease()) {
                if (listaHasItens(filtro.getClinvardiseases())) {
                    Expression<String> expression = root.get("clinvarDisease");
                    Predicate where = expression.in(filtro.getClinvardiseases());
                    condicoes.add(where);
                }
            }

            if (filtro.isByClinvarAccession()) {
                if (listaHasItens(filtro.getClinvarAccessions())) {
                    Expression<String> expression = root.get("clinvarAccession");
                    Predicate where = expression.in(filtro.getClinvarAccessions());
                    condicoes.add(where);
                }
            }

            if (filtro.isByClinvarAlleleType()) {
                if (listaHasItens(filtro.getClinvarAlleleTypes())) {
                    Expression<String> expression = root.get("clinvarAlleleType");
                    Predicate where = expression.in(filtro.getClinvarAlleleTypes());
                    condicoes.add(where);
                }
            }

            if (filtro.isByClinvarAlleleOrigin()) {
                if (listaHasItens(filtro.getClinvarAlleleOrigins())) {
                    Expression<String> expression = root.get("clinvarAlleleOrigin");
                    Predicate where = expression.in(filtro.getClinvarAlleleOrigins());
                    condicoes.add(where);
                }
            }

            if (filtro.isBySift()) {
                if (listaHasItens(filtro.getSifts())) {
                    Expression<String> expression = root.get("sift");
                    Predicate where = expression.in(filtro.getSifts());
                    condicoes.add(where);
                }
            }

            if (filtro.isByPolyphenhdiv()) {
                if (listaHasItens(filtro.getPolyphenHdivs())) {
                    Expression<String> expression = root.get("polyphenHdiv");
                    Predicate where = expression.in(filtro.getPolyphenHdivs());
                    condicoes.add(where);
                }
            }

            if (filtro.isByPolypheHvar()) {
                if (listaHasItens(filtro.getPolyphenHvars())) {
                    Expression<String> expression = root.get("polyphenHvar");
                    Predicate where = expression.in(filtro.getPolyphenHvars());
                    condicoes.add(where);
                }
            }

            if (filtro.isByMutationTaster()) {
                if (listaHasItens(filtro.getMutationTasters())) {
                    Expression<String> expression = root.get("mutationTaster");
                    Predicate where = expression.in(filtro.getMutationTasters());
                    condicoes.add(where);
                }
            }

            if (filtro.isByLrt()) {
                if (listaHasItens(filtro.getLrts())) {
                    Expression<String> expression = root.get("lrt");
                    Predicate where = expression.in(filtro.getLrts());
                    condicoes.add(where);
                }
            }

            if (filtro.isByFeature()) {
                if (listaHasItens(filtro.getFeatures())) {
                    Expression<String> expression = root.get("feature");
                    Predicate where = expression.in(filtro.getFeatures());
                    condicoes.add(where);
                }
            }

            if (filtro.isByEnsembl()) {
                if (listaHasItens(filtro.getEnsembls())) {
                    Expression<String> expression = root.get("ensembl");
                    Predicate where = expression.in(filtro.getEnsembls());
                    condicoes.add(where);
                }
            }

            if (filtro.isByInterproDomain()) {
                if (listaHasItens(filtro.getInterproDomains())) {
                    Expression<String> expression = root.get("interproDomain");
                    Predicate where = expression.in(filtro.getInterproDomains());
                    condicoes.add(where);
                }
            }
            
            if (filtro.isByVariantStatus()) {
                if (listaHasItens(filtro.getVariantStatuses())) {
                    Expression<String> expression = root.get("variantStatus");
                    Predicate where = expression.in(filtro.getVariantStatuses());
                    condicoes.add(where);
                }
            }
            
            if (filtro.isByGenoType()) {
                if (listaHasItens(filtro.getGenoTypes())) {
                    Expression<String> expression = root.get("genoType");
                    Predicate where = expression.in(filtro.getGenoTypes());
                    condicoes.add(where);
                }
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
        } catch (Exception ex) {
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
