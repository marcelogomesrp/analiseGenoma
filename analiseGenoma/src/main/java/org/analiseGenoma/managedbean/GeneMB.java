package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.managedbean.util.RequestParam;
import org.analiseGenoma.model.BancoBiologico;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.service.BancoBiologicoService;
import org.analiseGenoma.service.GeneService;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;



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
    private UploadedFile uploadedFile;
    private List<SelectItem> bds;
    @Inject
    private BancoBiologicoService bdService;
    private Long idBd;

    public List<SelectItem> getSelectGenes() {
        List<SelectItem> genesSelect = new ArrayList<SelectItem>();
        for (Gene g : geneService.buscar()) {
            genesSelect.add(new SelectItem(g.getId(), g.getSymbol() + " - " + g.getName()));
        }
        return genesSelect;
    }

    @PostConstruct
    public void init() {
        gene = new Gene();
        genes = geneService.buscar();
        bds = new ArrayList<SelectItem>();
        for(BancoBiologico bd: bdService.buscar()){
            bds.add(new SelectItem(bd.getId(), bd.getNome()));
        }
                
        
    }

    public void adicionar() {
        if (idgenenovo != null) {
            if (!idgenenovo.equals("")) {
                gene.setSynonymou(geneService.buscarPorId(Long.valueOf(idgenenovo)));
            }
        }
        geneService.adicionar(gene);
        gene = new Gene();
        idgenenovo = "";
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Cadastrado com sucesso"));

    }

    public void pesquisar() {
        //genes = geneService.buscarNome(nome);
        genes = geneService.buscarLikeSimbolo(getGene().getSymbol());
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

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public List<SelectItem> getBds() {
        return bds;
    }

    public void setBds(List<SelectItem> bds) {
        this.bds = bds;
    }
    
    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public Long getIdBd() {
        return idBd;
    }

    public void setIdBd(Long idBd) {
        this.idBd = idBd;
    }

    
    
    public void upload() {
        String msg = "Erro ao realizar o upload";
        if (uploadedFile != null) {
            //etniaService.upload(uploadedFile.getContents());
            geneService.upload(uploadedFile.getContents(), idBd);
            msg = "Importado com sucesso";
            RequestContext.getCurrentInstance().closeDialog(msg);
        }
    }

    public void viewEtniaUpload() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        RequestContext.getCurrentInstance().openDialog("viewGeneUpload", options, null);
    }

        public void onViewEtniaUpload(SelectEvent event) {
        //etnias = etniaService.buscar();
        String msg = (String) event.getObject();
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(msg));

    }
    
}
