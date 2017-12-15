package org.analiseGenoma.service;

import java.util.List;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.EffectDao;
import org.analiseGenoma.model.Effect;

public class EffectService extends Service<Effect> { 
    
    public EffectService() {
        super(Effect.class);
    }

    private EffectDao getDao() {
        return ((EffectDao) dao);
    }

    public List<Effect> findByName(String name) {
        return this.getDao().findByName(name);
    }

    @Transactional
    public Effect findOrCreate(String name) {
        List<Effect> list = this.findByName(name);
        if (list.size() == 1) {
            return list.get(0);
        }
        Effect obj = new Effect();
        obj.setName(name);
        this.adicionar(obj);
        return obj;
    }
}
