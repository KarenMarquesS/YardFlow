-- Correção do relacionamento entre YardFlow e Moto
-- Remover coluna idmoto da tabela tb_yf_IoT (não deveria existir)
ALTER TABLE tb_yf_IoT DROP COLUMN IF EXISTS idmoto;

-- Garantir que a coluna yardflow_idyf existe na tabela tb_yf_moto
ALTER TABLE tb_yf_moto ADD COLUMN IF NOT EXISTS yardflow_idyf BIGINT;

-- Adicionar constraint de foreign key se não existir
ALTER TABLE tb_yf_moto DROP CONSTRAINT IF EXISTS fk_moto_yardflow;
ALTER TABLE tb_yf_moto ADD CONSTRAINT fk_moto_yardflow 
FOREIGN KEY (yardflow_idyf) REFERENCES tb_yf_IoT(idyf);
