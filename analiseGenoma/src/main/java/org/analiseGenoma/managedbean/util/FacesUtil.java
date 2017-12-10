package org.analiseGenoma.managedbean.util;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;




public class FacesUtil {
    /*
    public static String getActionAttribute(ActionEvent event, String name) {
        return (String) event.getComponent().getAttributes().get(name);
    }
    public static void setApplicationMapValue(String key, Object value) {
        FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put(key, value);
    }
      */ 
    public static Object getSessionMapValue(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
    }
    
    public static void setSessionMapValue(String key, Object value) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
    }

}
