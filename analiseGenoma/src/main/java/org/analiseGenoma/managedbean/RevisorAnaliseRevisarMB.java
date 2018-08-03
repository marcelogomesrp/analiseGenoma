package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
//import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.managedbean.util.ColumnModel;
import org.analiseGenoma.managedbean.util.FacesUtil;
import org.analiseGenoma.managedbean.util.RequestParam;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.DbBio;
import org.analiseGenoma.model.DbBioInfo;
import org.analiseGenoma.model.Filtro;
import org.analiseGenoma.model.User;
import org.analiseGenoma.model.Variante;
import org.analiseGenoma.model.VarianteRevisada;
import org.analiseGenoma.service.AnaliseService;
import org.analiseGenoma.service.DbBioInfoService;
import org.analiseGenoma.service.DbBioService;
import org.analiseGenoma.service.FiltroService;
import org.analiseGenoma.service.InformacaoBiologicaService;
import org.analiseGenoma.service.VariantSelectedService;
import org.analiseGenoma.service.VarianteRevisadaService;
import org.analiseGenoma.service.VcfService;
import org.analiseGenoma.sessionbean.UserSB;
import org.primefaces.context.RequestContext;

@Named(value = "revAnaliseRevisarMB")
//@RequestScoped
@ViewScoped
public class RevisorAnaliseRevisarMB implements Serializable {

    private User usuario;
    private Analise analise;
    private List<Variante> variantes;
    @Inject
    @RequestParam
    private String id;
    @Inject
    private AnaliseService analiseService;
    @Inject
    private VcfService vcfService;
    @Inject
    private FacesContext context;
    private Filtro filtro;
    @Inject
    private FiltroService filtroService;
    private List<VarianteRevisada> variantesRev;
    @Inject
    private VarianteRevisadaService varianteRevisadaService;
    //@Inject private UsuarioMB usuarioMB;
    @Inject
    private VariantSelectedService variantSelectedService;
    
    @Inject
    private UserSB reviser;
    private boolean hideVariantChecked;
    private List<DbBio> bancos;
    @Inject private DbBioService dbBioService;
    
    private List<ColumnModel> columns;
    @Inject
    private InformacaoBiologicaService infoBioService;
    @Inject
    private DbBioInfoService dbBioInfoService;
    //private User revisor;

    @PostConstruct
    public void init() {
        bancos = new ArrayList<>();
        if (id != null) {
             bancos = dbBioService.find();
             this.addDBColumn();
            
            
            //revisor = usuarioMB.getUsuario();
            analise = analiseService.buscarPorId(Long.valueOf(id));
            filtro = filtroService.buscarPorAnalise(analise.getId());
            //variantes = vcfService.findVariante(analise, filtro);
            //variantes = variantSelectedService.findByAnalise(analise);
            variantes = vcfService.findVariante(analise, filtro);
//            if(hideVariantChecked){
//                for(Variante v: variantes){
//                    if(v.)
//                }
//            }
            System.out.println("Qtd: " + reviser.getUser().getRowsInTable());
            //variantesRev = varianteRevisadaService.findByAnaliseRevisor(analise.getVcf(), reviser.getUser());
            
        } else {
            analise = new Analise();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Analise getAnalise() {
        return analise;
    }

    public void setAnalise(Analise analise) {
        this.analise = analise;
    }

    public List<Variante> getVariantes() {
        return variantes;
    }

    public void setVariantes(List<Variante> variantes) {
        this.variantes = variantes;
    }

    public String finalizar() {
        System.out.println("Finalizado rev");
        
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Analise enviar ao gestor com sucesso"));
        return "index.xhtml?faces-redirect=true";
    }
    
    public String observacao(Long variantId){
        
        return "ok" + variantId;
    }
    
    public void observacaoEdited(AjaxBehaviorEvent event){
        System.out.println("Edita :D---");
    }
    
    
    public void teste(ValueChangeEvent e){
        System.out.println("teste...");
        
    }
    public void viewOpinar(Long id){
        Map<String, Object> options = new HashMap<>();
        options.put("modal", true);
        options.put("width", 800);
        options.put("height", 600);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        options.put("resizable", false);

        Map<String, List<String>> params = new HashMap<>();
        List<String> values = new ArrayList<>();
        values.add(id.toString());
        params.put("id", values);
        FacesUtil.setSessionMapValue("idVariante", id);
        FacesUtil.setSessionMapValue("idAnalise", analise.getId());        
        
        RequestContext.getCurrentInstance().openDialog("viewopinar_gestor", options, params);
        //variantes = vcfService.findVariante(analise, filtro);
    }

    public boolean isHideVariantChecked() {
        return hideVariantChecked;
    }

    public void setHideVariantChecked(boolean hideVariantChecked) {
        this.hideVariantChecked = hideVariantChecked;
    }

    public List<DbBio> getBancos() {
        return bancos;
    }

    public void setBancos(List<DbBio> bancos) {
        this.bancos = bancos;
    }
    
    private void addDBColumn() {
    
        columns = new ArrayList<ColumnModel>();
        //columns.add(new ColumnModel(columnKey.toUpperCase(), columnKey));
//        columns.add(new ColumnModel("Omin", "Omin"));
//        columns.add(new ColumnModel("NCBI", "ncbi"));
//        columns.add(new ColumnModel("Oubrobd", "OutroBd"));
        Integer x = 0;
        for (DbBio bd : bancos) {
            columns.add(new ColumnModel(bd.getName(), (x++).toString()));
        }
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnModel> columns) {
        this.columns = columns;
    }
    
    public String bdInfo(String bdName, Long geneId) {
        //return "aqui: " + bd + "id " + id;  
        if(analise.getPatologia() == null){
            return "";
        }
         DbBio bd = bancos.get(Integer.valueOf(bdName));
        //InformacaoBiologica info = null;        
        try {
            // SELECT * FROM dbbioinfo_gene WHERE dbbioinfo_disease_id_disease = 93612 AND genes_id_gene = 10877;
             //SELECT * FROM dbbioinfo_gene WHERE dbbioinfo_dbbio_id_dbbio = 2 AND dbbioinfo_disease_id_disease = 93612 AND genes_id_gene = 10877;

            //info = infoBioService.buscaBdGene(bd.getId(), geneId);            
            DbBioInfo info2 = dbBioInfoService.findByIDbIdDiseaseIdGene(bd.getId(), analise.getPatologia().getId(), geneId);
            
            if(info2 == null){
                //return "http://" + bd.getUrl() +  "/"+  bd.getId() +"/"+ analise.getPatologia().getId();
                return "";
            }else{
                if("Pathogenic".equals(info2.getUrl())){
                    return "Pathogenic.xhtml";
                }
                return info2.getUrl();
            }
        } catch (Exception ex) {
            System.out.println("AnaliseSelecionarVarianteMB: Nao foi possivel recuerar a info: " + ex.getMessage());
            return "e: " + ex.getMessage();
        }
        //return "none";
        //return "";
    }

    
}
