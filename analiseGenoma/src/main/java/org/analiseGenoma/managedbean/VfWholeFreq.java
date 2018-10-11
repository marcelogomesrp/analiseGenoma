/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.analiseGenoma.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.analiseGenoma.model.Filtro;
import org.analiseGenoma.model.VcfMetadata;
import org.analiseGenoma.service.FiltroService;
import org.analiseGenoma.sessionbean.FilterSB;
import org.analiseGenoma.sessionbean.VcfMetadataSB;
import org.primefaces.context.RequestContext;

@Named(value = "VfWholeFreq")
@RequestScoped
public class VfWholeFreq implements Serializable {

    @Inject
    private VcfMetadataSB vcfMetadataSb;
    @Inject
    private FilterSB filterSB;
    
    private double prevalenceMin;
    private double prevalenceMax;
    
    private double prevalenceMetaMin;
    private double prevalenceMetaMax;
    private VcfMetadata vcfMetadata;
    private Filtro filtro;
    @Inject
    private FiltroService filtroService;
    
    @PostConstruct
    public void init() {
        filtro = filterSB.getFilter();
        if(filtro!=null){
            if(filtro.getWholeVariantFreqMin() != null){
                prevalenceMin = filtro.getWholeVariantFreqMin();
            }
            if(filtro.getWholeVariantFreqMax() != null){
                prevalenceMax = filtro.getWholeVariantFreqMax();
            }
        }
        //prevalenceMin = filterSB.getFilter().getWholeVariantFreqMin();
        //prevalenceMax = filterSB.getFilter().getWholeVariantFreqMax();
        vcfMetadata = vcfMetadataSb.getVcfMetadata();
        //System.out.println("ok" + vcfm.getWholevariantfreqmax());
    }

    public void closeView() {
        filtro.setWholeVariantFreqMin(prevalenceMin);
        filtro.setWholeVariantFreqMax(prevalenceMax);
        filtroService.merge(filtro);
        filterSB.setFilter(filtro);
        //this.updateFiltro();
        //filtroService.merge(filtro);
        
        RequestContext.getCurrentInstance().closeDialog("It's done");
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException ex) {
            Logger.getLogger(AnaliseSelecionarVarianteMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public double getPrevalenceMin() {
        return prevalenceMin;
    }

    public void setPrevalenceMin(double prevalenceMin) {
        this.prevalenceMin = prevalenceMin;
    }

    public double getPrevalenceMax() {
        return prevalenceMax;
    }

    public void setPrevalenceMax(double prevalenceMax) {
        this.prevalenceMax = prevalenceMax;
    }

    public VcfMetadata getVcfMetadata() {
        return vcfMetadata;
    }

    public void setVcfMetadata(VcfMetadata vcfMetadata) {
        this.vcfMetadata = vcfMetadata;
    }
    
    
}
