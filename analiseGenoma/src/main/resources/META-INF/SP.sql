CREATE OR REPLACE FUNCTION public.insert_variant4(
	allelicdeph1 integer,
	allelicdeph2 integer,	
	geneSymbol character varying,
	Pcromossomo character varying,
	Preferencia character varying,
	Palterado character varying,
	PumdPredictor character varying, 
	Pzygosity character varying,
	Pfilter character varying,	
	PhgvsC character varying,
	PhgvsP character varying,
	PidSnp character varying,
	PexonIntron integer,
	Ptype character varying,	
	Peffect character varying,
	Pimpacto character varying,
	PclinvarSignificance character varying
	
)
    RETURNS integer AS $BODY$
	DECLARE
		variantId int;
		gene_id int;
		cromossomo_id int;
		umdPredictorId int;
		zygosityId int;
		filterId int;
		typeId int;
		effectId int;
		impactoId int;
		clinvarSignificanceId int;
		
		
    BEGIN
		geneSymbol := UPPER(geneSymbol);
		Pcromossomo := UPPER(Pcromossomo);
		Preferencia := UPPER(Preferencia);
		Palterado := UPPER(Palterado);
		Pzygosity := UPPER(Pzygosity);
		Pfilter := UPPER(Pfilter);
		Ptype := UPPER(Ptype);
		Peffect := UPPER(Peffect);
		Pimpacto := UPPER(Pimpacto);
		PclinvarSignificance := UPPER(PclinvarSignificance);
		
		cromossomo_id = (SELECT id_cromossomo FROM cromossomo c WHERE c.nome = Pcromossomo);
		IF(cromossomo_id IS NULL) THEN
			INSERT INTO cromossomo (nome) VALUES (Pcromossomo) RETURNING id_cromossomo INTO cromossomo_id;
		END IF;
				
		gene_id := (SELECT gene_id_gene FROM gene_symbol WHERE symbol = geneSymbol);		
		if(gene_id IS NULL) then
			INSERT INTO gene (symbol) VALUES (geneSymbol) RETURNING id_gene INTO gene_id;
			INSERT INTO gene_symbol (symbol, gene_id_gene) VALUES (geneSymbol,gene_id);
			INSERT INTO gene_dbbio (dbbio_id_dbbio,gene_id_gene) VALUES (1,gene_id);
 		end if;
		
		umdPredictorId := (SELECT u.id_umdpredictor FROM umd_predictor u WHERE u.name = PumdPredictor );
		IF(umdPredictorId IS NULL) THEN
			INSERT INTO umd_predictor (name) VALUES (PumdPredictor) RETURNING id_umdpredictor INTO umdPredictorId;
		END IF;
		
		zygosityId := (SELECT u.id_zygosity FROM zygosity u WHERE u.name = Pzygosity );
		IF(zygosityId IS NULL) THEN
			INSERT INTO zygosity (name) VALUES (Pzygosity) RETURNING id_zygosity INTO zygosityId;
		END IF;
		
		filterId := (SELECT u.id_filter FROM filter u WHERE u.name = Pfilter );
		IF(filterId IS NULL) THEN
			INSERT INTO filter (name) VALUES (Pfilter) RETURNING id_filter INTO filterId;
		END IF;
		
		typeId := (SELECT u.id_type FROM type u WHERE u.name = Ptype );
		IF(typeId IS NULL) THEN
			INSERT INTO type (name) VALUES (Ptype) RETURNING id_type INTO typeId;
		END IF;

		effectId := (SELECT u.id_effect FROM effect u WHERE u.name = Peffect );
		IF(effectId IS NULL) THEN
			INSERT INTO effect (name) VALUES (Peffect) RETURNING id_effect INTO effectId;
		END IF;
		
		impactoId := (SELECT u.id_impacto FROM impacto u WHERE u.name = Pimpacto );
		IF(impactoId IS NULL) THEN
			INSERT INTO impacto (name) VALUES (Pimpacto) RETURNING id_impacto INTO impactoId;
		END IF;
		
		clinvarSignificanceId := (SELECT u.id_clinvarsignificance FROM clinvar_significance u WHERE u.name = PclinvarSignificance );
		IF(clinvarSignificanceId IS NULL) THEN
			INSERT INTO clinvar_significance (name) VALUES (PclinvarSignificance) RETURNING id_clinvarsignificance INTO clinvarSignificanceId;
		END IF;
		
		
	    SELECT nextval('variante_id_informacaovcf_seq') INTO variantId;
		INSERT INTO variante ( id_informacaovcf,allelicdeph1, allelicdeph2, gene_id, cromossomo_id, referencia, alterado, umdpredictor_id, zygosity_id, filter_id 
			,hgvs_c, hgvs_p, idSnp, exon_intron, type_id, effect_id, impacto_id, clinvarsignificance_id ) 
		VALUES (				variantId,      allelicdeph1, allelicdeph2, gene_id, cromossomo_id, Preferencia, Palterado, umdPredictorId, zygosityId, filterId
			,PhgvsC, PhgvsP, PidSnp, PexonIntron, typeId, effectId, impactoId, clinvarSignificanceId);  
		return variantId;
	END;     
$BODY$
LANGUAGE plpgsql VOLATILE

--select insert_variant4(1,1,'ok','brca1','a','b');

--SELECT * FROM variante WHERE id_informacaovcf >= 1058455
