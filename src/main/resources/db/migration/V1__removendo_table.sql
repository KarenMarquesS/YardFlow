-- Removendo tabelas (ordem respeita as dependências)
IF OBJECT_ID('dbo.tb_yf_registro_check_in_out', 'U') IS NOT NULL DROP TABLE dbo.tb_yf_registro_check_in_out;
IF OBJECT_ID('dbo.tb_yf_moto', 'U') IS NOT NULL DROP TABLE dbo.tb_yf_moto;
IF OBJECT_ID('dbo.tb_yf_IoT', 'U') IS NOT NULL DROP TABLE dbo.tb_yf_IoT;
IF OBJECT_ID('dbo.tb_yf_patio', 'U') IS NOT NULL DROP TABLE dbo.tb_yf_patio;
IF OBJECT_ID('dbo.tb_yf_usuario', 'U') IS NOT NULL DROP TABLE dbo.tb_yf_usuario;
IF OBJECT_ID('dbo.tb_yf_endereco', 'U') IS NOT NULL DROP TABLE dbo.tb_yf_endereco;
IF OBJECT_ID('dbo.tb_yf_funcao', 'U') IS NOT NULL DROP TABLE dbo.tb_yf_funcao;
GO
