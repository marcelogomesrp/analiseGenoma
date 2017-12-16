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


INSERT INTO paciente (nome,gender,etnia_id) VALUES ('teste','m',1);