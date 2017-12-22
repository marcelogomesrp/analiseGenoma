package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
//import javax.enterprise.context.RequestScoped;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.print.attribute.standard.Severity;
import org.analiseGenoma.managedbean.util.RequestParam;
import org.analiseGenoma.model.Etnia;
import org.analiseGenoma.service.EtniaService;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

@Named(value = "etniaMB")
@ViewScoped
//@RequestScoped
public class EtniaMB implements Serializable {
    @Inject private FacesContext context;
    @Inject @RequestParam private String id;
    @Inject private EtniaService etniaService;
    private Etnia etnia;
    private List<Etnia> etnias;    
    private UploadedFile uploadedFile;
    private CrudMode crudMode;
    private boolean disabledValidation;

    @PostConstruct
    public void init() {
        disabledValidation = true;
        this.defCrudModeRead();
        etnias = etniaService.buscar();
        if (id != null) {
            if (!id.equals("")) {
                etnia = etniaService.buscarPorId(Long.valueOf(id));
            }
        }

    }

    public String adicionar() {
        try {
            etniaService.atualizar(etnia);
            etnia = new Etnia();
            context.getExternalContext()
                    .getFlash().setKeepMessages(true);
            context.addMessage(null, new FacesMessage("Cadastrado com sucesso"));
            return "etnia.xhtml?faces-redirect=true";
        } catch (Exception ex) {
            context.getExternalContext()
                    .getFlash().setKeepMessages(true);
            context.addMessage(null, new FacesMessage("Ocorreu erros ao realizar o cadastro"));
            return null;
        }
    }

    public void atualizarMode(Etnia etinia) {
        System.out.println("Rodando o atualizar mode");
        this.etnia = etinia;
    }

    public String atualizar() {
        System.out.println("Atualizadon....." + id + " - " + this.getEtnia().toString());
        etniaService.atualizar(this.getEtnia());
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
        this.etnias = etniaService.findByExample(etnia);
    }
    

    public void upload() {
        String msg = "Erro ao realizar o upload";
        if (uploadedFile != null) {
            etniaService.upload(uploadedFile.getContents());
            msg = "Importado com sucesso";
            RequestContext.getCurrentInstance().closeDialog(msg);            
        }
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public void viewEtniaUpload() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        RequestContext.getCurrentInstance().openDialog("viewEtniaUpload", options, null);
    }

    public void onViewEtniaUpload(SelectEvent event) {
        etnias = etniaService.buscar();
        String msg = (String) event.getObject();
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(msg));

    }
    
    public List<String> completeNome(String name){
        return etniaService.findNamesByName(name);
    }

    public List<String> completeSigla(String sigla){
        return etniaService.findSiglasBySigla(sigla);
    }
    
    public List<String> completeOrigem(String origem){
        return etniaService.findOrigensByOrigem(origem);
    }

    public void defCreateMode(){
        this.disabledValidation = false;
        this.etnia = new Etnia();     
        this.defCrudModeUpdate();
        
//        Application application = context.getApplication();
//        ViewHandler viewHandler = application.getViewHandler();
//        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
//        context.setViewRoot(viewRoot);
//        context.renderResponse();
    }

    public Etnia getEtnia() {
        if (etnia == null) {
            etnia = new Etnia();
        }
        return etnia;
    }

    public void setEtnia(Etnia etnia) {
        this.etnia = etnia;
    }

    public List<Etnia> getEtnias() {
        return etnias;
    }

    public void setEtnias(List<Etnia> etnias) {
        this.etnias = etnias;
    }

    public void editar(Etnia etnia) {
        this.etnia = etnia;
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
    
    

}


