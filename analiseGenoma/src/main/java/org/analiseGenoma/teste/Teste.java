package org.analiseGenoma.teste;

import org.analiseGenoma.model.Gene;
import org.analiseGenoma.service.GeneService;

public class Teste {
    public static void main(String[] args) {
        Gene gene = new Gene();
        gene.setNome("GeneTeste");
        gene.setSimbolo("GT");
        GeneService geneService = new GeneService();
        geneService.adicionar(gene);
        System.out.println("Gene adicionado com sucesso");
    }
}
