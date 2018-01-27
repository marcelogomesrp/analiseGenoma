package org.analiseGenoma.dao.xml;

import java.io.InputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.analiseGenoma.model.Gene;

public class DAOXML<T> implements Serializable {

    private final Class<T> classe;

    public DAOXML(Class<T> classe) {
        this.classe = classe;
    }
    
    //ok
    public T XmlToObject(InputStream inputStream) throws Exception{
        JAXBContext jaxbContext = JAXBContext.newInstance(classe);        
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();        
        return (T) unmarshaller.unmarshal(inputStream);
    }
    
   
    public Writer ObjectToXML(T obj) throws Exception{
        JAXBContext jaxbContext = JAXBContext.newInstance(classe);
        Marshaller marshaller = jaxbContext.createMarshaller();
        StringWriter writer = new StringWriter();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(obj, writer);
        return writer;
    }
    

} 

/*
        System.out.println("start");
        JAXBContext jaxbContext = JAXBContext.newInstance(Gene.class);
        
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Gene gene = (Gene) unmarshaller.unmarshal(new File("/marcelo/java/github/analiseGenoma/data/tmp/gene.xml"));
        System.out.println("Gene: " + gene.toString());
        for(GeneSymbolSynonym s : gene.getGeneSymbolSynonym()){
            System.out.println("S" + s.getSymbol());
        }

*/
