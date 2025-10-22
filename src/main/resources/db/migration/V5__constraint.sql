-- Ajustar relacionamentos entre Moto e Yardflow


-- Atualizar os registros existentes para fazer a ligação correta
UPDATE tb_yf_moto SET yardflow_idyf = idyf WHERE idyf IS NOT NULL;

-- Remover a coluna idyf antiga
ALTER TABLE tb_yf_moto DROP COLUMN idyf;



