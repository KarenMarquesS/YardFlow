-- Tabela: tb_yf_funcao
CREATE TABLE dbo.tb_yf_funcao (
  idfuncao BIGINT IDENTITY(1,1) PRIMARY KEY,
  funcao VARCHAR(50) NOT NULL UNIQUE
);
GO

-- Tabela: tb_yf_usuario
CREATE TABLE dbo.tb_yf_usuario (
   id BIGINT IDENTITY(1,1) PRIMARY KEY,
   nome VARCHAR(255) NOT NULL,
   email VARCHAR(255) NOT NULL,
   senha VARCHAR(255) NOT NULL,
   idfuncao BIGINT NOT NULL,
   CONSTRAINT uq_usuario_email UNIQUE (email),
   CONSTRAINT fk_usuario_funcao FOREIGN KEY (idfuncao) REFERENCES dbo.tb_yf_funcao(idfuncao)
);
GO

-- Tabela: tb_yf_patio
CREATE TABLE dbo.tb_yf_patio (
idpatio BIGINT IDENTITY(1,1) PRIMARY KEY,
name VARCHAR(255),
qtdvagas BIGINT NOT NULL
);
GO

-- Tabela: tb_yf_moto
CREATE TABLE dbo.tb_yf_moto (
idmoto BIGINT IDENTITY(1,1) PRIMARY KEY,
modelo VARCHAR(50) NOT NULL,
chassi VARCHAR(100),
placa VARCHAR(20),
historico TEXT,
idyf BIGINT
);
GO

-- Tabela: tb_yf_IoT
CREATE TABLE dbo.tb_yf_IoT (
idyf BIGINT IDENTITY(1,1) PRIMARY KEY,
serial VARCHAR(100) NOT NULL UNIQUE,
dtultimoacionamento DATE,
idmoto BIGINT
);
GO

-- Tabela: tb_yf_registro_check_in_out
CREATE TABLE dbo.tb_yf_registro_check_in_out (
idregistro BIGINT IDENTITY(1,1) PRIMARY KEY,
entradapatio DATE,
saidapatio DATE,
periodo BIGINT NOT NULL,
setor VARCHAR(50) NOT NULL,
idmoto BIGINT,
CONSTRAINT fk_registro_moto FOREIGN KEY (idmoto) REFERENCES dbo.tb_yf_moto(idmoto)
);
GO
