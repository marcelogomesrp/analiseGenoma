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
	PclinvarSignificance character varying,
	PclinvarDisease  character varying,
	PclinvarAccession character varying,
	PclinvarAlleleType character varying,
	PclinvarAlleleOrigin character varying,
	Psift character varying,
	PpolyphenHiv character varying,
	PpolyphenHvar character varying,
	PmutationTaster character varying,
	Plrt character varying, 
	PgerpRsScore  double precision,
	PgerpNeutralRate double precision,
	Pfeature character varying,
	Pensembl character varying, 
	PvertebrateGenomesConservationScore double precision,
	PinterproDomain  character varying,	
	PgenoType  character varying,	
	PvariantStatus character varying,
	
	PreadDepth character varying,	
	PalleleMutFraction double precision,
	PmeanBaseQuality double precision,
	PvarintType character varying,
	Pvalidate BOOLEAN,
		
	PdonorSpliceSite BOOLEAN,
	PacceptorSpliceSite BOOLEAN,
	Pmutation BOOLEAN,
	
	PeuropeanVarintFreq double precision, 
	PafricanVarintFreq double precision,
	PasianVarintFreq double precision,
	PamericanVarintFreq double precision, 
	PwholeVarintFreq double precision
	
	
	
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
		clinvarDiseaseId int;
		clinvarAccessionId int;
		clinvarAlleleTypeId int;
		clinvarAlleleOriginId int;
		siftId int;
		polyphenHivId int;
		polyphenHvarId int;
		mutationTasterId int;
		lrtId int;
		featureId int;
		ensemblId int;
		interproDomain int;
		variantStatus int;
		genoType int;
		interproDomainId int;
		variantStatusId int;
		genoTypeId int;
		
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
		PclinvarDisease := UPPER(PclinvarDisease);
		PclinvarAccession := UPPER(PclinvarAccession);
		PclinvarAlleleType := UPPER(PclinvarAlleleType);
		PclinvarAlleleOrigin := UPPER(PclinvarAlleleOrigin);
		Psift:= UPPER(Psift);
		PpolyphenHvar := UPPER(PpolyphenHvar);
		PmutationTaster := UPPER(PmutationTaster);
		Plrt := UPPER(Plrt);
		Pfeature := UPPER(Pfeature);
		Pensembl := UPPER(Pensembl);		
		PvariantStatus  := UPPER(PvariantStatus);
		PgenoType  := UPPER(PgenoType);
		PinterproDomain := UPPER(PinterproDomain);
		PvariantStatus := UPPER(PvariantStatus); 
		PgenoType := UPPER(PgenoType);
		
		PreadDepth := UPPER(PreadDepth);	
		PvarintType := UPPER(PvarintType);
		
		
		
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
		
		clinvarDiseaseId := (SELECT u.id_clinvardisease FROM clinvar_disease u WHERE u.name = PclinvarDisease );
		IF(clinvarDiseaseId IS NULL) THEN
			INSERT INTO clinvar_disease (name) VALUES (PclinvarDisease) RETURNING id_clinvardisease INTO clinvarDiseaseId;
		END IF;
		
		clinvarAccessionId := (SELECT u.id_clinvaraccession FROM clinvar_accession u WHERE u.name = PclinvarAccession );
		IF(clinvarAccessionId IS NULL) THEN
			INSERT INTO clinvar_accession (name) VALUES (PclinvarAccession) RETURNING id_clinvaraccession INTO clinvarAccessionId;
		END IF;
		
		clinvarAlleleTypeId := (SELECT u.id_clinvaralleletype FROM clinvar_allele_type u WHERE u.name = PclinvarAlleleType );
		IF(clinvarAlleleTypeId IS NULL) THEN
			INSERT INTO clinvar_allele_type (name) VALUES (PclinvarAlleleType) RETURNING id_clinvaralleletype INTO clinvarAlleleTypeId;
		END IF;
		
		clinvarAlleleOriginId := (SELECT u.id_clinvaralleleorigin FROM clinvar_allele_origin u WHERE u.name = PclinvarAlleleOrigin );
		IF(clinvarAlleleOriginId IS NULL) THEN
			INSERT INTO clinvar_allele_origin (name) VALUES (PclinvarAlleleOrigin) RETURNING id_clinvaralleleorigin INTO clinvarAlleleOriginId;
		END IF;
		
		siftId := (SELECT u.id_sift FROM sift u WHERE u.name = Psift );
		IF(siftId IS NULL) THEN
			INSERT INTO sift (name) VALUES (Psift) RETURNING id_sift INTO siftId;
		END IF;
		
		polyphenHivId := (SELECT u.id_polyphehdiv FROM polyphe_hdiv u WHERE u.name = PpolyphenHiv );
		IF(polyphenHivId IS NULL) THEN
			INSERT INTO polyphe_hdiv (name) VALUES (PpolyphenHiv) RETURNING id_polyphehdiv INTO polyphenHivId;
		END IF;
		
		polyphenHvarId := (SELECT u.id_polyphehvar FROM polyphe_hvar u WHERE u.name = PpolyphenHvar );
		IF(polyphenHvarId IS NULL) THEN
			INSERT INTO polyphe_hvar (name) VALUES (PpolyphenHvar) RETURNING id_polyphehvar INTO polyphenHvarId;
		END IF;
		
		mutationTasterId := (SELECT u.id_mutationtaster FROM mutation_taster u WHERE u.name = PmutationTaster );
		IF(mutationTasterId IS NULL) THEN
			INSERT INTO mutation_taster (name) VALUES (PmutationTaster) RETURNING id_mutationtaster INTO mutationTasterId;
		END IF;

		lrtId := (SELECT u.id_lrt FROM lrt u WHERE u.name = Plrt );
		IF(lrtId IS NULL) THEN
			INSERT INTO lrt (name) VALUES (Plrt) RETURNING id_lrt INTO lrtId;
		END IF;		
		
		featureId := (SELECT u.id_feature FROM feature u WHERE u.name = Pfeature );
		IF(featureId IS NULL) THEN
			INSERT INTO feature (name) VALUES (Pfeature) RETURNING id_feature INTO featureId;
		END IF;
		
		ensemblId := (SELECT u.id_ensembl FROM ensembl u WHERE u.idensembl = Pensembl );
		IF(ensemblId IS NULL) THEN
			INSERT INTO ensembl (idensembl) VALUES (Pensembl) RETURNING id_ensembl INTO ensemblId;
		END IF;
		
		interproDomainId := (SELECT u.id_interpro_domain FROM interpro_domain u WHERE u.name = PinterproDomain );
		IF(interproDomainId IS NULL) THEN
			INSERT INTO interpro_domain (name) VALUES (PinterproDomain) RETURNING id_interpro_domain INTO interproDomainId;
		END IF;

		variantStatusId := (SELECT u.id_variantstatus FROM variantStatus u WHERE u.name = PvariantStatus );
		IF(variantStatusId IS NULL) THEN
			INSERT INTO variantStatus (name) VALUES (PvariantStatus) RETURNING id_variantstatus INTO variantStatusId;
		END IF;
		
		genoTypeId := (SELECT u.id_genotype FROM genoType u WHERE u.name = PgenoType );
		IF(genoTypeId IS NULL) THEN
			INSERT INTO genoType (name) VALUES (PgenoType) RETURNING id_genotype INTO genoTypeId;
		END IF;		
		
		
	    SELECT nextval('variante_id_informacaovcf_seq') INTO variantId;
		INSERT INTO variante ( id_informacaovcf,allelicdeph1, allelicdeph2, gene_id, cromossomo_id, referencia, alterado, umdpredictor_id, zygosity_id, filter_id 
			,hgvs_c, hgvs_p, idSnp, exon_intron, type_id, effect_id, impacto_id, clinvarsignificance_id
			, clinvardisease_id, clinvaralleletype_id, clinvaralleleorigin_id, sift_id, polyphenhdiv_id, polyphenhvar_id
			, mutationTaster_id, lrt_id, gerpneutralrate, gerprsscore, feature_id, _id, vertebrategenomesconservationscore
			, interprodomain_id, variantStatus_id, genotype_id, readdepth, allelemutfraction, meanbasequality, varinttype
			, validate, donorSpliceSite, acceptorSpliceSite, mutation
			, europeanVarintFreq, africanVarintFreq, asianVarintFreq, americanVarintFreq, wholeVarintFreq) 
		VALUES (				variantId,      allelicdeph1, allelicdeph2, gene_id, cromossomo_id, Preferencia, Palterado, umdPredictorId, zygosityId, filterId
			,PhgvsC, PhgvsP, PidSnp, PexonIntron, typeId, effectId, impactoId, clinvarSignificanceId
			, clinvarDiseaseId, clinvarAlleleTypeId, clinvarAlleleOriginId, siftId, polyphenHivId, polyphenHvarId
			, mutationTasterId, lrtId, PgerpNeutralRate, PgerpRsScore, featureId, ensemblId, PvertebrateGenomesConservationScore
			, interproDomainId, variantStatusId, genoTypeId, PreadDepth, PalleleMutFraction, PmeanBaseQuality, PvarintType
			, Pvalidate, PdonorSpliceSite, PacceptorSpliceSite, Pmutation
			, PeuropeanVarintFreq, PafricanVarintFreq, PasianVarintFreq, PamericanVarintFreq, PwholeVarintFreq);  
		return variantId;
	END;     
$BODY$
LANGUAGE plpgsql VOLATILE
--SELECT u.id_clinvardisease FROM clinvar_disease u
--select insert_variant4(1,1,'ok','brca1','a','b');

--SELECT * FROM variante WHERE id_informacaovcf >= 1058455
