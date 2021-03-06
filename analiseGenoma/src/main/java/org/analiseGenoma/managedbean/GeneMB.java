package org.analiseGenoma.managedbean;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.managedbean.util.ColumnModel;
import org.analiseGenoma.managedbean.util.RequestParam;
import org.analiseGenoma.model.DbBio;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.GeneDbBio;
import org.analiseGenoma.model.GeneSymbolSynonym;
import org.analiseGenoma.service.BancoBiologicoService;
import org.analiseGenoma.service.GeneService;
import org.analiseGenoma.service.util.Line;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
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
    private DbBio dbBio;
    private List<String> symbolSynonyms;
    private List<String> nameSynonyms;
    private GeneDbBio geneDbBio;
    private List<ColumnModel> columns;
    
    
    
        private void addDBColumn() {
    
        columns = new ArrayList<ColumnModel>();
        //columns.add(new ColumnModel(columnKey.toUpperCase(), columnKey));
//        columns.add(new ColumnModel("Omin", "Omin"));
//        columns.add(new ColumnModel("NCBI", "ncbi"));
//        columns.add(new ColumnModel("Oubrobd", "OutroBd"));
        //Integer x = 0;
        for (DbBio bd : bdService.buscar()) {
            //columns.add(new ColumnModel(bd.getName(), (x++).toString()));
            columns.add(new ColumnModel(bd.getName(), bd.getId().toString() ));
        }
    }
    
    

//    public List<SelectItem> getSelectGenes() {
//        List<SelectItem> genesSelect = new ArrayList<SelectItem>();
//        for (Gene g : geneService.buscar()) {
//            genesSelect.add(new SelectItem(g.getId(), g.getSymbol() + " - " + g.getName()));
//        }
//        return genesSelect;
//    }
    public List<SelectItem> getSelectDbbios() {
        List<SelectItem> select = new ArrayList<>();
        for (DbBio dbbio : bdService.buscar()) {
            //select.add(new SelectItem(dbbio.getId(), dbbio.getName()));
            select.add(new SelectItem(dbbio, dbbio.getName()));
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
        for (DbBio bd : bdService.buscar()) {
            bds.add(new SelectItem(bd.getId(), bd.getName()));
        }

    }

    public void reload() {
        gene = new Gene();
        geneDbBio = new GeneDbBio();
        genes = geneService.find();
        symbolSynonyms = new ArrayList<>();
        nameSynonyms = new ArrayList<>();
        this.addDBColumn();

    }

    public void add() {
        try {
            geneService.persiste(gene, symbolSynonyms, nameSynonyms, geneDbBio);
            this.reload();
            context.getExternalContext()
                    .getFlash().setKeepMessages(true);
            context.addMessage(null, new FacesMessage("It's done"));
        } catch (Exception ex) {
            Logger.getLogger(GeneMB.class.getName()).log(Level.SEVERE, null, ex);
            context.getExternalContext()
                    .getFlash().setKeepMessages(true);
            context.addMessage(null, new FacesMessage("Error: " + ex.getMessage() ));
        }

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

    public DbBio getDbBio() {
        return dbBio;
    }

    public void setDbBio(DbBio dbBio) {
        this.dbBio = dbBio;
    }

    public GeneDbBio getGeneDbBio() {
        return geneDbBio;
    }

    public void setGeneDbBio(GeneDbBio geneDbBio) {
        this.geneDbBio = geneDbBio;
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnModel> columns) {
        this.columns = columns;
    }

    
    
   private Gene makeGene(Line ln) {
        Gene g = new Gene();
        g.setSymbol(ln.getField(1));
        g.setName(ln.getField(2));
        if (ln.getSize() > 3) {
            if (!(ln.getField(3).isEmpty())) {
                String synonymous[] = ln.getField(3).split(",");
                for (String s : synonymous) {
                    g.addGeneSymbolSynonymous(s);
                }
            }
        }
        if (ln.getSize() > 4) {
            if (!(ln.getField(4).isEmpty())) {
                String synonymous[] = ln.getField(4).split(",");
                for (String s : synonymous) {
                    g.addGeneSymbolSynonymous(s);
                }
            }
        }

        return g;
    }
        
    public void upload() {
        String msg = "Erro ao realizar o upload";
        if (uploadedFile != null) {
            geneService.upload(uploadedFile.getContents(), dbBio);        
//            CSVReader csv = new CSVReader(uploadedFile.getContents());
//            int x=0;
//            for (Line ln : csv.getFile()) {
//            //for(x=0;x<30;x++){
//                Gene g = this.makeGene(ln);
//                //Gene g = new Gene();
//                //g.setSymbol("g" + x++);
//                //g.setName("n" + x++);
//                
//                
//                try {
//                    geneService.persiste(g);
//                } catch (Exception ex) {
//                    Logger.getLogger(GeneMB.class.getName()).log(Level.SEVERE, null, ex);
//                    System.out.println("Erro no GeneMB.upload");
//                }
//            }
            
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

    public StreamedContent getFileXMLExample() {

        InputStream stream = geneService.findAsXML();
        StreamedContent file;
        file = new DefaultStreamedContent(stream, "application/xml", "gene.xml");
        return file;
    }

    public void findBySymbol() {
        gene = geneService.findBySymbol(gene.getSymbol());
    }

    public void edit(Gene gene){
        this.gene = gene;
    }
    
    public String bdInfo(String bdId, Long geneId) {
        GeneDbBio g = null;
        try{
        g = geneService.findByDbIdGeneId(bdId,geneId);
        }catch(Exception ex){
            System.out.println("Erro: " + ex.getMessage());
            return "Erro no mb: " + ex.getMessage();
        }
        if(g == null){
            //return "nao tem: " +bdId +  ": " + geneId;
            return "";
            
        }else{
            return g.getUrl();
        }
    }
    
    public String idDB(String bdId, Long geneId) {
        GeneDbBio g = null;
        try{
        g = geneService.findByDbIdGeneId(bdId,geneId);
        }catch(Exception ex){
            System.out.println("Erro: " + ex.getMessage());
            return "Erro no mb: " + ex.getMessage();
        }
        if(g == null){
            //return bdId +  ": " + geneId;
            //return "nao tem: " +bdId +  ": " + geneId;
            return "";
        }else{
            return g.getDbIdentifier();
        }
    }
    
    
    public String synonyms(Gene gene){
        String r = "";        
        for(GeneSymbolSynonym s: gene.getGeneSymbolSynonym()){
            r = r + s.getSymbol() + " ";
        }
        return r;
    }
}
