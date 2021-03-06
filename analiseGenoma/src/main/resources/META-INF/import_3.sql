INSERT INTO usuario (nome,email,senha,telefone,administrador,gestor,revisor) VALUES ('admin','admin@admin.com','admin','111111',true,true,true);
INSERT INTO usuario (nome,email,senha,telefone,administrador,gestor,revisor) VALUES ('1','1','1','111111',true,true,true);
INSERT INTO usuario (nome,email,senha,telefone,administrador,gestor,revisor) VALUES ('admin1','admin1@admin.com','admin','111112',true,true,true);
INSERT INTO usuario (nome,email,senha,telefone,administrador,gestor,revisor) VALUES ('admin2','admin2@admin.com','admin','111113',true,true,true);
INSERT INTO usuario (nome,email,senha,telefone,administrador,gestor,revisor) VALUES ('admin3','admin3@admin.com','admin','1111114',true,true,true);
INSERT INTO usuario (nome,email,senha,telefone,administrador,gestor,revisor) VALUES ('wilsonjr','wilsonjr@usp.br','senha','111111',true,true,true);



INSERT INTO population (code,description) VALUES ("AFR", "African");
INSERT INTO population (code,description) VALUES ("AMR", "Ad Mixed American");
INSERT INTO population (code,description) VALUES ("EAS", "East Asian");
INSERT INTO population (code,description) VALUES ("EUR", "European");
INSERT INTO population (code,description) VALUES ("SAS", "South Asian");


INSERT INTO population (code,description,superpopulation_id) VALUES ("CHB","Han Chinese in Beijing, China",3);       
INSERT INTO population (code,description,superpopulation_id) VALUES ("JPT","Japanese in Tokyo, Japan",3);
INSERT INTO population (code,description,superpopulation_id) VALUES ("CHS","Southern Han Chinese",3);
INSERT INTO population (code,description,superpopulation_id) VALUES ("CDX","Chinese Dai in Xishuangbanna, China",3);
INSERT INTO population (code,description,superpopulation_id) VALUES ("KHV","Kinh in Ho Chi Minh City, Vietnam",3);
INSERT INTO population (code,description,superpopulation_id) VALUES ("CEU","Utah Residents (CEPH) with Northern and Western European Ancestry",4);
INSERT INTO population (code,description,superpopulation_id) VALUES ("TSI","Toscani in Italia",4);
INSERT INTO population (code,description,superpopulation_id) VALUES ("FIN","Finnish in Finland",4);
INSERT INTO population (code,description,superpopulation_id) VALUES ("GBR","British in England and Scotland",4);
INSERT INTO population (code,description,superpopulation_id) VALUES ("IBS","Iberian Population in Spain",4);
INSERT INTO population (code,description,superpopulation_id) VALUES ("YRI","Yoruba in Ibadan, Nigeria",1);
INSERT INTO population (code,description,superpopulation_id) VALUES ("LWK","Luhya in Webuye, Kenya",1);
INSERT INTO population (code,description,superpopulation_id) VALUES ("GWD","Gambian in Western Divisions in the Gambia",1);
INSERT INTO population (code,description,superpopulation_id) VALUES ("MSL","Mende in Sierra Leone",1);
INSERT INTO population (code,description,superpopulation_id) VALUES ("ESN","Esan in Nigeria",1);
INSERT INTO population (code,description,superpopulation_id) VALUES ("ASW","Americans of African Ancestry in SW USA",1);
INSERT INTO population (code,description,superpopulation_id) VALUES ("ACB","African Caribbeans in Barbados",1);
INSERT INTO population (code,description,superpopulation_id) VALUES ("MXL","Mexican Ancestry from Los Angeles USA",1);
INSERT INTO population (code,description,superpopulation_id) VALUES ("PUR","Puerto Ricans from Puerto Rico",2);
INSERT INTO population (code,description,superpopulation_id) VALUES ("CLM","Colombians from Medellin, Colombia",2);
INSERT INTO population (code,description,superpopulation_id) VALUES ("PEL","Peruvians from Lima, Peru",2);
INSERT INTO population (code,description,superpopulation_id) VALUES ("GIH","Gujarati Indian from Houston, Texas",5);
INSERT INTO population (code,description,superpopulation_id) VALUES ("PJL","Punjabi from Lahore, Pakistan",5);
INSERT INTO population (code,description,superpopulation_id) VALUES ("BEB","Bengali from Bangladesh",5);
INSERT INTO population (code,description,superpopulation_id) VALUES ("STU","Sri Lankan Tamil from the UK",5);
INSERT INTO population (code,description,superpopulation_id) VALUES ("ITU","Indian Telugu from the UK",5);





-- INSERT INTO etnia (sigla,nome,origem) VALUES('ASW','African Ancestry','SW USA');
-- INSERT INTO etnia (sigla,nome,origem) VALUES('CHD','Chinese','Metropolitan Denver, CO, USA');
-- INSERT INTO etnia (sigla,nome,origem) VALUES('GHI','Gujarati Indians','Texas, USA');
-- INSERT INTO etnia (sigla,nome,origem) VALUES('CHB','Chinese','Beijing China');
-- INSERT INTO etnia (sigla,nome,origem) VALUES('JPT','Japanese','Tokyo, Japan');
-- INSERT INTO etnia (sigla,nome,origem) VALUES('LWK','Luhya','Webuye Kenya');
-- INSERT INTO etnia (sigla,nome,origem) VALUES('MKK','Kassai','Kingyawa Kenya');
-- INSERT INTO etnia (sigla,nome,origem) VALUES('MXL','Mexican','Los Angeles, CA, USA');
-- INSERT INTO etnia (sigla,nome,origem) VALUES('TSI','Toscani','Italia');
-- INSERT INTO etnia (sigla,nome,origem) VALUES('YRI','Ibadan','Nigeria');
-- 
-- 
-- INSERT INTO paciente (nome,gender,etnia_id) VALUES ('1teste','m',1);
-- 
-- INSERT INTO banco_biologico (nome,site,descricao,urlInfo) VALUES ('OMIM','http://omim.org','bla bla bla','https://www.omim.org/entry/[UTIL]');
-- INSERT INTO banco_biologico (nome,site,descricao,urlInfo) VALUES ('ClinVar','https://www.ncbi.nlm.nih.gov/clinvar/','bla bla bla','https://www.ncbi.nlm.nih.gov/gene/[UTIL]');
-- 
-- 
-- 
-- INSERT INTO gene (name,symbol,synonymou_id) VALUES('gene1A','g1a',null);
-- INSERT INTO gene (name,symbol,synonymou_id) VALUES('gene2A','g2a',null);
-- INSERT INTO gene (name,symbol,synonymou_id) VALUES('gene3A','g3a',null);
-- INSERT INTO gene (name,symbol,synonymou_id) VALUES('gene1B','g1b',1);
-- INSERT INTO gene (name,symbol,synonymou_id) VALUES('gene2B','g2b',2);
-- INSERT INTO gene (name,symbol,synonymou_id) VALUES('gene1C','g1c',4);
-- 
-- INSERT INTO gene (name,symbol,synonymou_id) VALUES('gene teste D','DDX11L1',null);
-- INSERT INTO gene (name,symbol,synonymou_id) VALUES('gene teste W','WASH7P',null);
-- INSERT INTO gene (name,symbol,synonymou_id) VALUES('gene teste W','FAM41C',null);
-- INSERT INTO gene (name,symbol,synonymou_id) VALUES('gene teste W','TUBB8P11',null);
-- INSERT INTO gene (name,symbol,synonymou_id) VALUES('gene teste W','RP11-54O7.16',null);
-- 
-- 
-- INSERT INTO banco_biologico (nome,site,descricao,urlInfo) VALUES ('OMIM','http://omim.org','bla bla bla','https://www.omim.org/entry/[UTIL]');
-- INSERT INTO banco_biologico (nome,site,descricao,urlInfo) VALUES ('ClinVar','https://www.ncbi.nlm.nih.gov/clinvar/','bla bla bla','https://www.ncbi.nlm.nih.gov/gene/[UTIL]');
-- 
-- 
-- INSERT INTO patologia (nome,cid) VALUES ('gripe1','1111');
-- INSERT INTO patologia (nome,cid) VALUES ('gripe2','1112');
-- INSERT INTO patologia (nome,cid) VALUES ('gripe3','1113');
-- INSERT INTO patologia (nome,cid) VALUES ('cancer1','1121');
-- INSERT INTO patologia (nome,cid) VALUES ('cancer2','1122');
-- INSERT INTO patologia (nome,cid) VALUES ('cancer3','1123');
-- 
-- INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,1,1,'609300');
-- INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,1,2,'609300');
-- INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,1,3,'609300');
-- 
-- INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,2,1,'609301');
-- INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,2,2,'609302');
-- INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,2,3,'609303');
-- 
-- INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,3,1,'609401');
-- INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,3,2,'609402');
-- INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,3,3,'609403');
-- 
-- 
-- INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (2,1,1,'609300');
-- INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (2,2,2,'609300');
-- 
-- INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,7,1,'609301');
-- INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,8,1,'609302');
-- 
-- INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,9,1,'609302');
-- INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,10,1,'609302');
-- INSERT INTO informacao_biologica (bancobiologico_id,gene_id,patologia_id,util) VALUES (1,11,1,'609302');