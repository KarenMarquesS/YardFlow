-- Remover coluna idmoto da tb_yf_IoT, se existir
IF COL_LENGTH('dbo.tb_yf_IoT', 'idmoto') IS NOT NULL
ALTER TABLE dbo.tb_yf_IoT DROP COLUMN idmoto;
GO

-- Adicionar coluna yardflow_idyf se não existir
IF COL_LENGTH('dbo.tb_yf_moto', 'yardflow_idyf') IS NULL
ALTER TABLE dbo.tb_yf_moto ADD yardflow_idyf BIGINT NULL;
GO

-- Adicionar constraint de foreign key (removendo anterior se existir)
IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'fk_moto_yardflow')
ALTER TABLE dbo.tb_yf_moto DROP CONSTRAINT fk_moto_yardflow;
GO

ALTER TABLE dbo.tb_yf_moto
    ADD CONSTRAINT fk_moto_yardflow FOREIGN KEY (yardflow_idyf) REFERENCES dbo.tb_yf_IoT(idyf);
GO

-- Atualizar os registros existentes
UPDATE dbo.tb_yf_moto SET yardflow_idyf = idyf WHERE idyf IS NOT NULL;
GO

-- Remover a coluna antiga idyf
IF COL_LENGTH('dbo.tb_yf_moto', 'idyf') IS NOT NULL
ALTER TABLE dbo.tb_yf_moto DROP COLUMN idyf;
GO
