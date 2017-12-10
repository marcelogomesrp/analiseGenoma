package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.model.Impact;
import org.analiseGenoma.service.ImpactoService;

@Named(value = "impactoMB")
//@RequestScoped
@ViewScoped
public class ImpactoMB implements Serializable {
    @Inject
    private FacesContext context;
    private Impact impacto;
    //@Inject private ImpactoService impactoService;
    @Inject private ImpactoService impactoService;
    private List<Impact> listImpacto;
    private boolean updateMode;
    
    @PostConstruct
    public void init() {
        impacto = new Impact();
        listImpacto = impactoService.buscar();
//        listImpacto = new ArrayList<>();
        updateMode = false;
    }
    
    public void adicionar(){
        //context.getExternalContext().getFlash().setKeepMessages(true);
        
        List<Impact> bdList = impactoService.buscarNome(impacto.getName());
        if(bdList.isEmpty()){                   
        impactoService.adicionar(impacto);
        impacto = new Impact();
        listImpacto = impactoService.buscar();
        
        //context.addMessage(null, new FacesMessage("Cadastrado com sucesso"));
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Cadastrado com sucesso"));
        }else{
            context.addMessage("formulario:nome", new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", "Nome já cadastrado"));
        }
    }
    
    public void atualizar(){
        try{
        impactoService.atualizar(impacto);
        impacto = new Impact();
        listImpacto = impactoService.buscar();
        updateMode = false;
        context.getExternalContext()
        .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Atualizado com sucesso"));
        }catch(Exception ex){
            //SqlExceptionHelper
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "System Error: " + ex.getMessage()));
        }
    }
    
    public void pesquisar(){
        listImpacto = impactoService.buscarNome(impacto.getName());        
    }
    
    public void cancelar(){
        impacto = new Impact();
        listImpacto = impactoService.buscar();
        updateMode = false;
    }

    public Impact getImpacto() {
        return impacto;
    }

    public void setImpacto(Impact impacto) {
        this.impacto = impacto;
    }

    public List<Impact> getListImpacto() {
        return listImpacto;
    }

    public void setListImpacto(List<Impact> listImpacto) {
        this.listImpacto = listImpacto;
    }
    
    public void editar(Impact impacto){
        this.impacto = impacto;        
        updateMode = true;
    }

    public boolean isUpdateMode() {
        return updateMode;
    }
    
    public boolean isInsertMode() {
        return !updateMode;
    }

    public void setUpdateMode(boolean updateMode) {
        this.updateMode = updateMode;
    }
    
    
    
}
