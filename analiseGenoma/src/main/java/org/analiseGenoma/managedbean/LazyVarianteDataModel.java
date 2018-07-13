/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.analiseGenoma.managedbean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.Filtro;
import org.analiseGenoma.model.Variante;
import org.analiseGenoma.service.VcfService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.faces.view.ViewScoped;

/**
 *
 * @author marcelo
 */
@Named
@ViewScoped
public class LazyVarianteDataModel extends LazyDataModel<Variante> {

    private List<Variante> datasource;
    private Analise analise;
    private Filtro filtro;
    
    @Inject
    private VcfService vcfService;

//    public LazyVarianteDataModel(Analise analise, Filtro filtro) {
//        this.analise = analise;
//        this.filtro = filtro;
//
//    }

    @PostConstruct
    void init() {
//        datasource = vcfService.findVariante(analise, filtro);
//        this.setPageSize(5);
//        this.setRowCount(4);
    }

//    public LazyVarianteDataModel() {
//        datasource = new ArrayList<>();
//        for (int x = 0; x < 4; x++) {
//            Variante v = new Variante();
//            v.setPosition(new Long(x));
//            datasource.add(v);
//        }
//        this.setPageSize(5);
//        this.setRowCount(4);
//    }

    @Override
    public List<Variante> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        //datasource = vcfService.findVariante(analise, filtro);
        datasource = vcfService.findVariante(analise, filtro, first, pageSize);
        this.setPageSize(5);
        //this.setRowCount(datasource.size());
        this.setRowCount( vcfService.contarVariante(analise, filtro, first, pageSize).intValue());
        //this.setRowCount(datasource.size());
        /*
        List<Variante> teste = new ArrayList<>();
        Variante v = new Variante();
        v.setPosition(80l);
        v.setId(1L);
        //teste.add(new Variante());
        teste.add(v);
        return teste;*/
        return datasource;

    }

    @Override
    public Variante getRowData(String rowKey) {
        Long id = new Long(rowKey);
        for (Variante v : datasource) {
            if (v.getId().equals(id)) {
                return v;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(Variante v) {
        System.out.println("aqui");
        //return super.getRowKey(object); //To change body of generated methods, choose Tools | Templates.
        return v.getId();
    }

    public void setAnalise(Analise analise) {
        this.analise = analise;
    }

    public void setFiltro(Filtro filtro) {
        this.filtro = filtro;
    }

    
    
}
