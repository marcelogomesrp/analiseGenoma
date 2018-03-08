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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.managedbean.util.FacesUtil;
import org.analiseGenoma.managedbean.util.RequestParam;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.Filtro;
import org.analiseGenoma.model.Paciente;
import org.analiseGenoma.model.Vcf;
import org.analiseGenoma.service.AnaliseService;
import org.analiseGenoma.service.FiltroService;
import org.analiseGenoma.service.PacienteService;
import org.analiseGenoma.service.VcfService;
import org.primefaces.event.SelectEvent;

@Named(value = "analiseEditMB")
//@RequestScoped
@ViewScoped
public class AnaliseEditarMB implements Serializable {

    private Analise analise;
    private String patologia;
    private String cid;
    private String paciente;
    private Long idPaciente;
    private String correlato;
    private String idVcf;
    private Long idCorrelato;
    private String controle;
    private Long idControle;
    private Long idVcfFather;
    private Long idVcfMother;
    private boolean applyFilter;

    @Inject
    @RequestParam
    private String id;
    @Inject
    private AnaliseService analiseService;
    @Inject
    private PacienteService pacienteService;

    @Inject
    private FacesContext context;
    @Inject
    private VcfService vcfService;
    @Inject
    private FiltroService filtroService;

    @PostConstruct
    public void init() {
        System.out.println("Iniciando a pagina novamente...");
        analise = new Analise();
        Long value = (Long) FacesUtil.getSessionMapValue("idAnalise");
        if (value != null) {

            Analise analiseBd = analiseService.buscarPorId(value);
            analise.setNome(analiseBd.getNome());
            analise.setPatologia(analiseBd.getPatologia());
            analise.setObservacao(analiseBd.getObservacao());
            analise.setPaciente(analiseBd.getPaciente());
            analise.setControle(analiseBd.getControle());
            analise.setVcf(analiseBd.getVcf());
            patologia = analiseBd.getPatologia().getName();
            cid = analiseBd.getPatologia().getIcd();
            paciente = analiseBd.getPaciente().getNome();
            idPaciente = analiseBd.getPaciente().getId();
            
            idVcf = analiseBd.getVcf().getNome();
            System.out.println("nada");

        }
    }

    public Analise getAnalise() {
        return analise;
    }

    public void setAnalise(Analise analise) {
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
        
        Filtro filtro;
        
        if(applyFilter){
            filtro = filtroService.makeFiltro(analise);
        }
        else{
            filtro = filtroService.makeFiltroDefault(analise);
        }
        
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

    public void validateNome(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        String name = (String) o;
        if ((name == null) || (name.isEmpty())) {
            FacesMessage message
                    = new FacesMessage("name is required");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }

    public String duplicate(Analise analise) {
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        FacesUtil.setSessionMapValue("idAnalise", analise.getId());
        return "analise_editar.xhtml?faces-redirect=true";
    }

    public String getPatologia() {
        return patologia;
    }

    public void setPatologia(String patologia) {
        this.patologia = patologia;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public List<String> pacienteComplete(String query) {
        System.out.println("Patciente auto complet");
        List<String> results = new ArrayList<String>();
        pacienteService.buscarNome(query.toUpperCase() + "%").forEach(p -> results.add(p.getNome()));
        System.out.println("Lista com tamanho: " + results.size());
        return results;
    }

    public void onPacienteSelect(SelectEvent event) {
        List<Paciente> pacientes = pacienteService.buscarNome(paciente);
        if (pacientes != null) {
            if (pacientes.size() > 0) {
                idPaciente = pacientes.get(0).getId();
            }
        }
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getCorrelato() {
        return correlato;
    }

    public void setCorrelato(String correlato) {
        this.correlato = correlato;
    }

    public String getIdVcf() {
        return idVcf;
    }

    public void setIdVcf(String idVcf) {
        this.idVcf = idVcf;
    }

    public Long getIdCorrelato() {
        return idCorrelato;
    }

    public void setIdCorrelato(Long idCorrelato) {
        this.idCorrelato = idCorrelato;
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

    public boolean isApplyFilter() {
        return applyFilter;
    }

    public void setApplyFilter(boolean applyFilter) {
        this.applyFilter = applyFilter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PacienteService getPacienteService() {
        return pacienteService;
    }

    public void setPacienteService(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

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

    public List<SelectItem> getSelectVcfs() {
        List<SelectItem> vcfs = new ArrayList<SelectItem>();
        if (idPaciente != null) {
            for (Vcf v : vcfService.buscarPacienteId(idPaciente)) {
                vcfs.add(new SelectItem(v.getId(), v.getNome()));
            }
        }
        return vcfs;
    }

    public boolean pacienteHasFather() {
        if (idPaciente != null) {
            Paciente p = pacienteService.buscarId(idPaciente);
            if (p.getFather() != null) {
                return true;
            }
        }
        return false;
    }

    public boolean pacienteHasMother() {
        if (idPaciente != null) {
            Paciente p = pacienteService.buscarId(idPaciente);
            if (p.getMother() != null) {
                return true;
            }
        }
        return false;
    }

    public List<SelectItem> getSelectVcfsFather() {
        List<SelectItem> vcfs = new ArrayList<SelectItem>();
        if (idPaciente != null) {
            Paciente p = pacienteService.buscarId(idPaciente);
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
            Paciente p = pacienteService.buscarId(idPaciente);
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

    public List<String> controleComplete(String query) {
        System.out.println("Controle auto complet");
        List<String> results = new ArrayList<String>();
        pacienteService.buscarNome(query + "%").forEach(p -> results.add(p.getNome()));
        System.out.println("Lista com tamanho: " + results.size());
        return results;
    }

    public void onControleSelect(SelectEvent event) {
        List<Paciente> pacientes = pacienteService.buscarNome(correlato);
        if (pacientes != null) {
            if (pacientes.size() > 0) {
                idControle = pacientes.get(0).getId();
            }
        }
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

}
