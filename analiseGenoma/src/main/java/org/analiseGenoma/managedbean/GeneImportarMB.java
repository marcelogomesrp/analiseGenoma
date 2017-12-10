package org.analiseGenoma.managedbean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.service.GeneService;
import org.primefaces.model.UploadedFile;

@Named(value = "geneImportarMB")
@RequestScoped
public class GeneImportarMB implements Serializable {
    
    @Inject
    private GeneService geneService;
    private Gene gene;
    @Inject
    private FacesContext context;
    private UploadedFile uploadedFile;
    
                
    
    @PostConstruct
    public void init(){
    }
    
    public String adicionar(){
        System.out.println("Importando genes...");
        if(uploadedFile != null){                    
        geneService.importar(uploadedFile.getContents());
                context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Cadastrado com sucesso"));
        }else{
            context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Informe o arquivo"));
        }
        return "gene_importar.xhtml?faces-redirect=true";
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
    
    
    

    
}
