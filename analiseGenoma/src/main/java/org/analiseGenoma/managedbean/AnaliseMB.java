/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
//import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.Patient;
import org.analiseGenoma.model.Disease;
import org.analiseGenoma.model.Vcf;
import org.analiseGenoma.service.AnaliseService;
import org.analiseGenoma.service.PatientService;
import org.analiseGenoma.service.DiseaseService;
import org.analiseGenoma.service.VcfService;
import org.primefaces.event.SelectEvent;
import org.analiseGenoma.managedbean.util.FacesUtil;
import org.analiseGenoma.managedbean.util.RequestParam;
import org.analiseGenoma.model.Filtro;
import org.analiseGenoma.service.FiltroService;

@Named(value = "analiseMB")
//@RequestScoped
@ViewScoped
public class AnaliseMB implements Serializable {

    @Inject
    private FacesContext context;
    @Inject
    private AnaliseService analiseService;
    @Inject
    private DiseaseService patologiaService;
    @Inject
    private PatientService pacienteService;
    @Inject
    private VcfService vcfService;
    @Inject
    private AnaliseSelecionarVarianteMB selecionarMB;
    @Inject
    private FiltroService filtroService;
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
    private Long idVcfFather;
    private Long idVcfMother;
    private boolean applyFilter;
    
    @Inject
    @RequestParam
    private String id;

    @PostConstruct
    public void init() {
        System.out.println("Iniciando a pagina novamente...");
        analise = new Analise();
        applyFilter = true;
        Long value = (Long) FacesUtil.getSessionMapValue("idAnalise");
        if(value!=null){
            
            Analise analiseBd = analiseService.buscarPorId(value);
            analise.setPatologia(analiseBd.getPatologia());
            analise.setObservacao(analiseBd.getObservacao());
            analise.setPaciente(analiseBd.getPaciente());
            analise.setControle(analiseBd.getControle());
            analise.setVcf(analiseBd.getVcf());
            
            
        }
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
        this.patologia = patologia.toUpperCase();
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

    public Long getIdVcfFather() {
        return idVcfFather;
    }

    public void setIdVcfFather(Long idVcfFather) {
        this.idVcfFather = idVcfFather;
    }

    public Long getIdVcfMother() {
        return idVcfMother;
    }

    public void setIdVcfMother(Long idVcfMother) {
        this.idVcfMother = idVcfMother;
    }

    public void setAnalise(Analise analise) {
        if (analise == null) {
            analise = new Analise();
        }
        this.analise = analise;
    }

    public String adicionar() {
        analise.setEstado("criando");
        if (idPaciente != null) {
            analise.setPaciente(pacienteService.buscarId(idPaciente));
        }
        if (idControle != null) {
            analise.setControle(pacienteService.buscarId(idControle));
        }
        if (idVcf != null) {
            analise.setVcf(vcfService.buscarId(Long.valueOf(idVcf)));
        }

        if (idVcfFather != null) {
            analise.setVcfFather(vcfService.buscarId(idVcfFather));
        }
        if (idVcfMother != null) {
            analise.setVcfMother(vcfService.buscarId(idVcfMother));
        }

        //if(idControle != null)
        //analise.setVcfControle(vcfService.buscarId(Long.valueOf(idControle)));
        if (analise.getVcfsCorrelatos() != null) {
            for (Vcf vcf : analise.getVcfsCorrelatos()) {
                vcfService.atualizar(vcf);
            }
        }

        analiseService.adicionar(analise);
        
        Filtro filtro = null;
        
        
        if(applyFilter){
            filtro = filtroService.makeFiltro(analise);
        }
        else{
            filtro = filtroService.makeFiltroDefault(analise);
        }
        filtro.setName(analise.getNome());
        filtroService.persiste(filtro);

        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Cadastrado com sucesso"));

        FacesUtil.setSessionMapValue("id", analise.getId());
        analise.setEstado("criado");
        analiseService.atualizar(analise);
        analise = new Analise();
        //return "analise_nova.xhtml?faces-redirect=true";
        return "analise_selecionar_variantes.xhtml?faces-redirect=true";
        //return selecionarMB.ShowPage(analise);

    }

    public List<String> cidComplete(String query) {
        System.out.println("Cid auto complet");
        List<String> results = new ArrayList<String>();
        patologiaService.buscarCid(query + "%").forEach(p -> results.add(p.getIcd()));
        System.out.println("Lista com tamanho: " + results.size());
        return results;
    }

    public List<String> patologiaComplete(String query) {
        query = query.toUpperCase();
        System.out.println("Patologia auto complet: " + query);
        List<String> results = new ArrayList<String>();
        patologiaService.buscarNome(query + "%").forEach(p -> results.add(p.getName()));
        System.out.println("Lista com tamanho: " + results.size());
        return results;
    }

    public List<String> pacienteComplete(String query) {
        System.out.println("Patciente auto complet");
        List<String> results = new ArrayList<String>();
        pacienteService.buscarNome(query.toUpperCase() + "%").forEach(p -> results.add(p.getName()));
        System.out.println("Lista com tamanho: " + results.size());
        return results;
    }

    public List<String> controleComplete(String query) {
        System.out.println("Controle auto complet");
        List<String> results = new ArrayList<String>();
        pacienteService.buscarNome(query + "%").forEach(p -> results.add(p.getName()));
        System.out.println("Lista com tamanho: " + results.size());
        return results;
    }

    public void onCidSelect(SelectEvent event) {
        List<Disease> patologias = patologiaService.buscarCid(cid);
        if (patologias != null) {
            if (patologias.size() > 0) {
                analise.setPatologia(patologias.get(0));
                patologia = patologias.get(0).getName();
            }
        }
    }

    public void onPatologiaSelect(SelectEvent event) {
        List<Disease> patologias = patologiaService.buscarNome(patologia);
        if (patologias != null) {
            if (patologias.size() > 0) {
                analise.setPatologia(patologias.get(0));
                cid = patologias.get(0).getIcd();
                System.out.println("Definodo a patologia " + cid);
            }
        }
    }

    public void onPacienteSelect(SelectEvent event) {
        List<Patient> pacientes = pacienteService.buscarNome(paciente);
        if (pacientes != null) {
            if (pacientes.size() > 0) {
                idPaciente = pacientes.get(0).getId();
            }
        }
    }

    public void onCorrelatoSelect(SelectEvent event) {
        List<Patient> pacientes = pacienteService.buscarNome(paciente);
        if (pacientes != null) {
            if (pacientes.size() > 0) {
                idCorrelato = pacientes.get(0).getId();
            }
        }
    }

    public void onControleSelect(SelectEvent event) {
        List<Patient> pacientes = pacienteService.buscarNome(correlato);
        if (pacientes != null) {
            if (pacientes.size() > 0) {
                idControle = pacientes.get(0).getId();
            }
        }
    }

    public List<SelectItem> getSelectVcfs() {
        List<SelectItem> vcfs = new ArrayList<SelectItem>();
        if (idPaciente != null) {
            for (Vcf v : vcfService.buscarPacienteId(idPaciente)) {
                vcfs.add(new SelectItem(v.getId(), v.getNome()));
            }
        }
        return vcfs;
    }

    public List<SelectItem> getSelectVcfsFather() {
        List<SelectItem> vcfs = new ArrayList<SelectItem>();
        if (idPaciente != null) {
            Patient p = pacienteService.buscarId(idPaciente);
            if (p.getFather() != null) {
                if (p.getFather().getId() != null) {
                    for (Vcf v : vcfService.buscarPacienteId(p.getFather().getId())) {
                        vcfs.add(new SelectItem(v.getId(), v.getNome()));
                    }
                }
            }
        }
        return vcfs;
    }

    public List<SelectItem> getSelectVcfsMother() {
        List<SelectItem> vcfs = new ArrayList<SelectItem>();
        if (idPaciente != null) {
            Patient p = pacienteService.buscarId(idPaciente);
            if (p.getMother() != null) {
                if (p.getMother().getId() != null) {
                    for (Vcf v : vcfService.buscarPacienteId(p.getMother().getId())) {
                        vcfs.add(new SelectItem(v.getId(), v.getNome()));
                    }
                }
            }
        }
        return vcfs;
    }

    public List<SelectItem> getSelectControleVcfs() {
        List<SelectItem> vcfs = new ArrayList<SelectItem>();
        if (idControle != null) {
            for (Vcf v : vcfService.buscarPacienteId(idControle)) {
                vcfs.add(new SelectItem(v.getId(), v.getNome()));
            }
        }
        return vcfs;
    }

    public void addCorrelato() {
        System.out.println("Correlato: " + idCorrelato);
        Vcf vcfCorrelato = vcfService.buscarId(idCorrelato);
        if (vcfCorrelato != null) {
            analise.getVcfsCorrelatos().add(vcfCorrelato);
        }
    }

    public boolean pacienteHasFather() {
        if (idPaciente != null) {
            Patient p = pacienteService.buscarId(idPaciente);
            if (p.getFather() != null) {
                return true;
            }
        }
        return false;
    }

    public boolean pacienteHasMother() {
        if (idPaciente != null) {
            Patient p = pacienteService.buscarId(idPaciente);
            if (p.getMother() != null) {
                return true;
            }
        }
        return false;
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
    public void validateVcf(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        String vIdVCF = (String) o;
        if ((vIdVCF == null) || (vIdVCF.isEmpty())) {
            FacesMessage message
                    = new FacesMessage("vcf is required");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            //context.addMessage("correlato",message);
            throw new ValidatorException(message);
        }
    }
    
    public void validateNome(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        String name = (String) o;
        if ((name == null) || (name.isEmpty())) {
            FacesMessage message
                    = new FacesMessage("name is required");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            //context.addMessage("correlato",message);
            throw new ValidatorException(message);
        }
    }

    public boolean getApplyFilter() {
        return applyFilter;
    }

    public void setApplyFilter(boolean applyFilter) {
        this.applyFilter = applyFilter;
    }
    
    public List<String> completeFilter(String query) {
        //List<String> results = new ArrayList<String>();
//        for(int i = 0; i < 10; i++) {
//            results.add(query + i);
//        }
        return filtroService.find().stream().map(f -> f.getAnalise().getNome()).collect(Collectors.toList());
        //return results;
    }
    

}
