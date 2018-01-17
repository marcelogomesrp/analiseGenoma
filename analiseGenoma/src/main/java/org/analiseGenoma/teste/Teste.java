package org.analiseGenoma.teste;

import org.analiseGenoma.model.Gene;
import org.analiseGenoma.service.GeneService;

public class Teste {
    public static void main(String[] args) {
        Gene gene = new Gene();
        gene.setName("GeneTeste");
        gene.setSymbol("GT");
        GeneService geneService = new GeneService();
        geneService.adicionar(gene);
        System.out.println("Gene adicionado com sucesso");
    }
}
