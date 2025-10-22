-- Tabela: tb_yf_funcao
CREATE TABLE tb_yf_funcao (
  idfuncao BIGINT AUTO_INCREMENT PRIMARY KEY,
  funcao VARCHAR(50) NOT NULL UNIQUE
);

-- Tabela: tb_yf_usuario
CREATE TABLE tb_yf_usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL ,
    email VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL ,
    idfuncao BIGINT NOT NULL,
    CONSTRAINT uq_usuario_email UNIQUE (email),
    CONSTRAINT fk_usuario_funcao FOREIGN KEY (idfuncao) REFERENCES tb_yf_funcao(idfuncao)
);

-- Tabela: tb_yf_patio
CREATE TABLE tb_yf_patio (
     idpatio BIGINT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(255),
     qtdvagas BIGINT NOT NULL
);

-- Tabela: tb_yf_moto (sem FK por enquanto)
CREATE TABLE tb_yf_moto (
    idmoto BIGINT AUTO_INCREMENT PRIMARY KEY,
    modelo VARCHAR(50) NOT NULL,
    chassi VARCHAR(100),
    placa VARCHAR(20),
    historico TEXT,
    idyf BIGINT
);

-- Tabela: tb_yf_IoT (sem FK por enquanto)
CREATE TABLE tb_yf_IoT (
   idyf BIGINT AUTO_INCREMENT PRIMARY KEY,
   serial VARCHAR(100) NOT NULL UNIQUE,
   dtultimoacionamento DATE,
   idmoto BIGINT
);

-- Tabela: tb_yf_registro_check_in_out
CREATE TABLE tb_yf_registro_check_in_out (
     idregistro BIGINT AUTO_INCREMENT PRIMARY KEY,
     entradapatio DATE,
     saidapatio DATE,
     periodo BIGINT NOT NULL,
     setor VARCHAR(50) NOT NULL,
     idmoto BIGINT,
     CONSTRAINT fk_registro_moto FOREIGN KEY (idmoto) REFERENCES tb_yf_moto(idmoto)
);

