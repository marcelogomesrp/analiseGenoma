package org.analiseGenoma.sessionbean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.analiseGenoma.model.Analise;


@Named(value = "analiseSB")
@SessionScoped
public class AnaliseSB implements Serializable {
    private Analise analise;
    
    @PostConstruct
    public void init() {
        analise = new Analise();
    }

    public Analise getAnalise() {
        return analise;
    }

    public void setAnalise(Analise analise) {
        this.analise = analise;
    }

    public void reset() {
        this.analise = new Analise();
    }
    
}