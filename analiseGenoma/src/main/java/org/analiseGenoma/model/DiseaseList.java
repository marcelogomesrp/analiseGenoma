package org.analiseGenoma.model;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="diseases")
@XmlAccessorType(XmlAccessType.FIELD)
public class DiseaseList implements Serializable {
    
    @XmlElement(name = "disease")
    private List<Disease> diseases;

    public List<Disease> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<Disease> diseases) {
        this.diseases = diseases;
    }
    
}
