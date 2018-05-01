package org.analiseGenoma.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.analiseGenoma.model.ClinvarAccession;
import org.analiseGenoma.model.ClinvarAlleleOrigin;
import org.analiseGenoma.model.ClinvarAlleleType;
import org.analiseGenoma.model.ClinvarDisease;
import org.analiseGenoma.model.ClinvarSignificance;
import org.analiseGenoma.model.Cromossomo;
import org.analiseGenoma.model.Effect;
import org.analiseGenoma.model.Ensembl;
import org.analiseGenoma.model.Feature;
import org.analiseGenoma.model.Filter;
import org.analiseGenoma.model.Filtro;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.GenoType;
import org.analiseGenoma.model.Impact;
import org.analiseGenoma.model.InterproDomain;
import org.analiseGenoma.model.Lrt;
import org.analiseGenoma.model.MutationTaster;
import org.analiseGenoma.model.PolyphenHdiv;
import org.analiseGenoma.model.PolyphenHvar;
import org.analiseGenoma.model.Sift;
import org.analiseGenoma.model.Type;
import org.analiseGenoma.model.UmdPredictor;
import org.analiseGenoma.model.VariantStatus;
import org.analiseGenoma.model.Zygosity;

public class FiltroDao extends DAO<Filtro> {

    @Inject
    private CromossomoDao cromossomoDao;

    public FiltroDao() {
        super(Filtro.class);
    }

//    @Override
//    public Filtro findById(Long id) {
//        //return super.findById(id); //To change body of generated methods, choose Tools | Templates.
//         CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
//        CriteriaQuery<Filtro> criteriaQuery = criteriaBuilder.createQuery(Filtro.class);
//        Root<Filtro> root = criteriaQuery.from(Filtro.class);
//        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));
//        
//        root.fetch("cromossomos", JoinType.LEFT);
//        root.fetch("genes", JoinType.LEFT);
//        root.fetch("umdPredictors", JoinType.LEFT);
//        root.fetch("effects", JoinType.LEFT);
//        root.fetch("zygosities", JoinType.LEFT);
//        
//        //return manager.createQuery(criteriaQuery).getResultList();
//        return manager.createQuery(criteriaQuery).getSingleResult();
//    }
    @Override
    public Filtro findById(Long id) {
        //return super.findById(id); //To change body of generated methods, choose Tools | Templates.
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Filtro> criteriaQuery = criteriaBuilder.createQuery(Filtro.class);
        Root<Filtro> root = criteriaQuery.from(Filtro.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));

//        root.fetch("cromossomos", JoinType.LEFT);
//        root.fetch("genes", JoinType.LEFT);
//        root.fetch("umdPredictors", JoinType.LEFT);
//        root.fetch("effects", JoinType.LEFT);
//        root.fetch("zygosities", JoinType.LEFT);
        //return manager.createQuery(criteriaQuery).getResultList();
        Filtro retorno = manager.createQuery(criteriaQuery).getSingleResult();

        return retorno;
    }

    //JOIN FETCH o.items i WHERE o.id = :id");
    public Filtro buscarPorAnalise(Long idAnalise) {
        try {
            //Query query = manager.createQuery("SELECT f FROM Filtro f JOIN FETCH f.genes WHERE f.analise.id = :idAnalise ");
            //Query query = manager.createQuery("SELECT f FROM Filtro f WHERE f.analise.id = :idAnalise ");
            //Query query = manager.createQuery("SELECT f FROM Filtro f JOIN FETCH f.umdPredictors WHERE f.analise.id = :idAnalise");
            Query query = manager.createQuery("SELECT f FROM Filtro f LEFT JOIN FETCH f.umdPredictors WHERE f.analise.id = :idAnalise");
            //Query query = manager.createQuery("SELECT f FROM Filtro f WHERE f.analise.id = :idAnalise");
            query.setParameter("idAnalise", idAnalise);

            Filtro filtro = (Filtro) query.getSingleResult();
            //filtro.getCromossomos().size();
            return filtro;
        } catch (Exception ex) {
            System.out.println("Erro:: " + ex.getMessage());
            return null;
        }
    }

    public Set<Gene> buscarGene(Long idFiltro) {
        Query query = manager.createQuery("SELECT f.genes FROM Filtro f WHERE f.id = :idFiltro");
        query.setParameter("idFiltro", idFiltro);
        List<Gene> genes = query.getResultList();
        return new HashSet<>(genes);
    }

    public Set<Effect> buscarEffect(Long idFiltro) {
        Query query = manager.createQuery("SELECT f.effects FROM Filtro f WHERE f.id = :idFiltro");
        query.setParameter("idFiltro", idFiltro);
        List<Effect> effects = query.getResultList();
        return new HashSet<>(effects);
    }

    public Set<Cromossomo> buscarCromossomos(Long idFiltro) {
        Query query = manager.createQuery("SELECT f.cromossomos FROM Filtro f WHERE f.id = :idFiltro");
        query.setParameter("idFiltro", idFiltro);
        List<Cromossomo> list = query.getResultList();
        return new HashSet(list);
    }

//    public Set<String> buscarHgvsC(Long idFiltro){
//        //aqui
//        Query query = manager.createQuery("SELECT f.hgvscs FROM Filtro f WHERE f.id = :idFiltro");
//        query.setParameter("idFiltro", idFiltro);
//        List<String> list = query.getResultList();
//        return new HashSet(list);
//    }
//    @Override
//    public void adicionar(Filtro filtro) {
//        if(filtro.getCromossomos() != null){
//            for(Cromossomo f:filtro.getCromossomos()){
//                cromossomoDao.atualizar(f);
//            }
//        }
//        super.adicionar(filtro);
//    }
    public Set<Cromossomo> findCromossomoFromFiltro(Filtro filtro) {
        Query query = manager.createQuery("SELECT f.cromossomos FROM Filtro f WHERE f.id = :idFiltro");
        query.setParameter("idFiltro", filtro.getId());
        List<Cromossomo> list = query.getResultList();
        return new HashSet(list);
    }

    public Set<UmdPredictor> findUmdPerdictors(Filtro filtro) {
        Query query = manager.createQuery("SELECT f.umdPredictors FROM Filtro f WHERE f.id = :idFiltro");
        query.setParameter("idFiltro", filtro.getId());
        List<UmdPredictor> list = query.getResultList();
        return new HashSet(list);
    }

    public Set<Zygosity> buscarZygocity(Filtro filtro) {
        Query query = manager.createQuery("SELECT f.zygosities FROM Filtro f WHERE f.id = :idFiltro");
        query.setParameter("idFiltro", filtro.getId());
        List<Zygosity> list = query.getResultList();
        return new HashSet(list);
    }

//    public Set<Integer> buscarAllelicDeph1(Filtro filtro) {
//        List<Integer> list = new ArrayList<>();
//        try {
//            Query query = manager.createQuery("SELECT f.alleciDeph1s FROM Filtro f WHERE f.id = :idFiltro");
//            query.setParameter("idFiltro", filtro.getId());
//            list = query.getResultList();
//        } catch (Exception ex) {
//            System.out.println("Erro no filtroDao.buscarAllelicDeph1: " + ex.getMessage());
//        }
//        return new HashSet(list);
//    }
//    
//    public Set<Integer> buscarAllelicDeph2(Filtro filtro) {
//        Query query = manager.createQuery("SELECT f.alleciDeph2s FROM Filtro f WHERE f.id = :idFiltro");
//        query.setParameter("idFiltro", filtro.getId());
//        List<Integer> list = query.getResultList();
//        return new HashSet(list);
//    }
    public Set<Filter> buscarFilter(Filtro filtro) {
        Query query = manager.createQuery("SELECT f.filters FROM Filtro f WHERE f.id = :idFiltro");
        query.setParameter("idFiltro", filtro.getId());
        List<Filter> list = query.getResultList();
        return new HashSet(list);
    }

    public Set<Impact> buscarImpact(Filtro filtro) {
        Query query = manager.createQuery("SELECT f.impacts FROM Filtro f WHERE f.id = :idFiltro");
        query.setParameter("idFiltro", filtro.getId());
        List<Impact> list = query.getResultList();
        return new HashSet(list);
    }

    public Set<UmdPredictor> buscarUmdPredictor(Filtro filtro) {
        Query query = manager.createQuery("SELECT f.umdPredictors FROM Filtro f WHERE f.id = :idFiltro");
        query.setParameter("idFiltro", filtro.getId());
        List<UmdPredictor> list = query.getResultList();
        return new HashSet(list);
    }

    public Set<Zygosity> buscarUmdZygosities(Filtro filtro) {
        Query query = manager.createQuery("SELECT f.zygosities FROM Filtro f WHERE f.id = :idFiltro");
        query.setParameter("idFiltro", filtro.getId());
        List<Zygosity> list = query.getResultList();
        return new HashSet(list);
    }

    public Set<Type> buscarUmdTypies(Filtro filtro) {
        Query query = manager.createQuery("SELECT f.typies FROM Filtro f WHERE f.id = :idFiltro");
        query.setParameter("idFiltro", filtro.getId());
        List<Type> list = query.getResultList();
        return new HashSet(list);
    }

    public Set<ClinvarSignificance> buscarClinvarSignificance(Filtro filtro) {
        Query query = manager.createQuery("SELECT f.clinvarsignificances FROM Filtro f WHERE f.id = :idFiltro");
        query.setParameter("idFiltro", filtro.getId());
        List<ClinvarSignificance> list = query.getResultList();
        return new HashSet(list);
    }

    public Set<ClinvarDisease> buscarClinvarDisease(Filtro filtro) {
        Query query = manager.createQuery("SELECT f.clinvardiseases FROM Filtro f WHERE f.id = :idFiltro");
        query.setParameter("idFiltro", filtro.getId());
        List<ClinvarDisease> list = query.getResultList();
        return new HashSet(list);
    }

    public Set<ClinvarAccession> buscarClinvarAccession(Filtro filtro) {
        Query query = manager.createQuery("SELECT f.clinvarAccessions FROM Filtro f WHERE f.id = :idFiltro");
        query.setParameter("idFiltro", filtro.getId());
        List<ClinvarAccession> list = query.getResultList();
        return new HashSet(list);
    }

    public Set<ClinvarAlleleType> buscarClinvarAlleleTypes(Filtro filtro) {
        Query query = manager.createQuery("SELECT f.clinvarAlleleTypes FROM Filtro f WHERE f.id = :idFiltro");
        query.setParameter("idFiltro", filtro.getId());
        List<ClinvarAlleleType> list = query.getResultList();
        return new HashSet(list);
    }

    public Set<ClinvarAlleleOrigin> buscarClinvarAlleleOrigins(Filtro filtro) {
        Query query = manager.createQuery("SELECT f.clinvarAlleleOrigins FROM Filtro f WHERE f.id = :idFiltro");
        query.setParameter("idFiltro", filtro.getId());
        List<ClinvarAlleleOrigin> list = query.getResultList();
        return new HashSet(list);
    }

    public Set<Sift> buscarSift(Filtro filtro) {
        Query query = manager.createQuery("SELECT f.sifts FROM Filtro f WHERE f.id = :idFiltro");
        query.setParameter("idFiltro", filtro.getId());
        List<Sift> list = query.getResultList();
        return new HashSet(list);
    }

    public Set<PolyphenHdiv> buscarPolyphenHdiv(Filtro filtro) {
        Query query = manager.createQuery("SELECT f.polyphenHdivs FROM Filtro f WHERE f.id = :idFiltro");
        query.setParameter("idFiltro", filtro.getId());
        List<PolyphenHdiv> list = query.getResultList();
        return new HashSet(list);
    }

    public Set<PolyphenHvar> buscarPolyphenHvar(Filtro filtro) {
        Query query = manager.createQuery("SELECT f.polyphenHvars FROM Filtro f WHERE f.id = :idFiltro");
        query.setParameter("idFiltro", filtro.getId());
        List<PolyphenHvar> list = query.getResultList();
        return new HashSet(list);
    }

    public Set<MutationTaster> buscarMutationTaster(Filtro filtro) {
        Query query = manager.createQuery("SELECT f.mutationTasters FROM Filtro f WHERE f.id = :idFiltro");
        query.setParameter("idFiltro", filtro.getId());
        List<MutationTaster> list = query.getResultList();
        return new HashSet(list);
    }

    public Set<Lrt> buscarLrt(Filtro filtro) {
        Query query = manager.createQuery("SELECT f.lrts FROM Filtro f WHERE f.id = :idFiltro");
        query.setParameter("idFiltro", filtro.getId());
        List<Lrt> list = query.getResultList();
        return new HashSet(list);
    }

    public Set<Feature> buscarFeature(Filtro filtro) {
        Query query = manager.createQuery("SELECT f.features FROM Filtro f WHERE f.id = :idFiltro");
        query.setParameter("idFiltro", filtro.getId());
        List<Feature> list = query.getResultList();
        return new HashSet(list);
    }
    
    public Set<Ensembl> buscarEnsembl(Filtro filtro) {
        Query query = manager.createQuery("SELECT f.ensembls FROM Filtro f WHERE f.id = :idFiltro");
        query.setParameter("idFiltro", filtro.getId());
        List<Ensembl> list = query.getResultList();
        return new HashSet(list);
    }
    
    public Set<InterproDomain> buscarInterproDomain(Filtro filtro) {
        Query query = manager.createQuery("SELECT f.interproDomains FROM Filtro f WHERE f.id = :idFiltro");
        query.setParameter("idFiltro", filtro.getId());
        List<InterproDomain> list = query.getResultList();
        return new HashSet(list);
    }
    
    public Set<VariantStatus> buscarVariantStatus(Filtro filtro) {
        Query query = manager.createQuery("SELECT f.variantStatuses FROM Filtro f WHERE f.id = :idFiltro");
        query.setParameter("idFiltro", filtro.getId());
        List<VariantStatus> list = query.getResultList();
        return new HashSet(list);
    }
    
    public Set<GenoType> buscarGenoType(Filtro filtro) {
        Query query = manager.createQuery("SELECT f.genoTypes FROM Filtro f WHERE f.id = :idFiltro");
        query.setParameter("idFiltro", filtro.getId());
        List<GenoType> list = query.getResultList();
        return new HashSet(list);
    }
    


}
