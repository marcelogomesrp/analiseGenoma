package org.analiseGenoma.managedbean;

import org.analiseGenoma.model.Vcf;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.managedbean.util.RequestParam;
import org.analiseGenoma.model.Variante;
import org.analiseGenoma.service.VcfService;

@Named(value = "vcfViewMB")
@RequestScoped
public class VcfViewMB implements Serializable {

    private Vcf vcf;
    private List<Variante> variants;
    @Inject
    @RequestParam
    private String id;
    @Inject private VcfService vcfService;

    @PostConstruct
    public void init() {
        vcf = new Vcf();
        if (id != null) {
            if (!id.equals("")) {
                Long lid = Long.valueOf(id);
                vcf = vcfService.buscarId(lid);
                variants = vcfService.buscarVariante(vcf.getId());
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
    
    
    
    
    
    
}
