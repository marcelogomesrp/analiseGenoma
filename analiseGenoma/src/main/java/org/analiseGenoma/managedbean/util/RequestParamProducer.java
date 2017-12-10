package org.analiseGenoma.managedbean.util;

import java.io.Serializable;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

public class RequestParamProducer implements Serializable {

    private static final long serialVersionUID = -4260202951977249652L;
    @Inject
    FacesContext facesContext;

    // Producer for @RequestParam
    @Produces
    @RequestParam
    String getRequestParameter(InjectionPoint ip) {
        String name = ip.getAnnotated().getAnnotation(RequestParam.class)
                .value();

        if ("".equals(name))
            name = ip.getMember().getName();

        return facesContext.getExternalContext().getRequestParameterMap()
                .get(name);
    }
}