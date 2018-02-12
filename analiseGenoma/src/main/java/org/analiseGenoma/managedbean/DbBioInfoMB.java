package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.model.DbBio;
import org.analiseGenoma.model.DbBioInfo;
import org.analiseGenoma.model.Disease;
import org.analiseGenoma.service.DbBioInfoService;
import org.analiseGenoma.service.DbBioService;
import org.analiseGenoma.service.DiseaseService;
import org.analiseGenoma.service.GeneService;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

@Named(value = "dbBioInfoMB")
@ViewScoped
public class DbBioInfoMB implements Serializable {

    @Inject
    private FacesContext context;
    private DbBioInfo dbBioInfo;
    private List<DbBioInfo> list;
    private DbBio idBd;
    @Inject
    private DbBioService dbBioService;
    @Inject
    private DiseaseService diseaseService;
    @Inject
    private GeneService geneService;
    private Disease disease;
    private List<String> genes;
    private UploadedFile uploadedFile;
    @Inject
    private DbBioInfoService bioInfoService;

    public DbBioInfo getDbBioInfo() {
        return dbBioInfo;
    }

    public void setDbBioInfo(DbBioInfo dbBioInfo) {
        this.dbBioInfo = dbBioInfo;
    }

    public List<DbBioInfo> getList() {
        return list;
    }

    public void setList(List<DbBioInfo> list) {
        this.list = list;
    }

    public DbBio getIdBd() {
        return idBd;
    }

    public void setIdBd(DbBio idBd) {
        this.idBd = idBd;
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public List<String> getGenes() {
        return genes;
    }

    public void setGenes(List<String> genes) {
        this.genes = genes;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    @PostConstruct
    public void init() {
        dbBioInfo = new DbBioInfo();
        disease = new Disease();
        genes = new ArrayList<>();
    }

    @Transactional
    public void add() {
        if (idBd != null) {
            dbBioInfo.setDbBio(idBd);
        }
        dbBioInfo.setDisease(disease);
//        dbBioInfo.setGenes(new HashSet<>());
//        for (String gene : genes) {
//            dbBioInfo.getGenes().add(geneService.findBySymbolOrCreate(gene));
//        }
        dbBioService.persiste(dbBioInfo);
        dbBioInfo = new DbBioInfo();
        disease = new Disease();
        idBd = null;
        genes = new ArrayList<>();
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("It's done"));
    }

    public List<SelectItem> getSelectDbbios() {
//        List<SelectItem> select = new ArrayList<SelectItem>();
//        for (DbBio dbbio : dbBioService.find()) {
//            select.add(new SelectItem(dbbio.getId(), dbbio.getName()));
//        }
//        return select;

        List<SelectItem> select = new ArrayList<>();
        for (DbBio dbbio : dbBioService.find()) {
            //select.add(new SelectItem(dbbio.getId(), dbbio.getName()));
            select.add(new SelectItem(dbbio, dbbio.getName()));
        }
        return select;

    }

    
    
    
    public List<Disease> completeDisease(String query) {
        return diseaseService.findLikeName(query);
    }

    public void upload() {
        String msg = "Error";
        if (uploadedFile != null) {
            bioInfoService.uploadInfo(uploadedFile.getContents(),idBd);
            //dbBioService.uploadInfo(uploadedFile.getContents(), idBd);
            //etniaService.upload(uploadedFile.getContents());
            //geneService.upload(uploadedFile.getContents(), idBd);
            msg = "it's done";
            RequestContext.getCurrentInstance().closeDialog(msg);
        }
    }

    public void viewUpload() {
        Map<String, Object> options = new HashMap<>();
        options.put("resizable", false);
        RequestContext.getCurrentInstance().openDialog("viewDbBioinfoUpload", options, null);
    }

    public void onViewUpload(SelectEvent event) {
        //etnias = etniaService.buscar();
        String msg = (String) event.getObject();
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(msg));

    }

}
