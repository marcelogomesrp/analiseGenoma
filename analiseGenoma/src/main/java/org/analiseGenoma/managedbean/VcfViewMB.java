package org.analiseGenoma.managedbean;

import org.analiseGenoma.model.Vcf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.managedbean.util.RequestParam;
import org.analiseGenoma.model.Cromossomo;
import org.analiseGenoma.model.Effect;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.Sift;
import org.analiseGenoma.model.UmdPredictor;
import org.analiseGenoma.model.Variante;
import org.analiseGenoma.model.VcfMetadata;
import org.analiseGenoma.service.VcfMetadataService;
import org.analiseGenoma.service.VcfService;
import org.primefaces.model.chart.PieChartModel;

@Named(value = "vcfViewMB")
@RequestScoped
public class VcfViewMB implements Serializable {

    private Vcf vcf;
    private VcfMetadata vcfMetadata;
    private List<Variante> variants;
    @Inject
    @RequestParam
    private String id;
    @Inject private VcfService vcfService;
    @Inject private VcfMetadataService vcfMetadataService;
    private PieChartModel genePieModel;

    @PostConstruct
    public void init() {
        vcf = new Vcf();
        if (id != null) {
            if (!id.equals("")) {
                Long lid = Long.valueOf(id);
                vcf = vcfService.buscarId(lid);
                variants = vcfService.buscarVariante(vcf.getId());
                vcfMetadata = vcfMetadataService.findByVcfId(vcf.getId());
                genePieModel = new PieChartModel();                
               for (Gene g : vcfMetadata.getMapGene().keySet()){
                    genePieModel.set(g.getSimbolo(), vcfMetadata.getMapGene().get(g));
                }
               genePieModel.setTitle("Qtd genes");
                genePieModel.setLegendPosition("w");
               //vcfMetadata.getMapGene().forEach((k,v)-> genePieModel.set(k->get, v));
                //pieModel1.set("Brand 1", 540);
            }else{
                id="vz";
            }
        }else{
            id = "null";
        }        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Vcf getVcf() {
        return vcf;
    }

    public void setVcf(Vcf vcf) {
        this.vcf = vcf;
    }

    public List<Variante> getVariants() {
        return variants;
    }

    public void setVariants(List<Variante> variants) {
        this.variants = variants;
    }

    public VcfMetadata getVcfMetadata() {
        return vcfMetadata;
    }

    public void setVcfMetadata(VcfMetadata vcfMetadata) {
        this.vcfMetadata = vcfMetadata;
    }
    
    public List<Cromossomo> getCromossos() {
        return new ArrayList<>(vcfMetadata.getCromossomos());
    }
    
    public List<Gene> getGenes(){
        return new ArrayList<>(vcfMetadata.getGenes());
    }
    
    public List<UmdPredictor> getUmdPredictors(){
        return new ArrayList<>(vcfMetadata.getUmdPredictors());
    }
    public List<Effect> getEffects(){
        return new ArrayList<>(vcfMetadata.getEffects());
    }
    public List<Sift> getSifts(){
        return new ArrayList<>(vcfMetadata.getSifts());
    }

    public PieChartModel getGenePieModel() {
        return genePieModel;
    }

    public void setGenePieModel(PieChartModel genePieModel) {
        this.genePieModel = genePieModel;
    }
    
    
    
}
