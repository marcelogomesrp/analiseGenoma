/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.analiseGenoma.managedbean;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;


@Named(value = "viewAlertNoFilterMB")
@RequestScoped

public class ViewAlertNoFilterMB implements Serializable {
    public void close(){
       RequestContext.getCurrentInstance().closeDialog(""); 
       
    }
}
