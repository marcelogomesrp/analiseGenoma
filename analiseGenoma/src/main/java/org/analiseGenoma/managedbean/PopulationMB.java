package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
//import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.managedbean.util.RequestParam;
import org.analiseGenoma.model.Population;
import org.analiseGenoma.service.PopulationService;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

@Named(value = "populationMB")
@ViewScoped
//@RequestScoped
public class PopulationMB implements Serializable {
    @Inject private FacesContext context;
    @Inject @RequestParam private String id;
    @Inject private PopulationService populationService;
    private Population population;
    private List<Population> list;    
    private UploadedFile uploadedFile;
    private CrudMode crudMode;
    private boolean disabledValidation;
    private String superPopulationCode;

    @PostConstruct
    public void init() {
        disabledValidation = true;
        this.defCrudModeRead();
        list = populationService.find();
        if (id != null) {
            if (!id.equals("")) {
                population = populationService.findById(Long.valueOf(id));
            }
        }

    }

    public String persiste() {
        try {
            if(!superPopulationCode.isEmpty()){
                Population sp = populationService.findByCode(superPopulationCode);
                if(sp != null){
                    population.setSuperPopulation(sp);
                }
            }
            populationService.merge(population);
            population = new Population();
            context.getExternalContext()
                    .getFlash().setKeepMessages(true);
            context.addMessage(null, new FacesMessage("It's done"));
            return "population.xhtml?faces-redirect=true";
        } catch (Exception ex) {
            context.getExternalContext()
                    .getFlash().setKeepMessages(true);
            context.addMessage(null, new FacesMessage("Fault has occurred" + ex.getMessage()));
            return null;
        }
    }

    public void atualizarMode(Population population) {
        System.out.println("Rodando o atualizar mode");
        this.population = population;
    }

    public String atualizar() {
        System.out.println("Atualizadon....." + id + " - " + this.getPopulation().toString());
        populationService.merge(this.getPopulation());
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Atualizado com sucesso"));
        return "etnia.xhtml?faces-redirect=true";
    }

    public String limpar() {
        this.defCrudModeRead();
        return "etnia.xhtml?faces-redirect=true";
    }


    public void findByExample(){
        try {
            this.list = populationService.findByExample(population);
        } catch (Exception ex) {
            Logger.getLogger(PopulationMB.class.getName()).log(Level.SEVERE, null, ex);
            list = new ArrayList<>();
        }
    }
    

    public void upload() {
        String msg = "Error";
        if (uploadedFile != null) {
            populationService.upload(uploadedFile.getContents());
            msg = "It's done";
            RequestContext.getCurrentInstance().closeDialog(msg);            
        }
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public void viewPopulationUpload() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        RequestContext.getCurrentInstance().openDialog("viewPopulationUpload", options, null);
    }

    public void onViewPopulationUpload(SelectEvent event) {
        list = populationService.find();
        String msg = (String) event.getObject();
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(msg));

    }

    
//    public List<String> completeNome(String name){
//        return populationService.findNamesByName(name);
//    }
//
//    public List<String> completeSigla(String sigla){
//        return populationService.findSiglasBySigla(sigla);
//    }
//    
//    public List<String> completeOrigem(String origem){
//        return populationService.findOrigensByOrigem(origem);
//    }

    public void defCreateMode(){
        this.disabledValidation = false;
        this.population = new Population();     
        this.defCrudModeUpdate();
        
//        Application application = context.getApplication();
//        ViewHandler viewHandler = application.getViewHandler();
//        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
//        context.setViewRoot(viewRoot);
//        context.renderResponse();
    }

    public Population getPopulation() {
        if (population == null) {
            population = new Population();
        }
        return population;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    public List<Population> getPopulations() {
        return list;
    }

    public void setPopulations(List<Population> populations) {
        this.list = populations;
    }

    public void editar(Population population) {
        this.population = population;
        this.disabledValidation = false;        
        this.defCrudModeUpdate();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isCrudModeRead(){
        return crudMode.equals(CrudMode.Read);
    }
    public void defCrudModeRead(){
        disabledValidation = true;
        crudMode = CrudMode.Read;
    }
    public boolean isCrudModeUpdate(){
        return crudMode.equals(CrudMode.Update);
    }
    public void defCrudModeUpdate(){
        crudMode = CrudMode.Update;
    }
    
    public boolean isCrudModeFind(){
        return crudMode.equals(CrudMode.Find);
    }
    public void defCrudModeFind(){
        crudMode = CrudMode.Find;
    }    

    public String getSuperPopulationCode() {
        return superPopulationCode;
    }

    public void setSuperPopulationCode(String superPopulationCode) {
        this.superPopulationCode = superPopulationCode;
    }
    

    
    
//    public boolean isCrudModeRead() {
//        return crudModeRead;
//    }
//
//    public void setCrudModeRead(boolean crudModeRead) {
//        this.crudModeRead = crudModeRead;
//    }

    public boolean isDisabledValidation() {
        return disabledValidation;
    }

    public void setDisabledValidation(boolean disabledValidation) {
        this.disabledValidation = disabledValidation;
    }
    
    public List<String> codeComplete(String code) {
        List<String> results = new ArrayList<String>();
        return populationService.findByLikeCode(code)
                .stream()
                .map(p -> p.getCode())
                .distinct()
                .collect(Collectors.toList());
                
//        
//        populationService.findByCode(query + "%").forEach(p -> results.add(p.getCode());
//        geneService.buscarNome(query + "%").forEach(p -> results.add(p.getName()));        
//        return results;
    }

//        public List<String> completeCode(String code){
//        return populationService.findByCode(code);
//    }
}


