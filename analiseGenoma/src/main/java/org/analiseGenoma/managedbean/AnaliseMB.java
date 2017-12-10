/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
//import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.Paciente;
import org.analiseGenoma.model.Patologia;
import org.analiseGenoma.model.Vcf;
import org.analiseGenoma.service.AnaliseService;
import org.analiseGenoma.service.PacienteService;
import org.analiseGenoma.service.PatologiaService;
import org.analiseGenoma.service.VcfService;
import org.primefaces.event.SelectEvent;
import org.analiseGenoma.managedbean.util.FacesUtil;

@Named(value = "analiseMB")
//@RequestScoped
@ViewScoped
public class AnaliseMB implements Serializable {

    @Inject private FacesContext context;
    @Inject private AnaliseService analiseService;
    @Inject private PatologiaService patologiaService;
    @Inject private PacienteService pacienteService;
    @Inject private VcfService vcfService;
    @Inject private AnaliseSelecionarVarianteMB selecionarMB;
    private Analise analise;
    private String cid;
    private String patologia;
    private String paciente;
    private String correlato;
    private String idVcf;
    private Long idPaciente;
    private Long idCorrelato;
    private String controle;
    private Long idControle;
            

    @PostConstruct
    public void init() {
        System.out.println("Iniciando a pagina novamente...");
        analise = new Analise();
    }

    public Analise getAnalise() {
        return analise;
    }
    
            
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
        
    }

    public String getPatologia() {
        return patologia;
    }

    public void setPatologia(String patologia) {
        this.patologia = patologia;
    }

    public String getPaciente() {
        return paciente;
    }

    public String getIdVcf() {
        return idVcf;
    }

    public void setIdVcf(String idVcf) {
        this.idVcf = idVcf;
    }
    
    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getControle() {
        return controle;
    }

    public void setControle(String controle) {
        this.controle = controle;
    }

    public Long getIdControle() {
        return idControle;
    }

    public void setIdControle(Long idControle) {
        this.idControle = idControle;
    }

    public String getCorrelato() {
        return correlato;
    }

    public void setCorrelato(String correlato) {
        this.correlato = correlato;
    }

    public Long getIdCorrelato() {
        return idCorrelato;
    }

    public void setIdCorrelato(Long idCorrelato) {
        this.idCorrelato = idCorrelato;
    }
    
    
    


    public void setAnalise(Analise analise) {
        if(analise == null)
            analise = new Analise();
        this.analise = analise;
    }

    public String adicionar() {
        analise.setEstado("criado");
        if(idPaciente != null)
            analise.setPaciente(pacienteService.buscarId(idPaciente));
        if(idControle != null)
            analise.setControle(pacienteService.buscarId(idControle));
        if(idVcf != null)
            analise.setVcf(vcfService.buscarId(Long.valueOf(idVcf)));
        //if(idControle != null)
            //analise.setVcfControle(vcfService.buscarId(Long.valueOf(idControle)));
        if(analise.getVcfsCorrelatos() != null){
            for(Vcf vcf: analise.getVcfsCorrelatos()){
                vcfService.atualizar(vcf);
            }
        }            
        analiseService.adicionar(analise);
        
        context.getExternalContext()
               .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Cadastrado com sucesso"));

        
        FacesUtil.setSessionMapValue("id", analise.getId());
        
        analise = new Analise();
        //return "analise_nova.xhtml?faces-redirect=true";
        return "analise_selecionar_variantes.xhtml?faces-redirect=true";
        //return selecionarMB.ShowPage(analise);
        
    }
    
    public List<String> cidComplete(String query) {
        System.out.println("Cid auto complet");
        List<String> results = new ArrayList<String>();
        patologiaService.buscarCid(query + "%").forEach(p -> results.add(p.getCid()));                
        System.out.println("Lista com tamanho: " + results.size());
        return results;
    }

    
    public List<String> patologiaComplete(String query) {
        System.out.println("Patologia auto complet");
        List<String> results = new ArrayList<String>();
        patologiaService.buscarNome(query + "%").forEach(p -> results.add(p.getNome()));                
        System.out.println("Lista com tamanho: " + results.size());
        return results;
    }
    
    public List<String> pacienteComplete(String query) {
        System.out.println("Patciente auto complet");
        List<String> results = new ArrayList<String>();
        pacienteService.buscarNome(query + "%").forEach(p -> results.add(p.getNome()));                
        System.out.println("Lista com tamanho: " + results.size());
        return results;
    }
    
    public List<String> controleComplete(String query) {
        System.out.println("Controle auto complet");
        List<String> results = new ArrayList<String>();
        pacienteService.buscarNome(query + "%").forEach(p -> results.add(p.getNome()));                
        System.out.println("Lista com tamanho: " + results.size());
        return results;
    }
    
       
    public void onCidSelect(SelectEvent event) {
        List<Patologia> patologias = patologiaService.buscarCid(cid);
        if(patologias != null){
            if(patologias.size() > 0 ){
                analise.setPatologia(patologias.get(0));
                patologia = patologias.get(0).getNome();
            }
        }
    }
        
    public void onPatologiaSelect(SelectEvent event) {
        List<Patologia> patologias = patologiaService.buscarNome(patologia);
        if(patologias!= null){
        if(patologias.size() > 0 ){
            analise.setPatologia(patologias.get(0));
            cid = patologias.get(0).getCid();
            System.out.println("Definodo a patologia " + cid);
        }
        }
    }
    
    public void onPacienteSelect(SelectEvent event) {
        List<Paciente> pacientes = pacienteService.buscarNome(paciente);
        if (pacientes != null) {
            if (pacientes.size() > 0) {
                idPaciente = pacientes.get(0).getId();
            }
        }
    }
    
    public void onCorrelatoSelect(SelectEvent event) {
        List<Paciente> pacientes = pacienteService.buscarNome(paciente);
        if(pacientes != null){
            if(pacientes.size() > 0){
                idCorrelato = pacientes.get(0).getId();
            }
        }
    }
    
    
    public void onControleSelect(SelectEvent event) {
        List<Paciente> pacientes = pacienteService.buscarNome(correlato);
        if (pacientes != null) {
            if (pacientes.size() > 0) {
                idControle = pacientes.get(0).getId();
            }
        }
    }
    
    
    public List<SelectItem> getSelectVcfs() {
        List<SelectItem> vcfs = new ArrayList<SelectItem>();
        if(idPaciente != null){
            for (Vcf v : vcfService.buscarPacienteId(idPaciente)) {
                vcfs.add(new SelectItem(v.getId(), v.getNome()));
            }
        }
        return vcfs;
    }


    public List<SelectItem> getSelectControleVcfs() {
        List<SelectItem> vcfs = new ArrayList<SelectItem>();
        if(idControle != null){
            for (Vcf v : vcfService.buscarPacienteId(idControle)) {
                vcfs.add(new SelectItem(v.getId(), v.getNome()));
            }
        }
        return vcfs;
    }

    public void addCorrelato(){
        System.out.println("Correlato: " + idCorrelato);     
        Vcf vcfCorrelato = vcfService.buscarId(idCorrelato);
        if(vcfCorrelato!= null)
            analise.getVcfsCorrelatos().add(vcfCorrelato);
    }
    
    
    /*
    
    public void onPacienteSelect(SelectEvent event) {
        List<Paciente> pacientes = pacienteService.buscarNome(paciente);
        if(pacientes.size() > 0){            
            List<Vcf> vcfs = vcfService.buscarPacienteId(pacientes.get(0).getId());
            for(Vcf v : vcfs){
                System.out.println("v--> " + v.getNome());
            }
        }
    }
    
    public List<String> pacienteComplete(String query) {
        List<String> results = new ArrayList<String>();
        pacienteService.buscarNome(query + "%").forEach(p -> results.add(p.getNome()));                        
        return results;
    }
    
    */
    
}