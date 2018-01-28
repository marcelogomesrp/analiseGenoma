package org.analiseGenoma.model;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="genes")
@XmlAccessorType(XmlAccessType.FIELD)
public class GeneList implements Serializable {
    
    @XmlElement(name = "gene")
    private List<Gene> genes;

    public List<Gene> getGenes() {
        return genes;
    }

    public void setGenes(List<Gene> genes) {
        this.genes = genes;
    }
    
}