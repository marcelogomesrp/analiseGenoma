package org.analisegenoma.dao;

import java.io.Serializable;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class UsuarioDaoTest implements Serializable{
    
    private EntityManagerFactory emf;
    
    @Before
    public void init(){
        //emf = Persistence.createEntityManagerFactory("AnaliseGenomaPU");        
    }
    
    @After
    public void destroy(){
        //emf.close();        
    }
    
    
    @Test
    public void test1(){
        
    }
}
