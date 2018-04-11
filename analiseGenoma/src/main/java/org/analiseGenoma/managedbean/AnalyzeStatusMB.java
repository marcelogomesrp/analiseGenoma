package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.User;
import org.analiseGenoma.model.VarianteRevisada;
import org.analiseGenoma.model.Vcf;
import org.analiseGenoma.service.VarianteRevisadaService;
import org.analiseGenoma.sessionbean.AnaliseSB;

@Named(value = "analyzeStatusMB")
@RequestScoped
public class AnalyzeStatusMB implements Serializable {

    @Inject
    private AnaliseSB analyzeSB;
    
    @Inject
    private FacesContext context;
    
    @Inject
    private VarianteRevisadaService varianteRevisadaService;

    

    public String show(Analise analise) {
        analyzeSB.setAnalise(analise);
        return "analyzeStatus.xhtml?faces-redirect=true";
//        context.getExternalContext()
//                .getFlash().setKeepMessages(true);
//        FacesUtil.setSessionMapValue("idAnalise", analise.getId());
//        return "analise_editar.xhtml?faces-redirect=true";
    }
    
    public int percentageCompleted(User u){
            //private VarianteRevisada varianteRevisada;    
        Vcf vcf = analyzeSB.getAnalise().getVcf();
        List<VarianteRevisada> vr = varianteRevisadaService.findByAnaliseRevisor(vcf, u);
        int total = vr.size();
        int feito = 0;
        //feito = vr.stream().filter((v) -> (v.getPatogenic() == null)).map((_item) -> 1).reduce(feito, Integer::sum);
        for(VarianteRevisada v : vr){
            if(v.getPatogenic() != null){
                feito++;
            }
        }
        if(feito == 0){
            return 0;
        }else{
            int percentual = total / feito * 100;
            return percentual;
        }
    }

}
