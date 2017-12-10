package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.model.BancoBiologico;
import org.analiseGenoma.model.InformacaoBiologica;
import org.analiseGenoma.service.BancoBiologicoService;
import org.analiseGenoma.service.GeneService;
import org.analiseGenoma.service.InformacaoBiologicaService;
import org.analiseGenoma.service.PatologiaService;
import org.primefaces.model.UploadedFile;

@Named(value = "infoImportarMB")
@RequestScoped
public class InfoImportarMB implements Serializable {

    @Inject
    private BancoBiologicoService bdBioService;
    @Inject
    private PatologiaService patologiaService;
    @Inject
    private GeneService geneService;
    @Inject
    private InformacaoBiologicaService infoBioService;    
    @Inject
    private FacesContext context;
    private InformacaoBiologica infoBio;
    private String idBd;    
    private UploadedFile uploadedFile;
    
    

    @PostConstruct
    public void init() {
        infoBio = new InformacaoBiologica();
    }

    public List<SelectItem> getSelectBds() {
        List<SelectItem> bds = new ArrayList<SelectItem>();
        for (BancoBiologico bd : bdBioService.buscar()) {
            bds.add(new SelectItem(bd.getId(), bd.getNome()));
        }
        return bds;
    }
 
    public String getIdBd() {
        return idBd;
    }

    public void setIdBd(String idBd) {
        this.idBd = idBd;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String adicionar(){
        System.out.println("Adicionado...");
        if (uploadedFile != null) {
            infoBioService.importar(idBd, uploadedFile.getContents());
        }
                context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Importado com sucesso"));
        return "info_importar.xhtml?faces-redirect=true";
    }

    
}
