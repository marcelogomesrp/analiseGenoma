/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.VcfMetadata;



public class VcfMetadataDao extends DAO<VcfMetadata> {

    public VcfMetadataDao() {
        super(VcfMetadata.class);
    }

    public List<VcfMetadata> findByVcf(Long id) {
        try {
            Query query = manager.createQuery("SELECT vmt FROM VcfMetadata vmt WHERE vmt.vcf.id = :id");
            query.setParameter("id", id);
            List<VcfMetadata> list = query.getResultList();
            return list;
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
            return null;
        }

    }
}