package org.analiseGenoma.service;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.EtniaDao;
import org.analiseGenoma.model.Etnia;

@Named
public class EtniaService implements Serializable {

    @Inject
    private EtniaDao etniaDao;

    @PostConstruct
    public void init() {
    }

    @Transactional
    public void adicionar(Etnia etnia) {
        etniaDao.adicionar(etnia);
    }

    @Transactional
    public void atualizar(Etnia etnia) {
        etniaDao.atualizar(etnia);
    }

    public void adicionar(List<Etnia> list) {
        list.forEach((e) -> {
            adicionar(e);
        });
    }

    public List<Etnia> buscar() {
        return etniaDao.buscar();
    }

    public Etnia buscarPorId(Long id) {
        return etniaDao.buscarPorId(id);
    }

    @Transactional
    public void upload(byte[] contents) {
        if (contents.length > 0) {
            Runnable r = () -> {
                this.importar(contents);
            };
            Thread t = new Thread(r);
            t.start();
        }
    }

    public List<Etnia> findByExample(Etnia etnia) {
        return etniaDao.findByExample(etnia);
    }

    public List<String> findNamesByName(String name) {
        Etnia etnia = new Etnia();
        etnia.setNome(name + "%");
        List<String> retorno = etniaDao.findByExample(etnia)
                .stream()
                .map(e -> e.getNome())
                .distinct()
                .collect(Collectors.toList());
        return retorno;
    }

    public List<String> findSiglasBySigla(String sigla) {
        Etnia etnia = new Etnia();
        etnia.setSigla(sigla + "%");
        List<String> retorno = etniaDao.findByExample(etnia)
                .stream()
                .map(e -> e.getSigla())
                .distinct()
                .collect(Collectors.toList());
        return retorno;
    }

    public List<String> findOrigensByOrigem(String origem) {
        Etnia etnia = new Etnia();
        etnia.setSigla(origem + "%");
        List<String> retorno = etniaDao.findByExample(etnia)
                .stream()
                .map(e -> e.getOrigem())
                .distinct()
                .collect(Collectors.toList());
        return retorno;
    }

    private void importar(byte[] contents) {
        String[] arquivo = new String(contents, StandardCharsets.UTF_8).split("\n");
        List<Etnia> lista = new ArrayList<>();
        for (String ln : arquivo) {
            String[] linha = ln.split("\t");
            Etnia e = new Etnia();
            e.setSigla(linha[0]);
            e.setNome(linha[1]);
            e.setOrigem(linha[2]);
            this.adicionar(e);
        }
        this.adicionar(lista);
    }

}
