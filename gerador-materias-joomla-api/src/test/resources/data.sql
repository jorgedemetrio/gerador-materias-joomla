-- Insert test data for H2 database

-- Insert test categories
INSERT INTO tbl_categoria (id, id_joomla, titulo, alias, usar_prompts, id_pai) VALUES 
(1, 100, 'Categoria Teste 1', 'categoria-teste-1', true, null),
(2, 101, 'Categoria Teste 2', 'categoria-teste-2', true, null),
(3, 102, 'Subcategoria Teste', 'subcategoria-teste', true, 1);

-- Insert test tags
INSERT INTO tbl_tag (id, uuid_requisicao, id_joomla, titulo, alias, idioma) VALUES 
(1, 'test-uuid-1', 200, 'Tag Teste 1', 'tag-teste-1', 'pt-BR'),
(2, 'test-uuid-2', 201, 'Tag Teste 2', 'tag-teste-2', 'pt-BR'),
(3, 'test-uuid-3', 202, 'Tag Teste 3', 'tag-teste-3', 'pt-BR');

-- Insert test mapa perguntas
INSERT INTO tbl_mapa_perguntas (id, uuid_requisicao, pergunta, ordem_relevancia, motivo_sugestao, perfil_enquadra, stream, id_categoria, data_sugestao_publicacao) VALUES 
(1, 'test-uuid-mapa-1', 'Como fazer meditação para iniciantes?', 1, 'Pergunta muito procurada', 'Iniciantes em meditação', null, 1, '2024-01-15'),
(2, 'test-uuid-mapa-2', 'Quais são os benefícios do Reiki?', 2, 'Tema popular', 'Praticantes de terapias alternativas', null, 2, '2024-01-20');

-- Insert test termos mapa pergunta
INSERT INTO tbl_mapaperguntas_termo (id, uuid_requisicao, termo, id_mapa_pergunta) VALUES 
(1, 'test-uuid-termo-1', 'meditação', 1),
(2, 'test-uuid-termo-2', 'iniciantes', 1),
(3, 'test-uuid-termo-3', 'reiki', 2),
(4, 'test-uuid-termo-4', 'benefícios', 2);

-- Insert test sub mapa perguntas
INSERT INTO tbl_mapa_subperguntas (id, uuid_requisicao, pergunta, id_mapa_pergunta) VALUES 
(1, 'test-uuid-sub-1', 'Quanto tempo devo meditar por dia?', 1),
(2, 'test-uuid-sub-2', 'Qual a melhor posição para meditar?', 1),
(3, 'test-uuid-sub-3', 'Como funciona uma sessão de Reiki?', 2);

-- Insert test carga massa
INSERT INTO tbl_carga_promover (id, uuid_requisicao, status, data_inicio_processo, data_fim_processo, data_inicio_carga, data_fim_carga, nota, requisicao) VALUES 
(1, 'test-uuid-carga-1', 'PROCESSAR', '2024-01-01 00:00:00', '2024-01-31 23:59:59', '2024-01-01 10:00:00', '2024-01-01 12:00:00', 'Carga de teste', '{"tema": "teste"}');

-- Insert test materia
INSERT INTO tbl_materia (id, id_joomla, tema_proposto, uuid_requisicao, titulo_selecionado, titulo_1, titulo_2, titulo_3, meta_descircao, keywords, introducao, materia, stream, alias, post_instagram, data_publicar, roteiro, cricao_na_plataforma, cricao_banco, exportado_para_plataforma, id_categoria, id_mapa_pergunta, status) VALUES 
(1, 300, 'Meditação para Iniciantes', 'test-uuid-materia-1', 1, 'Como Meditar: Guia Completo', 'Meditação Fácil para Começar', 'Primeiros Passos na Meditação', 'Aprenda a meditar de forma simples e eficaz', 'meditação, iniciantes, relaxamento', 'A meditação é uma prática milenar...', '<p>Conteúdo da matéria sobre meditação...</p>', null, 'como-meditar-guia-completo', 'Post para Instagram sobre meditação', '2024-02-01 10:00:00', 'Roteiro do vídeo', '2024-01-15 14:30:00', '2024-01-15 14:00:00', null, 1, 1, 'PROCESSAR');

-- Insert test FAQ
INSERT INTO tbl_faq (id, uuid_requisicao, pergunta, resposta, id_materia) VALUES 
(1, 'test-uuid-faq-1', 'Quanto tempo devo meditar?', 'Comece com 5-10 minutos por dia', 1),
(2, 'test-uuid-faq-2', 'Preciso de equipamentos especiais?', 'Não, apenas um local tranquilo', 1),
(3, 'test-uuid-faq-3', 'Posso meditar deitado?', 'É melhor sentar para evitar adormecer', 1);

-- Insert test materia-tag relationships
INSERT INTO tbl_materia_tag (id_materia, id_tag) VALUES 
(1, 1),
(1, 2);
