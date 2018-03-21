package org.analiseGenoma.service;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.UserDao;
import org.analiseGenoma.model.User;

@Named
public class UsuarioService implements Serializable {

    @Inject
    private UserDao usuarioDao;
    @Inject 
    private FacesContext context;

    
    @PostConstruct
    public void init() {
    }

    @Transactional
    public void adicionar(User usuario, String confirmaSenha) throws Exception {
        if(senhaValida(usuario.getPassword(), confirmaSenha)){
            usuarioDao.persist(usuario);
        }
    }

    @Transactional
    public void adicionar(User usuario, String confirmaSenha, byte[] contents) throws Exception {
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
    
    public List<User> buscarRevisores(){
        //return usuarioDao.buscarPorRevisor();
        return null;
        
    }

    public User buscarPorId(Long id) {
        return usuarioDao.findById(id);
    }

    public User findRevisoresByName(String name) {
        /*
        List<User> users = usuarioDao.findRevisorByName(name);
        if(users.size() == 1)
            return users.get(0);
*/
        return null;
    }

    public List<User> findByExample(User u) {
        try {
            return usuarioDao.findByExample(u);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
