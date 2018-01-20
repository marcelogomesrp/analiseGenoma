package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.managedbean.util.RequestParam;
import org.analiseGenoma.model.Icd10;
import org.analiseGenoma.service.Icd10Service;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

@Named(value = "icd10MB")
@ViewScoped
public class Icd10MB implements Serializable {

    @Inject
    private FacesContext context;
    @Inject
    @RequestParam
    private String id;
    @Inject
    private Icd10Service icd10Service;
    private Icd10 icd10;
    private List<Icd10> list;
    private UploadedFile uploadedFile;

    public Icd10 getIcd10() {
        return icd10;
    }

    public void setIcd10(Icd10 icd10) {
        this.icd10 = icd10;
    }

    public List<Icd10> getList() {
        return list;
    }

    public void setList(List<Icd10> list) {
        this.list = list;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
    
    
    
    @PostConstruct
    public void init() {
        icd10 = new Icd10();
        list = icd10Service.find();

    }

    public void add() {
        icd10Service.merge(icd10);
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("It's done"));
        icd10 = new Icd10();
        list = icd10Service.find();
    }
    
    
    public void viewIcd10Upload() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        RequestContext.getCurrentInstance().openDialog("viewIcd10Upload", options, null);
    }
    
        public void upload() {
        String msg = "Error uploading";
        if (uploadedFile != null) {
            icd10Service.upload(uploadedFile.getContents());
            msg = "Imported successfully";
            RequestContext.getCurrentInstance().closeDialog(msg);            
        }
    }
        
    public void onViewEtniaUpload(SelectEvent event) {
        list = icd10Service.find();
        String msg = (String) event.getObject();
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(msg));

    }        

}
