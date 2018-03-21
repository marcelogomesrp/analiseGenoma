/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.model.User;
import org.analiseGenoma.service.UsuarioService;

/**
 *
 * @author marcelo
 */
@Named(value = "aTesteMB")
@RequestScoped
public class ATesteMB implements Serializable{
    
    @Inject
    UsuarioService us;
    
    public void vai(){
        System.out.println("OK");
        User u = new User();
        //u.setId(1L);
        u.setEmail("admin%");
        List<User> list = us.findByExample(u);
        for(User user : list){
            System.out.println("--> " + user.toString());
        }
    }
    
}
