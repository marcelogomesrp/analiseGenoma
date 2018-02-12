package org.analiseGenoma.model;

import java.io.Serializable;
import java.util.Objects;

public class DbBioInfoPK implements Serializable{    
    private Disease disease;
    private DbBio dbBio;

    public DbBioInfoPK() {
    }

    public DbBioInfoPK(Disease disease, DbBio dbBio) {
        this.disease = disease;
        this.dbBio = dbBio;
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public DbBio getDbBio() {
        return dbBio;
    }

    public void setDbBio(DbBio dbBio) {
        this.dbBio = dbBio;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.disease);
        hash = 53 * hash + Objects.hashCode(this.dbBio);
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
        final DbBioInfoPK other = (DbBioInfoPK) obj;
        if (!Objects.equals(this.disease, other.disease)) {
            return false;
        }
        if (!Objects.equals(this.dbBio, other.dbBio)) {
            return false;
        }
        return true;
    }
    
    
    
}
