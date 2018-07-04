package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.managedbean.util.ColumnModel;
import org.analiseGenoma.managedbean.util.FacesUtil;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.AnaliseLaudo;
import org.analiseGenoma.model.Filtro;
import org.analiseGenoma.model.User;
import org.analiseGenoma.model.Variante;
import org.analiseGenoma.model.VarianteRevisada;
import org.analiseGenoma.service.AnaliseLaudoService;
import org.analiseGenoma.service.AnaliseService;
import org.analiseGenoma.service.DiseaseService;
import org.analiseGenoma.service.FiltroService;
import org.analiseGenoma.service.ReviserService;
import org.analiseGenoma.service.VarianteRevisadaService;
import org.analiseGenoma.service.VcfService;
import org.analiseGenoma.sessionbean.AnaliseSB;

@Named(value = "analiseLaudarMB")
@ViewScoped
public class AnaliseLaudarMB implements Serializable {

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

    @Inject
    private FiltroService filtroService;
    @Inject
    private VcfService vcfService;
    private Filtro filtro;
    private List<Variante> variantes;
    
    
    @Inject
    private VarianteRevisadaService varianteRevisadaService;
    
    
    @Inject
    private ReviserService reviserService;
    
     private List<ColumnModel> revisores;

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
            if (analiseLaudo == null) {
                analiseLaudo = new AnaliseLaudo();
                analiseLaudo.setAnalise(analise);
            } else {
                //cid = analiseLaudo.getPatologia().getIcd();
                //patologia = analiseLaudo.getPatologia().getName();
                //analiseLaudo.setPatologia(analiseLaudo.getPatologia());
            }
            filtro = filtroService.buscarPorAnalise(analise.getId());
            variantes = vcfService.findVariante(analise, filtro);
            
            revisores = new ArrayList<ColumnModel>();
            
            
            //revisores.add(new ColumnModel("R1", "r1"));
            for(User r : analise.getRevisores()){
                revisores.add(new ColumnModel(r.getName(), r.getId().toString()));                
            }
            
//                    for (DbBio bd : bancos) {
//            columns.add(new ColumnModel(bd.getName(), (x++).toString()));
//        }

        
            

        } catch (Exception ex) {
            System.out.println("Erro init analise laudar: " + ex.getMessage());
        }
    }
    
    public String windowName(String idRevisor, Long idVariant){
        return "PF('dlg"+idRevisor + ":" + idVariant + "').show();";
    }
    public String windowName2(String idRevisor, Long idVariant){
        return "dlg"+idRevisor + ":" + idVariant ;
    }
    
    public String note(String idRevisor, Long idVariant){
        List<VarianteRevisada> list = varianteRevisadaService.findByVarianteRevisor(idRevisor, idVariant ); 
        if(list.size() > 0){
            VarianteRevisada vr = list.get(0);
            return vr.getNote();
        }
        return "";
    }
    
    public String opiniao(String idRevisor, Long idVariant){
        //return "Revisor: " + idRevisor + " idVariant: " + idVariant;
        List<VarianteRevisada> list = varianteRevisadaService.findByVarianteRevisor(idRevisor, idVariant ); //(String idRevisor, Long idVariant) 
        if(list.size() > 0){
            VarianteRevisada vr = list.get(0);
            switch(vr.getPatogenic()){
                case 1: return "benign";
                case 2: return "likely benign";
                case 3: return "uncertain significance";
                case 4: return "likely pathogenic";
                case 5: return "pathogenic";
            }
            //return vr.getPatogenic().toString();
        }
        //return reviserService.getOpiniao(idRevisor, idVariant);
        return "";
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

    public List<Variante> getVariantes() {
        return variantes;
    }

    public void setVariantes(List<Variante> variantes) {
        this.variantes = variantes;
    }

    public List<ColumnModel> getRevisores() {
        return revisores;
    }

    public void setRevisores(List<ColumnModel> revisores) {
        this.revisores = revisores;
    }
    
    
    
}
