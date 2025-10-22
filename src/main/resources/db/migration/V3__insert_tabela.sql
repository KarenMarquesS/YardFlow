-- IoT
INSERT INTO dbo.tb_yf_IoT (serial, dtultimoacionamento, idmoto) VALUES
                                                                    ('SN-ABC10001', CAST('2025-01-10' AS DATE), 1),
                                                                    ('SN-ABC10002', CAST('2025-02-20' AS DATE), 2),
                                                                    ('SN-ABC10003', CAST('2025-03-05' AS DATE), 3),
                                                                    ('SN-ABC10004', CAST('2025-04-18' AS DATE), 4),
                                                                    ('SN-ABC10005', CAST('2025-05-25' AS DATE), 5),
                                                                    ('SN-ABC10006', CAST('2025-05-25' AS DATE), 6),
                                                                    ('SN-ABC10007', CAST('2025-05-25' AS DATE), 7),
                                                                    ('SN-ABC10008', CAST('2025-05-25' AS DATE), 8);
GO

-- Pátios
INSERT INTO dbo.tb_yf_patio (name, qtdvagas) VALUES
('Butantã', 100),
('Marília', 38),
('Nova Odessa', 204);
GO

-- Motos
INSERT INTO dbo.tb_yf_moto (modelo, chassi, placa, historico, idyf) VALUES
('MOTTU_SPORT', '2lcEz3b43U1ST0377', '2lcEz3b', 'trocar escapamento', 1),
('MOTTU_E', 'a8xD3u8oiu8s0sc90', 'a8xD3u8', 'trocar a bateria', 2),
('MOTTU_POP', '1CzR5PUlSwftC3834', '1CzR5PU', 'disponivel', 3),
('MOTTU_E', '1ALWx1M9FzlpK3671', '1ALWx1M', 'furtada', 4),
('MOTTU_SPORT', '5RlnaDEb3S7ee1853', '5RlnaD8', 'avaria na funilaria', 5),
('MOTTU_POP', '8CXgAkfuSfSdU4475', '8CX7gAk', 'trocar rodas', 6),
('MOTTU_E', '9tFFAzH53jRG96502', '9tFFAzH', 'trocar bateria', 7);
GO

-- Registros de check-in/out
INSERT INTO dbo.tb_yf_registro_check_in_out (entradapatio, saidapatio, periodo, setor, idmoto) VALUES
(CAST('2025-05-01' AS DATE), CAST('2025-05-08' AS DATE), 7,  'PENDENCIA', 1),
(CAST('2025-04-02' AS DATE), CAST('2025-05-04' AS DATE), 2,  'REPAROS_SIMPLES', 2),
(CAST('2025-05-02' AS DATE), CAST('2025-07-03' AS DATE), 59, 'MANUTENCAO', 3),
(CAST('2025-04-03' AS DATE), CAST('2025-04-28' AS DATE), 25, 'DEFEITO_MOTOR', 4),
(CAST('2025-05-03' AS DATE), CAST('2025-08-04' AS DATE), 91, 'DANOS_GRAVES', 5),
(CAST('2025-05-04' AS DATE), CAST('2025-06-04' AS DATE), 30, 'SEM_PLACA', 6),
(CAST('2025-05-04' AS DATE), CAST('2025-06-20' AS DATE), 46, 'DISPONIVEL_ALUGUEL', 7);
GO

-- Funções
INSERT INTO dbo.tb_yf_funcao (funcao) VALUES
('ADMIN'), ('GERENTE_PATIO'), ('RECEPCAO'), ('MECANICO'), ('EXPEDICAO');
GO

-- Usuários
INSERT INTO dbo.tb_yf_usuario (nome, email, senha, idfuncao) VALUES
('Ana Souza', 'ana.souza@example.com', '123456', 1),
('Carlos Oliveira', 'carlos.oliveira@example.com', '123456', 2),
('Fernanda Lima', 'fernanda.lima@example.com', '123456', 3),
('João Pereira', 'joao.pereira@example.com', '123456', 4),
('Mariana Santos', 'mariana.santos@example.com', '123456', 5);
GO
