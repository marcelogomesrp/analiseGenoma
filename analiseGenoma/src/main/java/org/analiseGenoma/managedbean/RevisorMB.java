package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.model.Usuario;
import org.analiseGenoma.service.UsuarioService;

@Named(value = "revisorMB")
@RequestScoped
public class RevisorMB implements Serializable {
    
    @Inject
    private ThemeSwitcherMB themeSwitcherMB;
    
    @Inject
    private UsuarioMB usuarioMB;
    @Inject
    private UsuarioService usuarioService;
    @Inject 
    private FacesContext context;
    
    public String login(){
        Usuario usuario = new Usuario();
        usuario.setRevisor(true);
        usuario.setEmail(usuarioMB.getUsuario().getEmail());
        usuario.setSenha(usuarioMB.getUsuario().getSenha());
        
        List<Usuario> listUser = usuarioService.findByExample(usuario);
        if(listUser.size() == 1){
            usuarioMB.setUsuario(listUser.get(0));
            themeSwitcherMB.changeRevisor();
            return "revisor/index.xhtml";
        }else{
            usuarioMB.getUsuario().setSenha("");
            context.getExternalContext()
                .getFlash().setKeepMessages(true);
            context.addMessage(null, new FacesMessage("User not allow"));
            return "entrar";
        }
        
    }
    
}
