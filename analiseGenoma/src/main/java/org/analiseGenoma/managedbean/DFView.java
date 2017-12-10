package org.analiseGenoma.managedbean;

import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

@Named(value = "dfView")
@RequestScoped
public class DFView {

    public void viewCarsCustomized() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", 640);
        options.put("height", 340);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");

        RequestContext.getCurrentInstance().openDialog("viewCars", options, null);
    }

    public void viewCars() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        RequestContext.getCurrentInstance().openDialog("viewCars", options, null);
    }
}
