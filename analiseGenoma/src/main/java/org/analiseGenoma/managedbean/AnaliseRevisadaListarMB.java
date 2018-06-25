package org.analiseGenoma.managedbean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.managedbean.util.FacesUtil;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.AnaliseLaudo;
import org.analiseGenoma.service.AnaliseLaudoService;
import org.analiseGenoma.service.AnaliseService;
import org.analiseGenoma.service.DiseaseService;
import org.analiseGenoma.sessionbean.AnaliseSB;

@Named(value = "analiseRevisadaListarMB")
@ViewScoped
public class AnaliseRevisadaListarMB implements Serializable {

    @Inject
    private FacesContext context;
    private AnaliseLaudo analiseLaudo;
    @Inject
    private AnaliseService analiseService;
    @Inject
    private AnaliseLaudoService analiseLaudoService;
    @Inject
    private DiseaseService patologiaService;
    @Inject
    private AnaliseSB analiseSB;
    
    private String cid;
    

    public AnaliseLaudo getAnaliseLaudo() {
        return analiseLaudo;
    }

    public void setAnaliseLaudo(AnaliseLaudo analiseLaudo) {
        this.analiseLaudo = analiseLaudo;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }


    

    @PostConstruct
    public void init() {
        try {
            
            Long value = (Long) FacesUtil.getSessionMapValue("idAnalise");
            //Analise analise = analiseService.buscarPorId(value);
            Analise analise = analiseSB.getAnalise();
            analiseLaudo = analiseLaudoService.find(analise);
            if(analiseLaudo == null){
                analiseLaudo = new AnaliseLaudo();
            }else{
                cid = analiseLaudo.getPatologia().getIcd();
                //patologia = analiseLaudo.getPatologia().getName();
                analiseLaudo.setPatologia(analiseLaudo.getPatologia());
            }
            analiseLaudo.setAnalise(analise);
        } catch (Exception ex) {
            System.out.println("Erro init analise laudar: " + ex.getMessage());
        }
    }

    public String viewLaudar(Analise selected) {
        Analise analise = selected;
        analiseSB.setAnalise(selected);
        
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        FacesUtil.setSessionMapValue("idAnalise", analise.getId());
        return "analise_laudar.xhtml?faces-redirect=true";
    }

    public String salvar() {
        analiseLaudoService.merge(analiseLaudo);
                context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Updated successfully"));
        return null;
    }
    
    
    
//    public List<String> cidComplete(String query) {
//        System.out.println("Cid auto complet");
//        List<String> results = new ArrayList<String>();
//        patologiaService.buscarCid(query + "%").forEach(p -> results.add(p.getIcd()));
//        System.out.println("Lista com tamanho: " + results.size());
//        return results;
//    }
    
//    public void onCidSelect(SelectEvent event) {
//        List<Disease> patologias = patologiaService.buscarCid(cid);
//        if (patologias != null) {
//            if (patologias.size() > 0) {
//                analiseLaudo.setPatologia(patologias.get(0));
////                patologia = patologias.get(0).getName();
//            }
//        }
//    }
//    
//    public void onPatologiaSelect(SelectEvent event) {
//        List<Disease> patologias = patologiaService.buscarNome(patologia);
//        if (patologias != null) {
//            if (patologias.size() > 0) {
//                analiseLaudo.setPatologia(patologias.get(0));
//                cid = patologias.get(0).getIcd();
//            }
//        }
//    }

}
