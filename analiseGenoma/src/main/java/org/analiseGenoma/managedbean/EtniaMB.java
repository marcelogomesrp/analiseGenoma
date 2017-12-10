package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
//import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.managedbean.util.RequestParam;
import org.analiseGenoma.model.Etnia;
import org.analiseGenoma.service.EtniaService;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

@Named(value = "etniaMB")
@RequestScoped
//@ViewScoped
public class EtniaMB implements Serializable {

    @Inject
    private FacesContext context;
    @Inject
    private EtniaService etniaService;
    private Etnia etnia;
    private List<Etnia> etnias;
    @Inject
    @RequestParam
    private String id;
    //private boolean modoEditar;
    private UploadedFile uploadedFile;
    private CrudMode mode;

    @PostConstruct
    public void init() {
        mode = CrudMode.Read;
        //modoEditar = false;
        System.out.println("rodando o init");
        //etnia = new Etnia();
        etnias = etniaService.buscar();

        if (id != null) {
            if (!id.equals("")) {
                etnia = etniaService.buscarPorId(Long.valueOf(id));
                //modoEditar = true;
                mode = CrudMode.Create;
            }
        }

    }

    public String adicionar() {         
        etniaService.atualizar(etnia);
        etnia = new Etnia();
        this.defReadMode();
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Cadastrado com sucesso"));
        return "etnia.xhtml?faces-redirect=true";
    }

    public void atualizarMode(Etnia etinia) {
        System.out.println("Rodando o atualizar mode");
        this.etnia = etinia;

        // return "etnia.xhtml?faces-redirect=true";
    }

    public String atualizar() {
        System.out.println("Atualizadon....." + id + " - " + this.getEtnia().toString());
        etniaService.atualizar(this.getEtnia());
//        this.limpar();
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Atualizado com sucesso"));
        return "etnia.xhtml?faces-redirect=true";
    }

    public String limpar() {
        System.out.println("Limpar executado");
        //etnia = new Etnia();
        return "etnia.xhtml?faces-redirect=true";
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
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public boolean isModoEditar() {
//        return modoEditar;
//    }
    public void findByExample(){
        this.etnias = etniaService.findByExample(etnia);
    }
    

    public void upload() {
        //RequestContext.getCurrentInstance().closeDialog();
        String msg = "Erro ao realizar o upload";
        if (uploadedFile != null) {
//            usuarioService.adicionar(usuario, confirmaSenha, uploadedFile.getContents());
            etniaService.upload(uploadedFile.getContents());
            msg = "Importado com sucesso";
            RequestContext.getCurrentInstance().closeDialog(msg);            
        }
//        context.getExternalContext()
//                .getFlash().setKeepMessages(true);
//        context.addMessage(null, new FacesMessage(msg));
//        this.refresh();
//        return "etnia.xhtml?faces-redirect=true";

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

        //return "etnia.xhtml?faces-redirect=true";        
    }

//    private void refresh() {
//        Application application = context.getApplication();
//        ViewHandler viewHandler = application.getViewHandler();
//        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
//        context.setViewRoot(viewRoot);
//        context.renderResponse();
//    }

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
    public boolean isCreateMode(){
        return mode.equals(CrudMode.Create);
    }
    
    public void defCreateMode(){
        this.etnia = new Etnia();
        mode = CrudMode.Create;
    }
    
    public boolean isReadMode(){
        return mode.equals(CrudMode.Read);
    }
    public void defReadMode(){
        mode = CrudMode.Read;
    }

}
