package org.analiseGenoma.service;

import java.io.InputStream;
import java.io.Serializable;
import java.io.Writer;
import javax.inject.Inject;
import org.analiseGenoma.dao.xml.DAOXML;

public class XMLService<T> implements Serializable {

    protected Class<T> classe;

    @Inject
    protected DAOXML<T> daoXML;

    private DAOXML<T> getDaoXML() {
        return daoXML;
    }

    public XMLService(Class<T> classe) {
        this.classe = classe;
    }

    public T XmlToObject(InputStream inputStream) throws Exception {
        return getDaoXML().XmlToObject(inputStream);

    }

    public Writer ObjectToXML(T obj) throws Exception {
        return getDaoXML().ObjectToXML(obj);
    }

}
