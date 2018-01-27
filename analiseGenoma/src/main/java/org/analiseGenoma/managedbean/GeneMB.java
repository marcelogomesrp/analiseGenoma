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
import org.analiseGenoma.model.DbBio;
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
    private List<String> symbolSynonyms;
    private List<String> nameSynonyms;
    
    

//    public List<SelectItem> getSelectGenes() {
//        List<SelectItem> genesSelect = new ArrayList<SelectItem>();
//        for (Gene g : geneService.buscar()) {
//            genesSelect.add(new SelectItem(g.getId(), g.getSymbol() + " - " + g.getName()));
//        }
//        return genesSelect;
//    }
    
    public List<SelectItem> getSelectDbbios() {
        List<SelectItem> select = new ArrayList<SelectItem>();
        for (DbBio dbbio : bdService.buscar()) {
            select.add(new SelectItem(dbbio.getId(), dbbio.getName()));
        }
        return select;
    }

    @PostConstruct
    public void init() {
//        gene = new Gene();
//        genes = geneService.find();
//        symbolSynonyms = new ArrayList<>();
        this.reload();
        bds = new ArrayList<SelectItem>();
        for(DbBio bd: bdService.buscar()){
            bds.add(new SelectItem(bd.getId(), bd.getName()));
        }
                
    }
    
    public void reload(){
        gene = new Gene();
        genes = geneService.find();
        symbolSynonyms = new ArrayList<>();
        nameSynonyms = new ArrayList<>();
    }

    public void add() {
//        if(symbolSynonyms != null){
//            //symbolSynonyms.forEach( s -> gene.getGeneSymbolSynonym().add(new GeneSymbolSynonym(s)));
//            for(String s : symbolSynonyms){
//                gene.addGeneSymbolSynonymous(s);
//            }
//        }
        geneService.persiste(gene, symbolSynonyms, nameSynonyms);
        
        this.reload();
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("It's done"));

    }

    public void pesquisar() {
        //genes = geneService.buscarNome(nome);
        //genes = geneService.buscarLikeSimbolo(getGene().getSymbol());
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

    public List<String> getSymbolSynonyms() {
        return symbolSynonyms;
    }

    public void setSymbolSynonyms(List<String> symbolSynonyms) {
        this.symbolSynonyms = symbolSynonyms;
    }

    public List<String> getNameSynonyms() {
        return nameSynonyms;
    }

    public void setNameSynonyms(List<String> nameSynonyms) {
        this.nameSynonyms = nameSynonyms;
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

    public void viewGeneUpload() {
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
