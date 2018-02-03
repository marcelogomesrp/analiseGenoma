package org.analiseGenoma.service.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Line  implements Serializable{
    private List<String> fields;
    private final String regex = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

    Line(String line) {
        fields = new ArrayList<>();
        String[] splitted = line.split(regex);
        for (String field : splitted) {
            fields.add(field.replaceAll("^\"|(\")$", "").trim());
        }
    }

    public int getSize(){
        return fields.size();
    }
    
    public String getField(int x) {
        return fields.get(x).replace("'", "\\'");        
    }
    
    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "Line{" + "fields=" + fields + '}';
    }

    
}
