package org.analiseGenoma.service;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.AnaliseDao;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.FiltroDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.dao.ImpactoDao;
import org.analiseGenoma.dao.PacienteDao;
import org.analiseGenoma.dao.PatologiaDao;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.Cromossomo;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.Impact;
import org.analiseGenoma.model.Vcf;

public class AnaliseService implements Serializable {
    @Inject
    private AnaliseDao analiseDao;
    @Inject 
    private FiltroDao filtroDao;
    @Inject
    private GeneDao geneDao;
    @Inject
    private CromossomoDao cromossomoDao;
    @Inject
    private VcfService vcfService;
    @Inject PatologiaDao patologiaDao;
    @Inject PacienteDao pacienteDao;
    @Inject
    private ImpactoDao impactoDao;
    @Inject private VcfMetadataService vcfMetadataService;
    

    
    
    @Transactional
    public void adicionar(Analise analise) {
        
        if(analise.getVcfsCorrelatos() != null){
            for(Vcf vcf: analise.getVcfsCorrelatos()){
                vcfService.atualizar(vcf);
            }
        }
        // add para evitar o detached entity
        if(analise.getPatologia() != null)
            patologiaDao.atualizar(analise.getPatologia());
        if(analise.getPaciente() != null)
            pacienteDao.atualizar(analise.getPaciente());
        if(analise.getVcf() != null)
            
        
        // fim do detached entity
        if(analise.getId() == null){
            analiseDao.adicionar(analise);
        }else{
            analiseDao.atualizar(analise);
            System.out.println("Opa já tinha Id ai nao pode dar um persiste ?");
        }
        
//        Filtro filtro = new Filtro();
//        filtro.setGenes(this.getGenes(analise));
//        filtro.setCromossomos(this.getCromossomos(analise));
//        filtro.setQualidadeMin(this.buscarQualidadeMin(analise.getId()));
//        filtro.setQualidadeMax(this.buscarQualidadeMax(analise.getId()) );
//        filtro.setImpacto(this.getImpactos(analise));
//        filtro.setAnalise(analise);
//        filtroDao.adicionar(filtro);
        
          //Filtro filtro = this.makeFiltro(analise);
          //filtroDao.adicionar(filtro);
    
        System.out.println("Filtro adicionado com sucesso");
    }
    
    public List<Gene> getGenes(Analise analise){
        return geneDao.buscarAnalise(analise.getId());
    }
    
    private List<Cromossomo> getCromossomos(Analise analise) {
        return cromossomoDao.buscarPorAnalise(analise.getId());
    }
    
    private List<Impact> getImpactos(Analise analise){
        // aqui return impactoDao.buscarPorAnalise(analise.getId());
        return null;
    }

    @Transactional
    public void atualizar(Analise analise) {
        analiseDao.atualizar(analise);
    }

    public List<Analise> buscar() {
        return analiseDao.buscar();
    }

    public Analise buscarPorId(Long id) {
        return analiseDao.buscarPorId(id);
    }

    public Double buscarQualidadeMax(Long idAnalise) {
        return analiseDao.buscarQualidadeMax(idAnalise);
    }
    
    public Double buscarQualidadeMin(Long idAnalise) {
        return analiseDao.buscarQualidadeMin(idAnalise);
    }

    public List<Cromossomo> buscarCromossomos(Long idAnalise) {
        return analiseDao.buscarCromossomos(idAnalise);
    }

    public List<Impact> buscarImpactos(Long idAnalise) {
        return analiseDao.buscarImpactos(idAnalise);
    }    


    

 

}
