package org.analiseGenoma.teste;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.GeneSymbolSynonym;

public class Teste {
    public static void main(String[] args) throws Exception {
        
        
         File initialFile = new File("/marcelo/java/github/analiseGenoma/data/tmp/gene.xml");
        InputStream targetStream = new FileInputStream(initialFile);
        
        GeneDao gdxml = new GeneDao();
        
        Gene g = gdxml.XmlToObject(targetStream);
        System.out.println("-->" + g.toString());
                
//        for(GeneSymbolSynonym s : g.getGeneSymbolSynonym()){
//            System.out.println("S" + s.getSymbol());
//        }

//        Gene gene = new Gene();
//        gene.setName("GeneTeste");
//        gene.setSymbol("GT");
//        GeneSymbolSynonym gSS = new GeneSymbolSynonym("GT1");
//        gene.setGeneSymbolSynonym(new HashSet<>());
//        gene.getGeneSymbolSynonym().add(new GeneSymbolSynonym("GT1"));
//        gene.getGeneSymbolSynonym().add(new GeneSymbolSynonym("GT2"));
//        
//        System.out.println(gdxml.ObjectToXML(gene));
//        
        
        
        
//        
//        Gene gene = new Gene();
//        gene.setName("GeneTeste");
//        gene.setSymbol("GT");
////        GeneSymbolSynonym gSS = new GeneSymbolSynonym("GT1");
////        gene.setGeneSymbolSynonym(new HashSet<>());
////        gene.getGeneSymbolSynonym().add(new GeneSymbolSynonym("GT1"));
////        gene.getGeneSymbolSynonym().add(new GeneSymbolSynonym("GT2"));
//        
//        System.out.println(gdxml.ObjectToXML(gene));

        
        
        
        
//        System.out.println("start");
//        JAXBContext jaxbContext = JAXBContext.newInstance(Gene.class);
//        
//        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//        Gene gene = (Gene) unmarshaller.unmarshal(new File("/marcelo/java/github/analiseGenoma/data/tmp/gene.xml"));
//        System.out.println("Gene: " + gene.toString());
//        for(GeneSymbolSynonym s : gene.getGeneSymbolSynonym()){
//            System.out.println("S" + s.getSymbol());
//        }

//        System.out.println("--local---");
//        Gene gene = new Gene();
//        gene.setName("GeneTeste");
//        gene.setSymbol("GT");
//        GeneSymbolSynonym gSS = new GeneSymbolSynonym("GT1");
//        gene.setGeneSymbolSynonym(new HashSet<>());
//        gene.getGeneSymbolSynonym().add(new GeneSymbolSynonym("GT1"));
//        gene.getGeneSymbolSynonym().add(new GeneSymbolSynonym("GT2"));
//
//       
//        Marshaller marshaller = jaxbContext.createMarshaller();
//        StringWriter writer = new StringWriter();
//        //FileWriter writer = new FileWriter("/marcelo/java/github/analiseGenoma/data/tmp/gene.xml");
//        
//        marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE ); 
//        
//        marshaller.marshal(gene, writer);
//        System.out.println("" + writer);
        
        System.out.println("end");


//        Gene gene = new Gene();
//        gene.setName("GeneTeste");
//        gene.setSymbol("GT");
//        GeneService geneService = new GeneService();
//        geneService.adicionar(gene);
//        System.out.println("Gene adicionado com sucesso");
    }
}
