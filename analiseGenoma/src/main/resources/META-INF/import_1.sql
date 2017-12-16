INSERT INTO usuario (nome,email,senha,telefone,administrador,gestor,revisor) VALUES ('admin','admin@admin.com','admin','111111',true,true,true);
INSERT INTO etnia (sigla,nome,origem) VALUES('ASW','African Ancestry','SW USA');
INSERT INTO etnia (sigla,nome,origem) VALUES('CHD','Chinese','Metropolitan Denver, CO, USA');
INSERT INTO etnia (sigla,nome,origem) VALUES('GHI','Gujarati Indians','Texas, USA');
INSERT INTO etnia (sigla,nome,origem) VALUES('CHB','Chinese','Beijing China');
INSERT INTO etnia (sigla,nome,origem) VALUES('JPT','Japanese','Tokyo, Japan');
INSERT INTO etnia (sigla,nome,origem) VALUES('LWK','Luhya','Webuye Kenya');
INSERT INTO etnia (sigla,nome,origem) VALUES('MKK','Kassai','Kingyawa Kenya');
INSERT INTO etnia (sigla,nome,origem) VALUES('MXL','Mexican','Los Angeles, CA, USA');
INSERT INTO etnia (sigla,nome,origem) VALUES('TSI','Toscani','Italia');
INSERT INTO etnia (sigla,nome,origem) VALUES('YRI','Ibadan','Nigeria');

INSERT INTO paciente (nome,dataNascimento,etnia_id,observacao) VALUES ('Jose da Silva','1982-11-27',1,'Paciente para teste');
INSERT INTO paciente (nome,dataNascimento,etnia_id,observacao) VALUES ('Maria da Silva','1990-01-8',1,'Paciente para teste');
INSERT INTO paciente (nome,dataNascimento,etnia_id,observacao) VALUES ('Mariana','2011-12-25',1,'Paciente para teste');
INSERT INTO paciente (nome,dataNascimento,etnia_id,observacao) VALUES ('1 da silva','2011-12-25',1,'Paciente para teste');


INSERT INTO impacto(name) VALUES ('MODIFIER');

INSERT INTO vcf (nome,idadeDoPaciente,dataImportacao,paciente_id) VALUES ('vcf de teste1',18,'2017-03-15',1);
INSERT INTO vcf (nome,idadeDoPaciente,dataImportacao,paciente_id) VALUES ('vcf de teste2',20,'2017-03-15',1);
INSERT INTO vcf (nome,idadeDoPaciente,dataImportacao,paciente_id) VALUES ('vcf de testeA',38,'2017-03-15',2);


INSERT INTO gene (nome,simbolo,novogene_id) VALUES('gene1A','g1a',null);
INSERT INTO gene (nome,simbolo,novogene_id) VALUES('gene2A','g2a',null);
INSERT INTO gene (nome,simbolo,novogene_id) VALUES('gene3A','g3a',null);
INSERT INTO gene (nome,simbolo,novogene_id) VALUES('gene1B','g1b',1);
INSERT INTO gene (nome,simbolo,novogene_id) VALUES('gene2B','g2b',2);
INSERT INTO gene (nome,simbolo,novogene_id) VALUES('gene1C','g1c',4);

INSERT INTO gene (nome,simbolo,novogene_id) VALUES('gene teste D','DDX11L1',null);
INSERT INTO gene (nome,simbolo,novogene_id) VALUES('gene teste W','WASH7P',null);
INSERT INTO gene (nome,simbolo,novogene_id) VALUES('gene teste W','FAM41C',null);
INSERT INTO gene (nome,simbolo,novogene_id) VALUES('gene teste W','TUBB8P11',null);
INSERT INTO gene (nome,simbolo,novogene_id) VALUES('gene teste W','RP11-54O7.16',null);




INSERT INTO banco_biologico (nome,site,descricao,urlInfo) VALUES ('OMIM','http://omim.org','bla bla bla','https://www.omim.org/entry/[UTIL]');
INSERT INTO banco_biologico (nome,site,descricao,urlInfo) VALUES ('ClinVar','https://www.ncbi.nlm.nih.gov/clinvar/','bla bla bla','https://www.ncbi.nlm.nih.gov/gene/[UTIL]');


INSERT INTO patologia (nome,cid) VALUES ('gripe1','1111');
INSERT INTO patologia (nome,cid) VALUES ('gripe2','1112');
INSERT INTO patologia (nome,cid) VALUES ('gripe3','1113');
INSERT INTO patologia (nome,cid) VALUES ('cancer1','1121');
INSERT INTO patologia (nome,cid) VALUES ('cancer2','1122');
INSERT INTO patologia (nome,cid) VALUES ('cancer3','1123');


INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,1,1,'609300');
INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,1,2,'609300');
INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,1,3,'609300');

INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,2,1,'609301');
INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,2,2,'609302');
INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,2,3,'609303');

INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,3,1,'609401');
INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,3,2,'609402');
INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,3,3,'609403');


INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (2,1,1,'609300');
INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (2,2,2,'609300');

INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,7,1,'609301');
INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,8,1,'609302');

INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,9,1,'609302');
INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,10,1,'609302');
INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,11,1,'609302');
/*
INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,12,1,'609302');
INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,13,1,'609302');
INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,14,1,'609302');
INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,15,1,'609302');
INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,16,1,'609302');
INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,17,1,'609302');
INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,18,1,'609302');
INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,19,1,'609302');
INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,20,1,'609302');

*/

INSERT INTO cromossomo (nome) VALUES ('1');
INSERT INTO cromossomo (nome) VALUES ('2');
INSERT INTO cromossomo (nome) VALUES ('3');
INSERT INTO cromossomo (nome) VALUES ('4');
INSERT INTO cromossomo (nome) VALUES ('5');

INSERT INTO filter (name) VALUES('f1');
INSERT INTO filter (name) VALUES('f2');
INSERT INTO filter (name) VALUES('f3');
INSERT INTO filter (name) VALUES('f4');
INSERT INTO filter (name) VALUES('f5');


INSERT INTO variante (alterado,cromossomo_id,filter_id,idSNP,impacto_id,position,qualidade,referencia,gene_id,vcf_id) VALUES ('T',1,1,'rs375086259',1,'14653',687.77,'C',1,1);
INSERT INTO variante (alterado,cromossomo_id,filter_id,idSNP,impacto_id,position,qualidade,referencia,gene_id,vcf_id) VALUES ('G',2,1,'rs375086259',1,'14653',687.77,'A',2,1);
INSERT INTO variante (alterado,cromossomo_id,filter_id,idSNP,impacto_id,position,qualidade,referencia,gene_id,vcf_id) VALUES ('C',3,2,'rs375086259',1,'14653',687.77,'T',3,1);
INSERT INTO variante (alterado,cromossomo_id,filter_id,idSNP,impacto_id,position,qualidade,referencia,gene_id,vcf_id) VALUES ('A',4,3,'rs375086259',1,'14653',687.77,'G',1,1);

--INSERT INTO variante (alterado,cromossomo_id,filtro,idSNP,impacto_id,position,qualidade,referencia,gene_id,vcf_id) VALUES ('T',1,'VQSRTrancheSNP99.00to99.90','rs375086259',1,'14653',687.77,'C',1,2);
--INSERT INTO variante (alterado,cromossomo_id,filtro,idSNP,impacto_id,position,qualidade,referencia,gene_id,vcf_id) VALUES ('G',2,'VQSRTrancheSNP99.00to99.90','rs375086259',1,'14653',687.77,'A',2,2);
--INSERT INTO variante (alterado,cromossomo_id,filtro,idSNP,impacto_id,position,qualidade,referencia,gene_id,vcf_id) VALUES ('C',3,'VQSRTrancheSNP99.00to99.90','rs375086259',1,'14653',687.77,'T',3,2);
--INSERT INTO variante (alterado,cromossomo_id,filtro,idSNP,impacto_id,position,qualidade,referencia,gene_id,vcf_id) VALUES ('A',4,'VQSRTrancheSNP99.00to99.90','rs375086259',1,'14653',687.77,'G',1,2);

--INSERT INTO variante (alterado,cromossomo_id,filtro,idSNP,impacto_id,position,qualidade,referencia,gene_id,vcf_id) VALUES ('T',1,'VQSRTrancheSNP99.00to99.90','rs375086259',1,'14653',687.77,'C',1,3);
--INSERT INTO variante (alterado,cromossomo_id,filtro,idSNP,impacto_id,position,qualidade,referencia,gene_id,vcf_id) VALUES ('G',2,'VQSRTrancheSNP99.00to99.90','rs375086259',1,'14653',687.77,'A',2,3);
--INSERT INTO variante (alterado,cromossomo_id,filtro,idSNP,impacto_id,position,qualidade,referencia,gene_id,vcf_id) VALUES ('C',3,'VQSRTrancheSNP99.00to99.90','rs375086259',1,'14653',687.77,'T',3,3);
--INSERT INTO variante (alterado,cromossomo_id,filtro,idSNP,impacto_id,position,qualidade,referencia,gene_id,vcf_id) VALUES ('A',4,'VQSRTrancheSNP99.00to99.90','rs375086259',1,'14653',687.77,'G',1,3);


INSERT INTO analise (estado,nome,observacao,paciente_id,patologia_id,vcf_id) VALUES ('criado','analise de exemplo 01','obs1',1,1,1);
