package org.analiseGenoma.model;

import java.io.Serializable;
import java.util.Objects;

public class DiseaseNameSynonymPK implements Serializable {
    private Long id;
    private Disease disease;

    public DiseaseNameSynonymPK() {
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.id);
        hash = 83 * hash + Objects.hashCode(this.disease);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DiseaseNameSynonymPK other = (DiseaseNameSynonymPK) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.disease, other.disease)) {
            return false;
        }
        return true;
    }
    
    
    
}
