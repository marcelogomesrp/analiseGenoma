package org.analiseGenoma.dao.xml;

import java.io.Serializable;
import org.analiseGenoma.model.Gene;

public class GeneDaoXML extends DAOXML<Gene> implements Serializable {

    public GeneDaoXML() {
        super(Gene.class);
    }

//    public Writer teste(Gene gene) throws Exception{
//        JAXBContext jaxbContext = JAXBContext.newInstance(Gene.class);
//        Marshaller marshaller = jaxbContext.createMarshaller();
//        StringWriter writer = new StringWriter();
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//        marshaller.marshal(gene, writer);
//        return writer;
//        //System.out.println("-->" + writer);
//    }
//
//    @Override
//    public Writer ObjectToXML(Gene gene) throws Exception {
//        JAXBContext jaxbContext = JAXBContext.newInstance(Gene.class);
//        Marshaller marshaller = jaxbContext.createMarshaller();
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//        StringWriter writer = new StringWriter();
//        marshaller.marshal(Gene.class, writer);
//        return writer;
//    }

}
