package org.analiseGenoma.service;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Named;
import org.analiseGenoma.dao.VariantSelectedDao;
import org.analiseGenoma.model.Analise;
import org.analiseGenoma.model.VariantSelected;
import org.analiseGenoma.model.Variante;

@Named
public class VariantSelectedService extends Service<VariantSelected> implements Serializable{

    public VariantSelectedService() {
        super(VariantSelected.class);
    }

    private VariantSelectedDao getDao() {
        return ((VariantSelectedDao) dao);
    }

    public List<Variante> findByAnalise(Analise analise) {
//         return getDao().findByProperty("analise", analise)
//                .stream()
//                .map(vs -> vs.getVariantes())
//                .flatMap(List::stream)
//                .collect(Collectors.toList());
        return getDao().findVariantByAnalise(analise);
    }
    


}