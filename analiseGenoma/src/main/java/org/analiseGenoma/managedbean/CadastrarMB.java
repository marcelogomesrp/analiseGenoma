package org.analiseGenoma.managedbean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.model.User;
import org.analiseGenoma.service.UsuarioService;

@Named(value = "cadastrarMB")
@RequestScoped
public class CadastrarMB implements Serializable {

    @Inject
    private FacesContext context;
    @Inject
    private UsuarioService usuarioService;
    @Inject
    private User user;
    
    private User usuario;
    private String confirmaSenha;
//    private UploadedFile uploadedFile;

    @PostConstruct
    public void init() {
        user = new User();
        usuario = new User();
        confirmaSenha = "";
    }

    private void addMessage(String message) {
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(message));
    }

//    public UploadedFile getUploadedFile() {
//        return uploadedFile;
//    }
//
//    public void setUploadedFile(UploadedFile uploadedFile) {
//        this.uploadedFile = uploadedFile;
//    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public String getConfirmaSenha() {
        return confirmaSenha;
    }

    public void setConfirmaSenha(String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }

    public void limpar() {
        usuario = new User();
        confirmaSenha = "";
    }

    public String salvar() {
        System.out.println("Salvando...");
        System.out.println("Usuario: " + usuario.toString());
//        if (uploadedFile == null) {
            try {
                usuarioService.adicionar(usuario, confirmaSenha);
            } catch (Exception ex) {
                //Logger.getLogger(CadastrarMB.class.getName()).log(Level.SEVERE, null, ex);
                context.getExternalContext()
                        .getFlash().setKeepMessages(true);
                context.addMessage(null, new FacesMessage(ex.getMessage()));
            }
//        } 
//        else {
//            try{
//            usuarioService.adicionar(usuario, confirmaSenha, uploadedFile.getContents());
//            }catch(Exception ex){
//                context.getExternalContext()
//                        .getFlash().setKeepMessages(true);
//                context.addMessage(null, new FacesMessage(ex.getMessage()));
//            }
//        }
        this.limpar();
        context.getExternalContext()
                .getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Cadastrado com sucesso"));
        return "cadastrar.xhtml?faces-redirect=true";
        //System.out.println("Fazendo o upload: " + uploadedFile.getFileName() );

//        try {                    
//            File file = new File("/tmp/analise/", uploadedFile.getFileName());
//
//            OutputStream out = new FileOutputStream(file);
//            out.write(uploadedFile.getContents());
//            out.close();
//            
//            
//            FacesContext.getCurrentInstance().addMessage(
//                    null, new FacesMessage("Upload completo",
//                            "O arquivo " + uploadedFile.getFileName() + " foi salvo!"));
//        } catch (IOException e) {
//            FacesContext.getCurrentInstance().addMessage(
//                    null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", e.getMessage()));
//        } catch(Exception ex){
//            FacesContext.getCurrentInstance().addMessage(
//                    null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro2", ex.getMessage()));
//        }
    }

}
