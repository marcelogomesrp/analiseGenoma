package org.analiseGenoma.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.DAO;
import org.analiseGenoma.dao.PopulationDao;
import org.analiseGenoma.model.Population;
import org.analiseGenoma.service.util.CSVReader;
import org.analiseGenoma.service.util.Line;

@Named
public class PopulationService extends Service<Population> implements Serializable {

    public PopulationService() {
        super(Population.class);
    }

    private PopulationDao getDao() {
        return ((PopulationDao) dao);
    }

    @PostConstruct
    public void init() {
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

    public List<Population> findByLikeCode(String code) {
//        Population p = new Population();
//        p.setCode(code + "%");
        return getDao().findByProperty("code", code, DAO.MatchMode.START);
    }

    public Population findByCode(String code) {
        List<Population> list = getDao().findByProperty("code", code);
        if (list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

//    public List<String> findByCode(String code) {
//        Population p = new Population();
//        p.setCode(code + "%");
//        List<String> populations = new ArrayList<>();
//        try {
//            populations = getDao().findByExample(p)
//                    .stream()
//                    .map(e -> e.getCode())
//                    .distinct()
//                    .collect(Collectors.toList());
//        } catch (Exception ex) {
//            Logger.getLogger(PopulationService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return populations;
//    }
//    public List<String> findNamesByName(String name) {
//        Population etnia = new Population();
//        etnia.setNome(name + "%");
//        List<String> retorno = populationDao.findByExample(etnia)
//                .stream()
//                .map(e -> e.getNome())
//                .distinct()
//                .collect(Collectors.toList());
//        return retorno;
//    }
//    public List<String> findSiglasBySigla(String sigla) {
//        Population etnia = new Population();
//        etnia.setSigla(sigla + "%");
//        List<String> retorno = populationDao.findByExample(etnia)
//                .stream()
//                .map(e -> e.getSigla())
//                .distinct()
//                .collect(Collectors.toList());
//        return retorno;
//    }
//
//    public List<String> findOrigensByOrigem(String origem) {
//        Population etnia = new Population();
//        etnia.setSigla(origem + "%");
//        List<String> retorno = populationDao.findByExample(etnia)
//                .stream()
//                .map(e -> e.getOrigem())
//                .distinct()
//                .collect(Collectors.toList());
//        return retorno;
//    }
//
    private void importar(byte[] contents) {
        //http://www.internationalgenome.org/category/population/
        CSVReader csv = new CSVReader(contents);
        //List<Population> list = new ArrayList<>();
        for (Line ln : csv.getFile()) {
            if (ln.getSize() >= 1) {
                Population p = new Population();                
                p.setCode(ln.getField(0));
                if(ln.getSize() >= 2)
                    p.setDescription(ln.getField(1));
                if(ln.getSize() >= 3)
                    p.setSuperPopulation(this.findOrCreateByCode(ln.getField(2)));
                //list.add(p);
                this.persiste(p);
            }
        }
        //this.persiste(list);

//        String[] arquivo = new String(contents, StandardCharsets.UTF_8).split("\n");
//        List<Population> lista = new ArrayList<>();
//        for (String ln : arquivo) {
//            String[] linha = ln.split("\t");
//            Population e = new Population();
//            e.setSigla(linha[0]);
//            e.setNome(linha[1]);
//            e.setOrigem(linha[2]);
//            this.persiste(e);
//        }
//        this.adicionar(lista);
    }

    private Population findOrCreateByCode(String code) {
//        List<Population> list = getDao().findByCode(code);
//        if(list.size() == 1){
        Population p = getDao().findByCode(code);
        if (p == null) {
            p = new Population();
            p.setCode(code);
            this.persiste(p);
        }
        return p;
    }

}
