CREATE OR REPLACE FUNCTION public.make_vcf_metadados(
	vcfId bigint
)
    RETURNS integer AS $BODY$
	DECLARE
		vcfmetadataId bigint;
		qtdVariantes bigint = 0;
		positonmax bigint = 0;
		positonmin bigint = 0;
		prevalencemax bigint = 0;
		prevalencemin bigint = 0;
		
		i  RECORD;
		cromossomo RECORD;
		umd RECORD;
		zygosity RECORD;
		
		
    BEGIN
		SELECT MAX(position), MIN(position) INTO positonmax, positonmin FROM variante where vcf_id = vcfId;
		SELECT COUNT(*) FROM variante where vcf_id = vcfId INTO qtdVariantes;
				
	    SELECT nextval('vcf_metadata_id_vcfmetadata_seq') INTO vcfmetadataId;
		INSERT INTO vcf_metadata (id_vcfmetadata, positonmax, positonmin, prevalencemax, prevalencemin, qtdVariante, vcf_id) 
		VALUES (vcfmetadataId, positonmax, positonmin, prevalencemax, prevalencemin, qtdVariantes,vcfId);  
		
		
		FOR i IN SELECT DISTINCT(gene_id) AS idGene FROM variante where vcf_id = vcfId    
		LOOP
			INSERT INTO vcf_metadata_gene (vcfmetadata_id_vcfmetadata, genes_id_gene) VALUES (vcfmetadataId,i.idGene);				
		END LOOP;
		
		FOR cromossomo IN SELECT DISTINCT(cromossomo_id) AS id FROM variante where vcf_id = vcfId    
		LOOP
			INSERT INTO vcf_metadata_cromossomo (vcfmetadata_id_vcfmetadata, cromossomos_id_cromossomo) VALUES (vcfmetadataId,cromossomo.id);				
		END LOOP;
		
		FOR umd IN SELECT DISTINCT(umdpredictor_id) AS id FROM variante where vcf_id = vcfId    
		LOOP
			INSERT INTO vcf_metadata_umd_predictor (vcfmetadata_id_vcfmetadata, umdpredictors_id_umdpredictor) VALUES (vcfmetadataId,umd.id);				
		END LOOP;
		
		FOR zygosity IN SELECT DISTINCT(zygosity_id) AS id FROM variante where vcf_id = vcfId    
		LOOP
			INSERT INTO vcf_metadata_zygosity (vcfmetadata_id_vcfmetadata, zygosities_id_zygosity) VALUES (vcfmetadataId,zygosity.id);				
		END LOOP;
		
		return vcfmetadataId;
	END;     
$BODY$
LANGUAGE plpgsql VOLATILE;

SELECT make_vcf_metadados(136);

