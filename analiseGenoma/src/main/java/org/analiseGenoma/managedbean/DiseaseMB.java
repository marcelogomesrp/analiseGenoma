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
import org.analiseGenoma.model.Age;
import org.analiseGenoma.model.DbBio;
import org.analiseGenoma.model.DbBioInfo;
import org.analiseGenoma.model.Disease;
import org.analiseGenoma.model.InheritanceType;
import org.analiseGenoma.service.AgeService;
import org.analiseGenoma.service.DbBioService;
import org.analiseGenoma.service.DiseaseService;
import org.analiseGenoma.service.InheritanceTypeService;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

@Named(value = "diseaseMB")
@RequestScoped
public class DiseaseMB implements Serializable {

    @Inject
    private DiseaseService diseaseService;
    private Disease disease;
    private DbBioInfo dbBioInfo;
    private List<String> genes;
    @Inject
    private FacesContext context;
    @Inject
    @RequestParam
    private String idPatologia;
    private List<Disease> diseases;
    private String nome;
    private Long idDbbio;
    private Long idAge;
    private InheritanceType inheritance;
    @Inject
    private DbBioService bdService;
    @Inject
    private AgeService ageService;
    @Inject
    private InheritanceTypeService inheritanceService;
    private UploadedFile uploadedFile;

    @PostConstruct
    public void init() {
        disease = new Disease();
        diseases = diseaseService.find();
        genes = new ArrayList<>();
    }

    public void add() {
        disease.setInheritanceType(inheritance);
        diseaseService.adicionar(disease);
        disease = new Disease();
        diseases = diseaseService.find();
        dbBioInfo = new DbBioInfo();
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("It's done"));

    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public Long getIdDbbio() {
        return idDbbio;
    }

    public void setIdDbbio(Long idDbbio) {
        this.idDbbio = idDbbio;
    }

    public List<Disease> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<Disease> diseases) {
        this.diseases = diseases;
    }

    public Long getIdAge() {
        return idAge;
    }

    public void setIdAge(Long idAge) {
        this.idAge = idAge;
    }

    public InheritanceType getInheritance() {
        return inheritance;
    }

    public void setInheritance(InheritanceType inheritance) {
        this.inheritance = inheritance;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public DbBioInfo getDbBioInfo() {
        return dbBioInfo;
    }

    public void setDbBioInfo(DbBioInfo dbBioInfo) {
        this.dbBioInfo = dbBioInfo;
    }

    public List<String> getGenes() {
        return genes;
    }

    public void setGenes(List<String> genes) {
        this.genes = genes;
    }
    
    
    

    public List<SelectItem> getSelectDbbios() {
        List<SelectItem> select = new ArrayList<>();
        for (DbBio dbbio : bdService.find()) {
            select.add(new SelectItem(dbbio.getId(), dbbio.getName()));
        }
        return select;
    }

    public List<SelectItem> getSelectAges() {
        List<SelectItem> select = new ArrayList<>();
        for (Age age : ageService.find()) {
            select.add(new SelectItem(age.getId(), age.getDescription()));
        }
//        ageService.find()
//                .stream()
//                .map(a -> select.add(new SelectItem(a.getDescription())));
        return select;
    }

    public List<InheritanceType> completeInheritance(String query) {
        return inheritanceService.findLikeType(query);
    }

    public void onInheritanceSelect(SelectEvent event) {
        inheritance = (InheritanceType) event.getObject();

    }

    public void upload() {
        String msg = "Error";
        if (uploadedFile != null) {
            diseaseService.upload(uploadedFile.getContents(), idDbbio);
            msg = "It's done";
            RequestContext.getCurrentInstance().closeDialog(msg);
        }
    }
    
    public void viewUpload() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        RequestContext.getCurrentInstance().openDialog("viewDiseaseUpload", options, null);
    }

    public void onViewUpload(SelectEvent event) {
        //list = populationService.find();
        String msg = (String) event.getObject();
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(msg));

    }
}
