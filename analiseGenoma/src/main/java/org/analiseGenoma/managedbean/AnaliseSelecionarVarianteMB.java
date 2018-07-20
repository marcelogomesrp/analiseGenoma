package org.analiseGenoma.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.analiseGenoma.managedbean.util.ColumnModel;
import org.analiseGenoma.managedbean.util.FacesUtil;
import org.analiseGenoma.managedbean.util.RequestParam;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.DbBio;
import org.analiseGenoma.model.Cromossomo;
import org.analiseGenoma.model.Filtro;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.Impact;
import org.analiseGenoma.model.InformacaoBiologica;
import org.analiseGenoma.model.Disease;
import org.analiseGenoma.model.VariantSelected;
import org.analiseGenoma.model.Variante;
import org.analiseGenoma.model.VcfMetadata;
import org.analiseGenoma.service.AnaliseService;
import org.analiseGenoma.service.BancoBiologicoService;
import org.analiseGenoma.service.CromossomoService;
import org.analiseGenoma.service.FiltroService;
import org.analiseGenoma.service.GeneService;
import org.analiseGenoma.service.ImpactoService;
import org.analiseGenoma.service.InformacaoBiologicaService;
import org.analiseGenoma.service.DiseaseService;
import org.analiseGenoma.service.VariantSelectedService;
import org.analiseGenoma.service.VcfMetadataService;
import org.analiseGenoma.service.VcfService;
import org.analiseGenoma.sessionbean.AnaliseSB;
import org.analiseGenoma.sessionbean.FilterSB;
import org.analiseGenoma.sessionbean.VcfMetadataSB;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;

@Named(value = "analiseSelcMB")
//@RequestScoped
@ViewScoped
public class AnaliseSelecionarVarianteMB implements Serializable {

    private LazyDataModel<Variante> lazyModel;
    @Inject
    private LazyVarianteDataModel lazyVarianteDataModel;

    @Inject
    private FacesContext context;
    @Inject
    private AnaliseService analiseService;
    @Inject
    private VcfService vcfService;
    @Inject
    private BancoBiologicoService bdBioService;
    @Inject
    private InformacaoBiologicaService infoBioService;
    @Inject
    private GeneService geneService;

    @Inject
    private FiltroService filtroService;
    @Inject
    private CromossomoService cromossomoService;
    @Inject
    private ImpactoService impactoService;
    @Inject
    private DiseaseService patologiaService;
    @Inject
    private VcfMetadataService vcfMetadataService;

    @Inject
    private FiltroService filterService;

    @Inject
    private VariantSelectedService variantSelectedService;

    @Inject
    @RequestParam
    private String id;
    private String teste;
    private int qtdVariante;
    private List<Variante> variantes;

    private String[] selectedCromossomo;
    private List<String> listCromossomos;
    private String[] selectedImpacto;
    private List<String> listImpactos;

    private List<ColumnModel> columns;
    private List<DbBio> bancos;

    private List<Gene> listGene = new ArrayList<Gene>();

    private DualListModel<String> duaListGene;
    private DualListModel<String> duaListEffect;
    private Double qualidadeMin;
    private Double qualidadeMax;

    private Filtro filtro;

    private String idBd;
    private String patologia;
    private VcfMetadata vcfMetadata;

    private Analise analise;
    @Inject
    private AnaliseSB analiseSB;
    @Inject
    private FilterSB filterSB;
    @Inject
    private VcfMetadataSB metadataSB;
    


    @PostConstruct
    public void init() {

        System.out.println("Iniciando a pagina novamente... a de selecionar");
        analise = new Analise();
        variantes = new ArrayList<>();
        qtdVariante = 0;
        System.out.println("-------------------------------------------------------");
        try {
            Long value = (Long) FacesUtil.getSessionMapValue("id");
            if(value == null){
                //FacesContext.getCurrentInstance().getExternalContext().redirect("DisplayList.jsf"); 
                context.getExternalContext().redirect("analise_pesquisar.xhtml");
                
            }
            System.out.println("--> " + value);
            if (value != null) {
                analise = analiseService.buscarPorId(value);
                analiseSB.setAnalise(analise);
//                //analiseSelcMB.analise.vcf.id
//                //filtro = new Filtro();
//                
                filtro = filtroService.buscarPorAnalise(analise.getId());
                filterSB.setFilter(filtro);
                //variantes = vcfService.findVariante(analise, filtro);
                lazyVarianteDataModel.setAnalise(analise);
                lazyVarianteDataModel.setFiltro(filtro);
                lazyModel = lazyVarianteDataModel;

                //lazyModel =  new LazyVarianteDataModel();
                //lazyModel =  new LazyVarianteDataModel(analise, filtro);
                vcfMetadata = vcfMetadataService.findByVcfId(analise.getVcf().getId());
                metadataSB.setVcfMetadata(vcfMetadata);

                if (filtro.getPositionMin() == null) {
                    filtro.setPositionMin(vcfMetadata.getPositonMin());
                }
                if (filtro.getPositionMax() == null) {
                    filtro.setPositionMax(vcfMetadata.getPositonMax());
                }

                //  List<String> genesSource = new ArrayList<>();
                //List<String> genesTarget = geneService.buscarAnalise(analise.getId());
                //List<String> genesTarget = new ArrayList<>(vcfMetadata.getGenes()); 
                //List<String> genesSource = vcfMetadata.getGenes().stream().map(g -> g.getSimbolo()).collect(Collectors.toList());
                List<String> genesTarget = filtro.getGenes().stream().map(g -> g.getSymbol()).collect(Collectors.toList());
                List<String> genesSource = vcfMetadata.getGenes()
                        .stream()
                        .map(g -> g.getSymbol())
                        .filter(g -> !genesTarget.contains(g))
                        .collect(Collectors.toList());

                //vcfMetadata.getGenes().stream().map(g -> g.getSimbolo()).collect(Collectors.toList());
                duaListGene = new DualListModel<>(genesSource, genesTarget);

                List<String> effectsTarget = filtro.getEffects().stream().map(e -> e.getName()).collect(Collectors.toList());
                List<String> effectsSource = vcfMetadata.getEffects()
                        .stream()
                        .map(e -> e.getName())
                        .filter(e -> !effectsTarget.contains(e))
                        .collect(Collectors.toList());
                duaListEffect = new DualListModel<>(effectsSource, effectsTarget);

                //variantes = vcfService.buscarVariante(analise.getVcf().getId(), filtro);
                //qtdVariante = variantes.size();
//                listGene = geneService.buscarAnalise(analise.getId());
//                listCromossomos = new ArrayList<>();
//                listImpactos = new ArrayList<>();
//                List<String> genesSource = new ArrayList<String>();
//                List<String> genesTarget = new ArrayList<String>();
//                for(Gene g: geneService.buscarAnalise(analise.getId())){
////                    if(filtro.getGenes()!= null){
////                        if(filtro.getGenes().contains(g)){
////                            genesTarget.add(g.getSimbolo());
////                        }else{
////                            genesSource.add(g.getSimbolo());
////                        }
////                    }
//                     genesSource.add(g.getSimbolo());
//                    //genesTarget.add(g.getSimbolo());                    
//                }
//                for(Gene g: filtro.getGenes()){
//                    genesTarget.add(g.getSimbolo());
//                    genesSource.remove(g.getSimbolo());
//                }
//                
//                
//                //List<String> genesTarget = geneService.buscarAnalise(analise.getId());
//                duaListGene = new DualListModel<String>(genesSource, genesTarget);
//                qualidadeMax = analiseService.buscarQualidadeMax(analise.getId());
//                //qualidadeMax = 100.0;
//                qualidadeMin = 10.0;
//                
//                
//                for(Cromossomo c : analiseService.buscarCromossomos(analise.getId())){
//                    listCromossomos.add(c.getNome());
//                }
//                
//                for(Impact i : analiseService.buscarImpactos(analise.getId())){
//                    listImpactos.add(i.getName());
//                }
//                
//                this.cromossomoSelecionarFromFiltro();
//                this.impactoSelecionarFromFiltro();
//                this.addDBColumn();
//                //https://stackoverflow.com/questions/39632619/how-to-generate-dynamic-columns-in-jsf-datatable
//                
            }
        } catch (Exception ex) {
            System.out.println("AnaliseSelecionarVarianteMB.init: Ops... deu erro aqui: " + ex.toString());
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeste() {
        return teste;
    }

    public void setTeste(String teste) {
        this.teste = teste;
    }

    public String[] getSelectedCromossomo() {
        return selectedCromossomo;
    }

    public void setSelectedCromossomo(String[] selectedCromossomo) {
        if (selectedCromossomo.length > 0) {
            this.selectedCromossomo = selectedCromossomo;
        }
    }

    public String[] getSelectedImpacto() {
        return selectedImpacto;
    }

    public void setSelectedImpacto(String[] selectedImpacto) {
        if (selectedCromossomo.length > 0) {
            this.selectedImpacto = selectedImpacto;
        }
    }

    public List<String> getListCromossomos() {
        return listCromossomos;
    }

    public void setListCromossomos(List<String> listCromossomos) {
        this.listCromossomos = listCromossomos;
    }

    public List<String> getListImpactos() {
        return listImpactos;
    }

    public void setListImpactos(List<String> listImpactos) {
        this.listImpactos = listImpactos;
    }

    public Filtro getFiltro() {
        return filtro;
    }

    public String ShowPage(Analise analise) {
        this.analise = analise;
        return "analise_selecionar_variantes.xhtml";
    }

    public Analise getAnalise() {
        return analise;
    }

    public void setAnalise(Analise analise) {
        this.analise = analise;
    }

    public int getQtdVariante() {
        return qtdVariante;
    }

    public void setQtdVariante(int qtdVariante) {
        this.qtdVariante = qtdVariante;
    }

    public List<Variante> getVariantes() {
        return variantes;
    }

    public void setVariantes(List<Variante> variantes) {
        this.variantes = variantes;
    }

    public String getIdBd() {
        return idBd;
    }

    public void setIdBd(String idBd) {
        this.idBd = idBd;
    }

    public String dobro(long id) {
        return "O dobro e: " + id * 2;
    }

    public void desfazerFiltrar() {
        System.out.println("Filtrando...");
        Filtro filtro = filtroService.buscarPorAnalise(analise.getId());
        //variantes = vcfService.buscarVariante(analise.getVcf().getId(), filtro);
        variantes = vcfService.findVariante(analise, filtro);
    }

    public void filtrar() {
        System.out.println("Filtrando...");
        Filtro filtroTemporario = new Filtro();
//        filtroTemporario.setGenes(new ArrayList<Gene>());
//        for(String g: duaListGene.getTarget()){
//            filtroTemporario.getGenes().add(geneService.buscarNovoSimbolo(g));
//        }

        filtroTemporario.setCromossomos(new HashSet<Cromossomo>());
        //for(String s: getListCromossomos()){
        System.out.println("qtd cromosssomo: " + getSelectedCromossomo().length);
        for (String s : getSelectedCromossomo()) {
            System.out.println("Cromossomo: " + s);
            filtroTemporario.getCromossomos().add(cromossomoService.buscarPorNome(s));
        }

        filtroTemporario.setImpacts(new HashSet<Impact>());
        for (String s : getSelectedImpacto()) {
            filtroTemporario.getImpacts().add(impactoService.buscarPorNome(s));
        }

//        filtroTemporario.setQualidadeMin(filtro.getQualidadeMin());
//        filtroTemporario.setQualidadeMax(filtro.getQualidadeMax());
//        for(String c : selectedCromossomo){
//            System.out.println("\t\t\t---> no filtro" + c);
//        }
        //getSelectedCromossomo
        //variantes = vcfService.buscarVariante(analise.getVcf().getId(), filtroTemporario);
        variantes = vcfService.findVariante(analise, filtroTemporario);

        System.out.println("Filtro a ser aplicado: ");

//        for(String s : selectedCromossomo){
//            System.out.println("Cromoss   omo: " + s);
//        }
/*
        for(Variante v: variantes){
            for(Gene g: duaListGene.getSource()){
                System.out.println("G: " + g.toString());
//                if(v.getGene().getSimbolo().equals(g))
//                    variantes.remove(v);
            }
            
        }
         */
//        List<String> genesSelecionados = duaListGene.getTarget();
//        for(String g: genesSelecionados){
//            System.out.println("----> " + g);
//            Gene gg = geneService.buscarNovoSimbolo(g);
//            System.out.println("xxx -> " + gg.toString());
//            
//        }
        /* lista do genes removidos 
        List<String> tst = duaListGene.getSource();
        System.out.println("Inicio: " + variantes.size());
        for(String g: tst){
            System.out.println("----> " + g);
        }
         */
 /*
        for(Variante v: variantes){
            for(String g: tst){
                System.out.println("G: " + g);
                if(v.getGene() != null){
                    if(tst.equals(v.getGene().getSimbolo())){
                        System.out.println("Removendo... ");
                        variantes.remove(v);                    
                    }            
                }
            }
        }*/
        System.out.println("Fim: " + variantes.size());
    }

    public void cromossomoSelecionarFromFiltro() {
        List<Cromossomo> lcromossomo = new ArrayList<Cromossomo>(filtro.getCromossomos());

        selectedCromossomo = new String[lcromossomo.size()];
        for (int x = 0; x < lcromossomo.size(); x++) {

            selectedCromossomo[x] = lcromossomo.get(x).getNome();
        }
    }

    public void impactoSelecionarFromFiltro() {

//        selectedImpacto = new String[3];
//        selectedImpacto[0] = "LOW";
//        selectedImpacto[1] = "MODERATE";
//        selectedImpacto[2] = "MODIFIER";
        List<Impact> limpacto = new ArrayList(filtro.getImpacts());
        selectedImpacto = new String[limpacto.size()];

        for (int x = 0; x < limpacto.size(); x++) {
            selectedImpacto[x] = limpacto.get(x).getName();
        }
    }

    public void cromossomoSelecionarTodos() {
        selectedCromossomo = new String[listCromossomos.size()];
        for (int x = 0; x < listCromossomos.size(); x++) {
            selectedCromossomo[x] = listCromossomos.get(x);
        }
    }

    public void impactoSelecionarTodos() {
        selectedImpacto = new String[listImpactos.size()];
        for (int x = 0; x < listImpactos.size(); x++) {
            selectedImpacto[x] = listImpactos.get(x);
        }
    }

    public void testar() {
        filtro.setPositionMin(54268135L);
        filtroService.merge(filtro);
//        for(String c : selectedCromossomo){
//            System.out.println("\t\t\t---> " + c);
//        }
    }

    public void cromossomoDeselecionarTodos() {
        selectedCromossomo = new String[0];
    }

    public void impactoDeselecionarTodos() {
        selectedImpacto = new String[0];
    }

    private void addDBColumn() {
        bancos = bdBioService.buscar();
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
        DbBio bd = bancos.get(Integer.valueOf(bdName));

        //return "https://www.omim.org/entry/[UTIL]".replace("[UTIL]", gene);
        InformacaoBiologica info = null;
        try {
            info = infoBioService.buscaBdGene(bd.getId(), geneId);
        } catch (Exception ex) {
            System.out.println("AnaliseSelecionarVarianteMB: Nao foi possivel recuerar a info: " + ex.getMessage());
        }
//        if (info != null) {
//            return bd.getUrlInfo().replace("[UTIL]", info.getUtil());
//        } else {
//            //return "nao peguei o info " + bd.getId() + " - " + geneId;
//            return "";
//        }
        //return bd.getUrlInfo(); //+ infoBioService.buscaBdGene(bd.getId(), geneId).getUtil();
        return "";
    }

    public List<Gene> getListGene() {
        return listGene;
    }

    public void setListGene(List<Gene> listGene) {
        this.listGene = listGene;
    }

    public DualListModel<String> getDuaListGene() {
        return duaListGene;
    }

    public void setDuaListGene(DualListModel<String> duaListGene) {
        this.duaListGene = duaListGene;
    }

    public DualListModel<String> getDuaListEffect() {
        return duaListEffect;
    }

    public void setDuaListEffect(DualListModel<String> duaListEffect) {
        this.duaListEffect = duaListEffect;
    }

    public Double getQualidadeMin() {
        return qualidadeMin;
    }

    public void setQualidadeMin(Double qualidadeMin) {
        this.qualidadeMin = qualidadeMin;
    }

    public Double getQualidadeMax() {
        return qualidadeMax;
    }

    public void setQualidadeMax(Double qualidadeMax) {
        this.qualidadeMax = qualidadeMax;
    }

    public String getPatologia() {
        return patologia;
    }

    public void setPatologia(String patologia) {
        this.patologia = patologia;
    }

    public List<SelectItem> getSelectBds() {
        List<SelectItem> bds = new ArrayList<SelectItem>();
        for (DbBio bd : bdBioService.buscar()) {
            bds.add(new SelectItem(bd.getId(), bd.getName()));
        }
        return bds;
    }

    public List<String> patologiaComplete(String query) {
        List<String> results = new ArrayList<String>();
        patologiaService.buscarNome(query + "%").forEach(p -> results.add(p.getName()));
        return results;
    }

    public void adicionarGenePorPatologia() {
        Disease p = patologiaService.buscarAddNome(patologia);
        if (p != null) {
            List<Gene> genesP = infoBioService.buscarGenePorPatologia(p);
            //geneService.buscarPorPatologia(p);
            for (Gene g : genesP) {
                //System.out.println("\t\t--------->\n " + g.toString());
                //filtro.getGenes().add(g);
                duaListGene.getTarget().add(g.getSymbol());
            }
        }
    }

    private void callView(String view) {
        Map<String, Object> options = new HashMap<>();
        options.put("modal", true);
        options.put("width", 1024);
        options.put("height", 768);
//        options.put("width", 800);
//        options.put("height", 380);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        options.put("resizable", false);

        Map<String, List<String>> params = new HashMap<String, List<String>>();
        List<String> values = new ArrayList<String>();
        values.add("1");
        params.put("id", values);
        RequestContext.getCurrentInstance().openDialog(view, options, params);
        // variantes = vcfService.findVariante(analise, filtro);
    }

    public void viewFilterChr() {
        String view = "viewfilter_chr";
        this.callView(view);
    }

    public void viewFilterPositon() {
        Map<String, Object> options = new HashMap<>();
        options.put("modal", true);
        options.put("width", 800);
        options.put("height", 600);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        options.put("resizable", false);

        Map<String, List<String>> params = new HashMap<String, List<String>>();
        List<String> values = new ArrayList<String>();
        values.add("1");
        params.put("id", values);
        RequestContext.getCurrentInstance().openDialog("viewfilter_position", options, params);
        variantes = vcfService.findVariante(analise, filtro);
    }

    public void viewFilterGene() {
        Map<String, Object> options = new HashMap<>();
        options.put("modal", true);
        options.put("width", 800);
        options.put("height", 600);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        options.put("resizable", false);

        Map<String, List<String>> params = new HashMap<String, List<String>>();
        List<String> values = new ArrayList<String>();
        values.add("1");
        params.put("id", values);
        RequestContext.getCurrentInstance().openDialog("viewfilter_gene", options, params);
        variantes = vcfService.findVariante(analise, filtro);
    }

    public void viewFilterUmdPredictor() {
        Map<String, Object> options = new HashMap<>();
        options.put("modal", true);
        options.put("width", 700);
        options.put("height", 350);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        options.put("resizable", false);

        Map<String, List<String>> params = new HashMap<String, List<String>>();
        List<String> values = new ArrayList<String>();
        RequestContext.getCurrentInstance().openDialog("viewfilter_umdPredictor", options, params);
        variantes = vcfService.findVariante(analise, filtro);
    }

    public void onViewFilterClose(SelectEvent event) {
        String msg = (String) event.getObject();
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(msg));
    }

    public void closeView() {
        this.updateFiltro();
        filtroService.merge(filtro);
        variantes = vcfService.findVariante(analise, filtro);
        RequestContext.getCurrentInstance().closeDialog("Filtro aplicado com sucesso");
        //org.primefaces.context.DefaultRequestContext.getCurrentInstance().update("formulario:tabela");

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException ex) {
            Logger.getLogger(AnaliseSelecionarVarianteMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public VcfMetadata getVcfMetadata() {
        return vcfMetadata;
    }

    public void setVcfMetadata(VcfMetadata vcfMetadata) {
        this.vcfMetadata = vcfMetadata;
    }

    public void updateFilterName() {
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Filter name updated"));
        filterService.merge(filtro);
    }

    private void updateFiltro() {
        Set<Gene> genes = new HashSet<>();
        for (String geneSymbol : duaListGene.getTarget()) {
            genes.add(geneService.findBySymbol(geneSymbol));
            //genes.add(geneService.buscarNovoSimbolo(geneSymbol));
        }
        filtro.setGenes(genes);

        //for(Gene g: geneService.buscarAnalise(analise.getId())){
//                    if(filtro.getGenes()!= null){
//                        if(filtro.getGenes().contains(g)){
//                            genesTarget.add(g.getSimbolo());
//                        }else{
//                            genesSource.add(g.getSimbolo());
//                        }
//                    }
        //genesSource.add(g.getSimbolo());
        //genesTarget.add(g.getSimbolo());                    
        //}
    }

    public void viewFilterFilter() {
        String view = "viewfilter_filter";
        this.callView(view);
    }

    public void viewFilterZygocity() {
        String view = "viewfilter_zygocity";
        this.callView(view);
    }

    public void viewFilterAllelic1() {
        String view = "viewfilter_allelic1";
        this.callView(view);
    }

    public void viewFilterAllelic2() {
        String view = "viewfilter_allelic2";
        this.callView(view);
    }

    public void viewFilterType() {
        String view = "viewfilter_type";
        this.callView(view);
    }

    public void viewFilterRef() {
        String view = "viewfilter_ref";
        this.callView(view);
    }

    public void viewFilterChanged() {
        String view = "viewfilter_changed";
        this.callView(view);
    }

    public void viewFilterHgvsC() {
        String view = "viewfilter_hgvsc";
        this.callView(view);
    }

    public void viewFilterHgvsP() {
        String view = "viewfilter_hgvsp";
        this.callView(view);
    }

    public void viewFilterImpact() {
        String view = "viewfilter_impact";
        this.callView(view);
    }

    public void viewFilterClinvarSignificance() {
        String view = "viewfilter_clinvarsignificance";
        this.callView(view);
    }

    //aqui inicio
    public void viewFilterAmericanvariantfreq() {
        String view = "viewfilter_americanvariantfreq";
        this.callView(view);
    }

    public void viewFilterAcceptorslicesite() {
        String view = "viewfilter_acceptorslicesite";
        this.callView(view);
    }

    public void viewFilterAfrincavariantfreq() {
        String view = "viewfilter_afrincavariantfreq";
        this.callView(view);
    }

    public void viewFilterAlllelemutfraction() {
        String view = "viewfilter_alllelemutfraction";
        this.callView(view);
    }

    public void viewFilterAssianvariantfreq() {
        String view = "viewfilter_assianvariantfreq";
        this.callView(view);
    }

    public void viewFilterClinvarAccession() {
        String view = "viewfilter_clinvaraccession";
        this.callView(view);
    }

    public void viewFilterClinvaralleleorign() {
        String view = "viewfilter_clinvaralleleorign";
        this.callView(view);
    }

    public void viewFilterClinvaralleletype() {
        String view = "viewfilter_clinvaralleletype";
        this.callView(view);
    }

    public void viewFilterClinvarDisease() {
        String view = "viewfilter_clinvarDisease";
        this.callView(view);
    }

    public void viewFilterDbsnp() {
        String view = "viewfilter_dbsnp";
        this.callView(view);
    }

    public void viewFilterDonorsplicesite() {
        String view = "viewfilter_donorsplicesite";
        this.callView(view);
    }

    public void viewFilterEnsembl() {
        String view = "viewfilter_ensembl";
        this.callView(view);
    }

    public void viewFilterEuropenavariantfreq() {
        String view = "viewfilter_europenavariantfreq";
        this.callView(view);
    }

    public void viewFilterFeature() {
        String view = "viewfilter_feature";
        this.callView(view);
    }

    public void viewFilterGenotype() {
        String view = "viewfilter_genotype";
        this.callView(view);
    }

    public void viewFilterGerpneutralrate() {
        String view = "viewfilter_gerpneutralrate";
        this.callView(view);
    }

    public void viewFilterGerprsscore() {
        String view = "viewfilter_gerprsscore";
        this.callView(view);
    }

    public void viewFilterInterprodomain() {
        String view = "viewfilter_interprodomain";
        this.callView(view);
    }

    public void viewFilterLrt() {
        String view = "viewfilter_lrt";
        this.callView(view);
    }

    public void viewFilterMeanbasequality() {
        String view = "viewfilter_meanbasequality";
        this.callView(view);
    }

    public void viewFilterMutationtaster() {
        String view = "viewfilter_mutationtaster";
        this.callView(view);
    }

    public void viewFilterPolyphenhdiv() {
        String view = "viewfilter_polyphenhdiv";
        this.callView(view);
    }

    public void viewFilterPolyphenhvar() {
        String view = "viewfilter_polyphenhvar";
        this.callView(view);
    }

    public void viewFilterReaddepth() {
        String view = "viewfilter_readdepth";
        this.callView(view);
    }

    public void viewFilterSift() {
        String view = "viewfilter_sift";
        this.callView(view);
    }

    public void viewFilterStatus() {
        String view = "viewfilter_status";
        this.callView(view);
    }

    public void viewFilterVarianttype() {
        String view = "viewfilter_varianttype";
        this.callView(view);
    }

    public void viewFilterVertebrategenomesconservationscore() {
        String view = "viewfilter_vertebrategenomesconservationscore";
        this.callView(view);
    }

    public void viewFilterExonintronnumber() {
        String view = "viewfilter_exonintronnumber";
        this.callView(view);
    }

    public void viewFilterEffect() {
        Map<String, Object> options = new HashMap<>();
        options.put("modal", true);
        options.put("width", 800);
        options.put("height", 600);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        options.put("resizable", false);

        Map<String, List<String>> params = new HashMap<String, List<String>>();
        List<String> values = new ArrayList<String>();
        values.add("1");
        params.put("id", values);
        RequestContext.getCurrentInstance().openDialog("viewfilter_effect", options, params);
        variantes = vcfService.findVariante(analise, filtro);
    }

    public void viewFilterPrevalence() {
        Map<String, Object> options = new HashMap<>();
        options.put("modal", true);
        options.put("width", 700);
        options.put("height", 350);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        options.put("resizable", false);

        Map<String, List<String>> params = new HashMap<String, List<String>>();
        List<String> values = new ArrayList<String>();
        RequestContext.getCurrentInstance().openDialog("viewfilter_prevalence", options, params);
        variantes = vcfService.findVariante(analise, filtro);
    }

    public String finalizarSelecao() {
        variantes = vcfService.findVariante(analise, filtro);
        VariantSelected variantSelected = new VariantSelected();
        variantSelected.setAnalise(analise);
        variantSelected.setVariantes(variantes);
        variantSelectedService.persiste(variantSelected);

        analise.setEstado("varianteSelecionada");
        analiseService.atualizar(analise);
        FacesUtil.setSessionMapValue("id", analise.getId());
        return "analise_select_reviser.xhtml?faces-redirect=true";
    }

    public int getQtdVariantes() {
        if (variantes != null) {
            return variantes.size();
        }
        return 0;
    }

    public LazyDataModel<Variante> getLazyModel() {
        // System.out.println("pegando o lazymodel PG size: " + lazyModel.getPageSize() + " Count " + lazyModel.getRowCount());
        if (lazyModel == null) {
            lazyModel = new LazyVarianteDataModel();
        }
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<Variante> lazyModel) {
        this.lazyModel = lazyModel;
    }

}

//        for(String g: duaListGene.getTarget()){
//            filtroTemporario.getGenes().add(geneService.buscarNovoSimbolo(g));
//        }

