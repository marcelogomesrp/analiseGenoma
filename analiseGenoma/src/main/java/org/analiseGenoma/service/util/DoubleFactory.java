package org.analiseGenoma.service.util;

public class DoubleFactory {
    public Double make(String valor){
        
        try{
            if("-".equals(valor)){
                return null;
            }
            Double d = Double.valueOf(valor);
            return d;
        }catch(Exception ex){
            
        }
        return null;
    }
}
