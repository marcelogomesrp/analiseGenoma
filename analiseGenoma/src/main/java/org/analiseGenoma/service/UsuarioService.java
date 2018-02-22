package org.analiseGenoma.service;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.UsuarioDao;
import org.analiseGenoma.model.Usuario;

@Named
public class UsuarioService implements Serializable {

    @Inject
    private UsuarioDao usuarioDao;
    @Inject 
    private FacesContext context;

    
    @PostConstruct
    public void init() {
    }

    @Transactional
    public void adicionar(Usuario usuario, String confirmaSenha) throws Exception {
        if(senhaValida(usuario.getSenha(), confirmaSenha)){
            usuarioDao.persist(usuario);
        }
    }

    @Transactional
    public void adicionar(Usuario usuario, String confirmaSenha, byte[] contents) throws Exception {
//        if(contents.length > 0)
//            usuario.setImage(contents);
        adicionar(usuario, confirmaSenha);
        /*
        if (contents.length > 0) {
            try {
                String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
                System.out.println("-->Path: " + path);
                File file = new File(path, usuario.getId() + ".png");
                OutputStream out = new FileOutputStream(file);
                out.write(contents);
                out.close();                
            } catch (Exception ex) {
                System.out.println("Erro: " + ex.getMessage());
            }
        }
        */
    }

    private boolean senhaValida(String senha, String confirmaSenha) throws Exception {
        if(senha.isEmpty())
            throw new Exception("A senha nao pode ser vazia");
        if(senha == ""){
            throw new Exception("A senha nao pode ser vazia");
        }
        if(!senha.equals(confirmaSenha)){
            throw new Exception("A senha e a confirmaçao da senha nao tem o mesmo valor");
        }
        return true;
    }
    
    public List<Usuario> buscarRevisores(){
        return usuarioDao.buscarPorRevisor();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioDao.findById(id);
    }

    public Usuario findRevisoresByName(String name) {
        List<Usuario> users = usuarioDao.findRevisorByName(name);
        if(users.size() == 1)
            return users.get(0);
        return null;
    }

    public List<Usuario> findByExample(Usuario u) {
        try {
            return usuarioDao.findByExample(u);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
