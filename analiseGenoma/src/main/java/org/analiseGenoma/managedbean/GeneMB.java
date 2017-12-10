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
import org.analiseGenoma.managedbean.util.RequestParam;
import org.analiseGenoma.model.Etnia;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.service.GeneService;

@Named(value = "geneMB")
@RequestScoped
public class GeneMB implements Serializable {
    
    @Inject
    private GeneService geneService;
    private Gene gene;
    @Inject
    private FacesContext context;
    @Inject
    @RequestParam
    private String idgenenovo;
    private List<Gene> genes;
    private String nome;
    
    
    public List<SelectItem> getSelectGenes() {
        List<SelectItem> genesSelect = new ArrayList<SelectItem>();
        for (Gene g : geneService.buscar()) {
            genesSelect.add(new SelectItem(g.getId(), g.getSimbolo() + " - " + g.getNome()));
        }
        return genesSelect;
    }            
    
    @PostConstruct
    public void init(){
        gene = new Gene();
        genes = geneService.buscar();
    }
    
    public void adicionar(){
        if(idgenenovo != null){
            if(!idgenenovo.equals("")){
                gene.setNovoGene(geneService.buscarPorId(Long.valueOf(idgenenovo)));
            }
        }
        geneService.adicionar(gene);
        gene = new Gene();
        idgenenovo = "";
                context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Cadastrado com sucesso"));
        
    }
    
    public void pesquisar(){
        //genes = geneService.buscarNome(nome);
        genes = geneService.buscarLikeSimbolo(getGene().getSimbolo());
    }

    public Gene getGene() {
        return gene;
    }

    public void setGene(Gene gene) {
        this.gene = gene;
    }

    public String getIdgenenovo() {
        return idgenenovo;
    }

    public void setIdgenenovo(String idgenenovo) {
        this.idgenenovo = idgenenovo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Gene> getGenes() {
        return genes;
    }

    public void setGenes(List<Gene> genes) {
        this.genes = genes;
    }
    
    
    

    
}
