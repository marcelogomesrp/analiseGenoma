package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.User;
import org.analiseGenoma.model.Variante;
import org.analiseGenoma.model.VarianteRevisada;
import org.analiseGenoma.model.Vcf;
import org.analiseGenoma.service.VariantSelectedService;
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
    @Inject
    private VariantSelectedService variantSelectedService;
    

    public String show(Analise analise) {
        analyzeSB.setAnalise(analise);
        return "analyzeStatus.xhtml?faces-redirect=true";
//        context.getExternalContext()
//                .getFlash().setKeepMessages(true);
//        FacesUtil.setSessionMapValue("idAnalise", analise.getId());
//        return "analise_editar.xhtml?faces-redirect=true";
    }
    
    public double percentageCompleted(User u){
        //variant_selected_variante;
        
            //private VarianteRevisada varianteRevisada;    
        Vcf vcf = analyzeSB.getAnalise().getVcf();
        List<VarianteRevisada> vr = varianteRevisadaService.findByAnaliseRevisor(vcf, u);
        double feito = vr.size();
        List<Variante> vs = variantSelectedService.findByAnalise(analyzeSB.getAnalise());
        double total = vs.size();
        //int feito = 0;
        //feito = vr.stream().filter((v) -> (v.getPatogenic() == null)).map((_item) -> 1).reduce(feito, Integer::sum);
//        for(VarianteRevisada v : vr){
//            if(v.getPatogenic() != null){
//                feito++;
//            }
//        }
        if(total == 0){
            return 0;
            }else{
            double percentual =  feito /total * 100;//50 / 100 * 100;oub
            return percentual;
        }
    }

}
