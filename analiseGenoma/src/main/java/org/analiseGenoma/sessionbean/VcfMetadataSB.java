package org.analiseGenoma.sessionbean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.analiseGenoma.model.VcfMetadata;


@Named(value = "vcfMetadataSB")
@SessionScoped
public class VcfMetadataSB implements Serializable {
    private VcfMetadata vcfMetadata;
    
    @PostConstruct
    public void init() {
        vcfMetadata = new VcfMetadata();
    }

    public VcfMetadata getVcfMetadata() {
        return vcfMetadata;
    }

    public void setVcfMetadata(VcfMetadata vcfMetadata) {
        this.vcfMetadata = vcfMetadata;
    }

    
    public void reset() {
        this.vcfMetadata = new VcfMetadata();
    }
    
}