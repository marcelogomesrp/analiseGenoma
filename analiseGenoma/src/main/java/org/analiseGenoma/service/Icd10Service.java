package org.analiseGenoma.service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.Icd10Dao;
import org.analiseGenoma.model.Icd10;

@Named
public class Icd10Service extends Service<Icd10> {

    public Icd10Service() {
        super(Icd10.class);
    }

    private Icd10Dao getDao() {
        return ((Icd10Dao) dao);
    }

    @Transactional
    public void upload(byte[] contents) {
        if (contents.length > 0) {
            Runnable r = () -> {
                this.importar(contents);
            };
            Thread t = new Thread(r);
            t.start();
        }
    }
    
    private void importar(byte[] contents) {
        String[] arquivo = new String(contents, StandardCharsets.UTF_8).split("\n");
        List<Icd10> lista = new ArrayList<>();
        for (String ln : arquivo) {
            Icd10 icd = new Icd10();           
            icd.setCode(ln.substring(6, 13).trim());
            icd.setDescription(ln.substring(16, 60).trim());
            icd.setLongDescription(ln.substring(77).trim());
//            String[] linha = ln.split("\t");
//            Icd10 icd = new Icd10();           
//            icd.setCode(linha[1]);
//            icd.setDescription(linha[3]);
//            icd.setLongDescription(linha[4]);
            
            this.persiste(icd);
        }
        //this.adicionar(lista);
    }
    
    public void adicionar(List<Icd10> list) {
        list.forEach((e) -> {
            persiste(e);
        });
    }
    

}
