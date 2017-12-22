package org.analiseGenoma.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.dao.VarianteDao;
import org.analiseGenoma.dao.VcfMetadataDao;
import org.analiseGenoma.model.Cromossomo;
import org.analiseGenoma.model.Effect;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.model.Sift;
import org.analiseGenoma.model.UmdPredictor;
import org.analiseGenoma.model.Variante;
import org.analiseGenoma.model.Vcf;
import org.analiseGenoma.model.VcfMetadata;


@Named
public class VcfMetadataService extends Service<VcfMetadata>{
    private Vcf vcf;
    private List<Variante> variantes;
    @Inject private VarianteDao varianteDao;
    
    public VcfMetadataService() {
        super(VcfMetadata.class);
    }
    
    private VcfMetadataDao getDao(){
        return ((VcfMetadataDao) dao);
    }

    public void setVcf(Vcf vcf) {
        this.vcf = vcf;
        variantes = varianteDao.findByVCF(vcf.getId());
    }
    
    
    //aqui vou criar as estatisticas 
    public VcfMetadata makeMetadata(Vcf vcf){
        this.setVcf(vcf);
        VcfMetadata vmd = new VcfMetadata();
        vmd.setVcf(vcf);
        vmd.setQtdVariante(this.getQtdVariante());
        vmd.setCromossomos(this.getCromossomos());
        vmd.setPositonMax(this.getPostionMax());
        vmd.setPositonMin(this.getPostionMin());
        vmd.setReferencias(this.getReferencias());
        vmd.setAlterado((this.getAlterados()));
        vmd.setGenes(this.getGenes());
        vmd.setUmdPredictors(this.getUmdPredictors());
        vmd.setEffects(this.getEffect());
        vmd.setSifts(this.getSift());
        vmd.setMapGene(this.getMapGene());
        
        
        return vmd;
    }

    public int getQtdVariante() {
        return variantes.size();
    }
    public Set<Cromossomo> getCromossomos(){
        return variantes.stream()
                .map(v -> v.getCromossomo())
                .distinct()
                .collect(Collectors.toSet());
    }
    
    public Set<String> getReferencias(){
        return variantes.stream()
                .map(v -> v.getReferencia())
                .distinct()
                .collect(Collectors.toSet());
    }
    
    public Set<String> getAlterados(){
        return variantes.stream()
                .map(v -> v.getAlterado())
                .distinct()
                .collect(Collectors.toSet());
    }
    
    public Long getPostionMax(){
        return variantes.stream()
                .max((v1,v2) -> Long.compare(v1.getPosition(), v2.getPosition()))
                .get()
                .getPosition();        
    }

    public Long getPostionMin(){
        return variantes.stream()
                .min((v1,v2) -> Long.compare(v1.getPosition(), v2.getPosition()))
                .get()
                .getPosition();        
    }

    public Set<Gene> getGenes() {
        return variantes.stream()
                .map(v -> v.getGene())
                .distinct()
                .collect(Collectors.toSet());
    }
    
    public Set<UmdPredictor> getUmdPredictors(){
        return variantes.stream()
                .map(v -> v.getUmdPredictor())
                .distinct()
                .collect(Collectors.toSet());
    }

    public Set<Effect> getEffect(){
        return variantes.stream()
                .map(v -> v.getEffect())
                .distinct()
                .collect(Collectors.toSet());
    }    
    public Set<Sift> getSift(){
        return variantes.stream()
                .map(v -> v.getSift())
                .distinct()
                .collect(Collectors.toSet());
    }

    public VcfMetadata findByVcfId(Long id) {
        List<VcfMetadata> list = getDao().findByVcf(id);
        if(list.size() == 1)
            return list.get(0);
        return null;
    }

    private Map<Gene, Integer> getMapGene() {
        HashMap<Gene, Integer> map = new HashMap<Gene,Integer>();
        for(Variante v: variantes){
            Integer total = map.get(v.getGene());
            if(total == null){
                total = 0;
            }
            total++;
            map.put(v.getGene(), total);
        }
        return map;
            
    }


    
    
    
    
}
