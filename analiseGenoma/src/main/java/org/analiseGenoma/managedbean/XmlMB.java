package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.model.Disease;
import org.analiseGenoma.model.DiseaseList;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.service.DiseaseService;
import org.analiseGenoma.service.GeneService;
import org.primefaces.model.UploadedFile;

@Named(value = "xmlMB")
@ViewScoped
public class XmlMB implements Serializable {

    @Inject
    private FacesContext context;
    @Inject
    private GeneService geneService;
    @Inject
    private DiseaseService diseaseService;
    
    
    private UploadedFile uploadedFile;
    private String valor;

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    
    
    public void upload() {
        System.out.println("ok");
        String msg = "Erro ao realizar o upload";
        if (uploadedFile != null) {
            Gene g = geneService.uploadXML(uploadedFile.getContents());
            msg = "Importado com sucesso:" + g.toString() + "fim";
            context.addMessage(null, new FacesMessage(msg));

        }
    }
    
    public void testar(){
        DiseaseList dl = new DiseaseList();
        dl.setDiseases(new ArrayList<>());
        
        Disease d = new Disease();
        d.setName("Disease de teste 1");
        dl.getDiseases().add(d);
        Disease d2 = new Disease();
        d2.setName("Disease de teste 2");
        dl.getDiseases().add(d2);
        
        try {
            valor = diseaseService.ObjectToXML(dl).toString();
            System.out.println("--> " + valor);
//        valor = "testado";
        } catch (Exception ex) {
            Logger.getLogger(XmlMB.class.getName()).log(Level.SEVERE, null, ex);
            valor = "Erro " + ex.getMessage();
        }
    }

}
