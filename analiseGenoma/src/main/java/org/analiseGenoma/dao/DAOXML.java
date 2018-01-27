/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.analiseGenoma.dao;

import java.io.InputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author marcelo
 */
public class DAOXML<T> implements Serializable {

    private final Class<T> classe;

    public DAOXML(Class<T> classe) {
        this.classe = classe;
    }

    public T XmlToObject(InputStream inputStream) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(classe);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (T) unmarshaller.unmarshal(inputStream);
    }

    public Writer ObjectToXML(T obj) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(classe);
        Marshaller marshaller = jaxbContext.createMarshaller();
        StringWriter writer = new StringWriter();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(obj, writer);
        return writer;
    }

}
