package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.managedbean.util.ColumnModel;
import org.analiseGenoma.managedbean.util.FacesUtil;
import org.analiseGenoma.managedbean.util.RequestParam;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.BancoBiologico;
import org.analiseGenoma.model.Cromossomo;
import org.analiseGenoma.model.Filtro;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.Impact;
import org.analiseGenoma.model.InformacaoBiologica;
import org.analiseGenoma.model.Patologia;
import org.analiseGenoma.model.Variante;
import org.analiseGenoma.service.AnaliseService;
import org.analiseGenoma.service.BancoBiologicoService;
import org.analiseGenoma.service.CromossomoService;
import org.analiseGenoma.service.FiltroService;
import org.analiseGenoma.service.GeneService;
import org.analiseGenoma.service.ImpactoService;
import org.analiseGenoma.service.InformacaoBiologicaService;
import org.analiseGenoma.service.PatologiaService;
import org.analiseGenoma.service.VcfService;
import org.primefaces.model.DualListModel;

@Named(value = "analiseSelcMB")
//@RequestScoped
@ViewScoped
public class AnaliseSelecionarVarianteMB implements Serializable {

    @Inject private FacesContext context;
    @Inject private AnaliseService analiseService;
    @Inject private VcfService vcfService;
    @Inject private BancoBiologicoService bdBioService;
    @Inject private InformacaoBiologicaService infoBioService;
    @Inject private GeneService geneService;
    
    @Inject private FiltroService filtroService;
    @Inject private CromossomoService cromossomoService;
    @Inject private ImpactoService impactoService;
    @Inject private PatologiaService patologiaService;
    
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
    private List<BancoBiologico> bancos;
    
    private List<Gene> listGene = new ArrayList<Gene>();
    
    private DualListModel<String> duaListGene;
    private Double qualidadeMin;
    private Double qualidadeMax;
    
    private Filtro filtro;
    
    private String idBd; 
    private String patologia;
    
    
    
    
    private Analise analise;
    @PostConstruct
    public void init() {

        System.out.println("Iniciando a pagina novamente... a de selecionar");
        analise = new Analise();
        variantes = new ArrayList<>();
        qtdVariante = 0;
        System.out.println("-------------------------------------------------------");
        try {
            Long value = (Long) FacesUtil.getSessionMapValue("id");
            System.out.println("--> " + value);
            if (value != null) {
                analise = analiseService.buscarPorId(value);
                //analiseSelcMB.analise.vcf.id
                //filtro = new Filtro();
                
                filtro = filtroService.buscarPorAnalise(analise.getId());           
                variantes = vcfService.buscarVariante(analise.getVcf().getId(),filtro);
                qtdVariante = variantes.size();
                listGene = geneService.buscarAnalise(analise.getId());
                listCromossomos = new ArrayList<>();
                listImpactos = new ArrayList<>();
                List<String> genesSource = new ArrayList<String>();
                List<String> genesTarget = new ArrayList<String>();
                for(Gene g: geneService.buscarAnalise(analise.getId())){
//                    if(filtro.getGenes()!= null){
//                        if(filtro.getGenes().contains(g)){
//                            genesTarget.add(g.getSimbolo());
//                        }else{
//                            genesSource.add(g.getSimbolo());
//                        }
//                    }
                     genesSource.add(g.getSimbolo());
                    //genesTarget.add(g.getSimbolo());                    
                }
                for(Gene g: filtro.getGenes()){
                    genesTarget.add(g.getSimbolo());
                    genesSource.remove(g.getSimbolo());
                }
                
                
                //List<String> genesTarget = geneService.buscarAnalise(analise.getId());
                duaListGene = new DualListModel<String>(genesSource, genesTarget);
                qualidadeMax = analiseService.buscarQualidadeMax(analise.getId());
                //qualidadeMax = 100.0;
                qualidadeMin = 10.0;
                
                
                for(Cromossomo c : analiseService.buscarCromossomos(analise.getId())){
                    listCromossomos.add(c.getNome());
                }
                
                for(Impact i : analiseService.buscarImpactos(analise.getId())){
                    listImpactos.add(i.getName());
                }
                
                this.cromossomoSelecionarFromFiltro();
                this.impactoSelecionarFromFiltro();
                this.addDBColumn();
                //https://stackoverflow.com/questions/39632619/how-to-generate-dynamic-columns-in-jsf-datatable
                
            }
        } catch (Exception ex) {
            System.out.println("Ops... deu erro aqui: " + ex.toString());
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
        if(selectedCromossomo.length > 0)
            this.selectedCromossomo = selectedCromossomo;
    }

    public String[] getSelectedImpacto() {
        return selectedImpacto;
    }

    public void setSelectedImpacto(String[] selectedImpacto) {
        if(selectedCromossomo.length > 0)
            this.selectedImpacto = selectedImpacto;
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
    
    
    
    
    public String ShowPage(Analise analise){
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
    
    
    
    public String dobro(long id){
        return "O dobro e: " + id * 2;
    }
    
    public void desfazerFiltrar(){
        System.out.println("Filtrando...");
        Filtro filtro = filtroService.buscarPorAnalise(analise.getId());           
        variantes = vcfService.buscarVariante(analise.getVcf().getId(), filtro);
    }
    public void filtrar(){
        System.out.println("Filtrando...");
        Filtro filtroTemporario = new Filtro();
        filtroTemporario.setGenes(new ArrayList<Gene>());
        for(String g: duaListGene.getTarget()){
            filtroTemporario.getGenes().add(geneService.buscarNovoSimbolo(g));
        }
        
        filtroTemporario.setCromossomos( new ArrayList<Cromossomo>());
        //for(String s: getListCromossomos()){
        System.out.println("qtd cromosssomo: " + getSelectedCromossomo().length);
        for(String s: getSelectedCromossomo()){
            System.out.println("Cromossomo: " + s);
            filtroTemporario.getCromossomos().add(cromossomoService.buscarPorNome(s));
        }
        
        filtroTemporario.setImpacto(new ArrayList<Impact>());
        for(String s: getSelectedImpacto()){
            filtroTemporario.getImpacto().add(impactoService.buscarPorNome(s));
        }
        
        filtroTemporario.setQualidadeMin(filtro.getQualidadeMin());
        filtroTemporario.setQualidadeMax(filtro.getQualidadeMax());
        
//        for(String c : selectedCromossomo){
//            System.out.println("\t\t\t---> no filtro" + c);
//        }
        
        //getSelectedCromossomo
        
        variantes = vcfService.buscarVariante(analise.getVcf().getId(), filtroTemporario);
        
                
        System.out.println("Filtro a ser aplicado: " );
        
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
    public void cromossomoSelecionarFromFiltro(){
        List<Cromossomo> lcromossomo = filtro.getCromossomos();
        
        selectedCromossomo = new String[lcromossomo.size()];
        for(int x=0;x<lcromossomo.size();x++){
            selectedCromossomo[x] = lcromossomo.get(x).getNome();
        }
    }
    public void impactoSelecionarFromFiltro(){
        
//        selectedImpacto = new String[3];
//        selectedImpacto[0] = "LOW";
//        selectedImpacto[1] = "MODERATE";
//        selectedImpacto[2] = "MODIFIER";
        List<Impact> limpacto = filtro.getImpacto();
        selectedImpacto = new String[limpacto.size()];
        
        for(int x=0;x<limpacto.size();x++){
            selectedImpacto[x] = limpacto.get(x).getName();
        }
    }
    
    public void cromossomoSelecionarTodos(){
        selectedCromossomo = new String[listCromossomos.size()];
        for(int x =0; x< listCromossomos.size(); x++){
            selectedCromossomo[x] = listCromossomos.get(x);
        }
    }
    
    public void impactoSelecionarTodos(){
        selectedImpacto = new String[listImpactos.size()];
        for(int x=0; x< listImpactos.size(); x++){
            selectedImpacto[x] = listImpactos.get(x);
        }
    }
    
    
    public void testar(){
        for(String c : selectedCromossomo){
            System.out.println("\t\t\t---> " + c);
        }
    }
    
    public void cromossomoDeselecionarTodos(){
        selectedCromossomo = new String[0];
    }
    
    public void impactoDeselecionarTodos(){
        selectedImpacto = new String[0];
    }
    
    
    private void addDBColumn(){
        bancos = bdBioService.buscar();
        columns = new ArrayList<ColumnModel>();
        //columns.add(new ColumnModel(columnKey.toUpperCase(), columnKey));
//        columns.add(new ColumnModel("Omin", "Omin"));
//        columns.add(new ColumnModel("NCBI", "ncbi"));
//        columns.add(new ColumnModel("Oubrobd", "OutroBd"));
        Integer x=0;
        for(BancoBiologico bd : bancos){
            columns.add(new ColumnModel(bd.getNome(), (x++).toString() ));
        }
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnModel> columns) {
        this.columns = columns;
    }
    
    public String bdInfo(String bdName, Long geneId){
        //return "aqui: " + bd + "id " + id;     
        BancoBiologico bd = bancos.get(Integer.valueOf(bdName));
        
        //return "https://www.omim.org/entry/[UTIL]".replace("[UTIL]", gene);
        InformacaoBiologica info = null;
        try {
            info = infoBioService.buscaBdGene(bd.getId(), geneId);
        } catch (Exception ex) {
            System.out.println("AnaliseSelecionarVarianteMB: Nao foi possivel recuerar a info: " + ex.getMessage());
        }
        if(info != null){
            return bd.getUrlInfo().replace("[UTIL]",  info.getUtil() );
        }else{
            //return "nao peguei o info " + bd.getId() + " - " + geneId;
            return "";
        }
        //return bd.getUrlInfo(); //+ infoBioService.buscaBdGene(bd.getId(), geneId).getUtil();
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
        for (BancoBiologico bd : bdBioService.buscar()) {
            bds.add(new SelectItem(bd.getId(), bd.getNome()));
        }
        return bds;
    }
    
    public List<String> patologiaComplete(String query) {
        List<String> results = new ArrayList<String>();
        patologiaService.buscarNome(query + "%").forEach(p -> results.add(p.getNome()));        
        return results;
    }
    
    public void adicionarGenePorPatologia(){
        Patologia p = patologiaService.buscarAddNome(patologia);
        if( p!= null){            
            List<Gene> genesP = infoBioService.buscarGenePorPatologia(p);
                    //geneService.buscarPorPatologia(p);
            for(Gene g : genesP){
                //System.out.println("\t\t--------->\n " + g.toString());
                //filtro.getGenes().add(g);
                duaListGene.getTarget().add(g.getSimbolo());
            }
        }
    }
    
}

//        for(String g: duaListGene.getTarget()){
//            filtroTemporario.getGenes().add(geneService.buscarNovoSimbolo(g));
//        }
        