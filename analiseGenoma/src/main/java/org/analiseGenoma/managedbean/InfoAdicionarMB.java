package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.model.DbBio;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.InformacaoBiologica;
import org.analiseGenoma.model.Disease;
import org.analiseGenoma.service.BancoBiologicoService;
import org.analiseGenoma.service.GeneService;
import org.analiseGenoma.service.InformacaoBiologicaService;
import org.analiseGenoma.service.PatologiaService;

@Named(value = "infoAdicionarMB")
@RequestScoped
public class InfoAdicionarMB implements Serializable {

    @Inject
    private BancoBiologicoService bdBioService;
    @Inject
    private PatologiaService patologiaService;
    @Inject
    private GeneService geneService;
    @Inject
    private InformacaoBiologicaService infoBioService;    
    @Inject
    private FacesContext context;
    private InformacaoBiologica infoBio;
    private String idBd;    
    private String patologia;
    private String gene;

    @PostConstruct
    public void init() {
        infoBio = new InformacaoBiologica();
    }

    public void adicionar() {
        System.out.println("Adicionando informacoes");
        if(idBd!= null){
            infoBio.setBdBio(bdBioService.buscarPorId(Long.valueOf(idBd)));            
        }
        if(patologia != null){
            List<Disease> patologias = patologiaService.buscarNome(patologia);
            if(patologias.size() > 0)
                infoBio.setPatologia(patologias.get(0));
        }
        
        if(gene != null){
            infoBio.setGene(geneService.buscarNovoNome(gene));
        }
        
        infoBioService.adicionar(infoBio);     
        idBd = "";
        patologia = "";
        gene = "";
        infoBio = new InformacaoBiologica();
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Cadastrado com sucesso"));
    }


    public List<SelectItem> getSelectBds() {
        List<SelectItem> bds = new ArrayList<SelectItem>();
        for (DbBio bd : bdBioService.buscar()) {
            bds.add(new SelectItem(bd.getId(), bd.getName()));
        }
        return bds;
    }
    
    
    public List<String> patologiaComplete(String query) {
        List<String> results = new ArrayList<String>();
        patologiaService.buscarNome(query + "%").forEach(p -> results.add(p.getName()));        
        return results;
    }

    public List<String> geneComplete(String query) {
        List<String> results = new ArrayList<String>();
        geneService.buscarNome(query + "%").forEach(p -> results.add(p.getName()));        
        return results;
    }
    
    public String getIdBd() {
        return idBd;
    }

    public void setIdBd(String idBd) {
        this.idBd = idBd;
    }

    public String getPatologia() {
        return patologia;
    }

    public void setPatologia(String patologia) {
        this.patologia = patologia;
    }

    public String getGene() {
        return gene;
    }

    public void setGene(String gene) {
        this.gene = gene;
    }
    
}
