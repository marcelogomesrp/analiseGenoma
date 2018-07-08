/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.analiseGenoma.teste;

import javax.batch.api.AbstractBatchlet;
import javax.inject.Named;

/**
 *
 * @author marcelo
 */
@Named
public class meuMeta  extends AbstractBatchlet {

    @Override
    public String process() throws Exception {
        System.out.println("Gerando os metadados.... :D");
        return "COMPLETED";
    }
    
}
