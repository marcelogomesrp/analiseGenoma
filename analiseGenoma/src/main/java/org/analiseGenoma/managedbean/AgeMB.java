package org.analiseGenoma.managedbean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.model.Age;
import org.analiseGenoma.service.AgeService;


@Named(value = "ageMB")
@ViewScoped
public class AgeMB implements Serializable {
    @Inject private FacesContext context;
    @Inject private AgeService ageService;
    private Age age;
    private List<Age> ages;

    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public List<Age> getAges() {
        return ages;
    }

    public void setAges(List<Age> ages) {
        this.ages = ages;
    }
    
    
    
    @PostConstruct
    public void init() {
        age = new Age();
        ages = ageService.find();
    }
    
    public void add(){
        ageService.persiste(age);
        context.addMessage(null, new FacesMessage("It's done") );
        age = new Age();
        ages = ageService.find();
    }
}