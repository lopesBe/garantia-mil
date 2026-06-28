-- =============================================
-- NOTA: Usuários são criados via DataInitializer.java
-- com senhas BCrypt geradas em runtime.
-- Login: admin / admin123
-- =============================================

-- =============================================
-- SEED: Reclamações de exemplo
-- =============================================
INSERT INTO reclamacoes (numero, cnpj_cliente, nome_cliente, cidade, estado, numero_nota, data_nota, data_ocorrencia, valor, quantidade, situacao, descricao_defeito, itens_envolvidos, observacoes, responsavel)
VALUES
('REC-0001', '12.345.678/0001-90', 'Construtora Alpha Ltda', 'São Paulo', 'SP', 'NF-001234', '2024-01-10', '2024-01-15', 15000.00, 50, 'Aberta', 'Produto apresentou trinca após instalação em ambiente externo.', 'Parafusos M8 x 40mm (lote 2024-A)', 'Cliente solicitou laudo técnico.', 'Carlos Silva'),
('REC-0002', '98.765.432/0001-11', 'Indústria Beta S.A.', 'Curitiba', 'PR', 'NF-005678', '2024-02-05', '2024-02-12', 8500.50, 20, 'Em Análise', 'Peças com dimensional fora do especificado.', 'Engrenagem cônica Z40', 'Amostra retida para análise laboratorial.', 'Ana Oliveira'),
('REC-0003', '55.444.333/0001-22', 'Mecânica Gama ME', 'Porto Alegre', 'RS', 'NF-009999', '2024-03-01', '2024-03-08', 3200.00, 10, 'Concluída', 'Falha de revestimento superficial em ambiente com umidade.', 'Chapa galvanizada 2mm (cód. CH-200)', 'Concluída com envio de reposição.', 'Roberto Souza');

-- =============================================
-- SEED: Garantias de exemplo
-- =============================================
INSERT INTO garantias (numero, numero_nota, cnpj_cliente, nome_cliente, data_solicitacao, reclamacao_id, status, observacoes_analise, conclusao, responsavel_decisao, data_decisao)
VALUES
('GAR-0001', 'NF-001234', '12.345.678/0001-90', 'Construtora Alpha Ltda', '2024-01-20', 1, 'Pendente', 'Aguardando análise do setor de qualidade.', NULL, NULL, NULL),
('GAR-0002', 'NF-005678', '98.765.432/0001-11', 'Indústria Beta S.A.', '2024-02-15', 2, 'Aprovada', 'Análise confirmou defeito de fabricação.', 'Aprovada. Reposição autorizada em até 15 dias úteis.', 'Ana Oliveira', '2024-02-20'),
('GAR-0003', 'NF-009999', '55.444.333/0001-22', 'Mecânica Gama ME', '2024-03-10', 3, 'Reprovada', 'Defeito causado por armazenamento inadequado pelo cliente.', 'Reprovada. Defeito não coberto pela garantia.', 'Carlos Silva', '2024-03-15');

-- =============================================
-- SEED: Itens das Garantias
-- =============================================
INSERT INTO garantia_itens (garantia_id, codigo, descricao, quantidade, unidade)
VALUES
(1, 'PAR-M8-40', 'Parafuso M8 x 40mm', 50, 'UN'),
(2, 'ENG-CON-Z40', 'Engrenagem Cônica Z40', 20, 'UN'),
(3, 'CH-200-GAL', 'Chapa Galvanizada 2mm', 10, 'M2');

-- =============================================
-- SEED: Análises de Qualidade de exemplo
-- =============================================
INSERT INTO analises_qualidade (numero, reclamacao_id, garantia_id, data_analise, responsavel_tecnico, codigo_produto, descricao_produto, lote_serie, data_fabricacao, evidencias, causa_raiz, conclusao_tecnica, acao_corretiva, status)
VALUES
('QLD-0001', 2, 2, '2024-02-16', 'Eng. Fernanda Costa', 'ENG-CON-Z40', 'Engrenagem Cônica Z40', 'LOTE-2024-B', '2024-01-05',
 'Dimensional fora do tolerado em 3 amostras analisadas. Desvio de +0,2mm no diâmetro primitivo.',
 'Desgaste prematuro da ferramenta de usinagem na linha de produção.',
 'Defeito de fabricação confirmado. Produto não conformidade com especificação técnica.',
 'Substituição da ferramenta de corte a cada 500 ciclos. Aumento da frequência de inspeção dimensional.',
 'Concluída'),
('QLD-0002', 1, 1, '2024-01-22', 'Eng. Marcos Pereira', 'PAR-M8-40', 'Parafuso M8 x 40mm', 'LOTE-2024-A', '2023-12-10',
 'Trinca observada na cabeça do parafuso em 5 das 10 amostras analisadas.',
 'Fragilização por hidrogênio durante processo de eletrodeposição.',
 'Em análise. Aguardando resultado de ensaio de tração.',
 NULL,
 'Em Andamento');
