/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;

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
public class ATesteMB implements Serializable {

    @Inject
    UsuarioService us;

    public void vai() {
        System.out.println("OK");
        User u = new User();
        //u.setId(1L);
        u.setEmail("admin%");
        List<User> list = us.findByExample(u);
        for (User user : list) {
            System.out.println("--> " + user.toString());
        }
    }

    public void vai2() {
        System.out.println("inicio");
        try {
            JobOperator jobOperator = BatchRuntime.getJobOperator();
            Properties props = new Properties();
            
           props.setProperty("nome", "nome_passado");
            System.out.println(jobOperator.start("meuJob", props));

        } catch (Exception ex) {
            System.out.println("Erro no vai2: " + ex.getMessage());
        }
        System.out.println("fim");
    }
    
        public void vai3() {
        System.out.println("inicio");
        try {
            JobOperator jobOperator = BatchRuntime.getJobOperator();
            Properties props = new Properties();
            
           props.setProperty("nome", "nome_passado");
            System.out.println(jobOperator.start("meuJob", props));

        } catch (Exception ex) {
            System.out.println("Erro no vai2: " + ex.getMessage());
        }
        System.out.println("fim");
    }

}
