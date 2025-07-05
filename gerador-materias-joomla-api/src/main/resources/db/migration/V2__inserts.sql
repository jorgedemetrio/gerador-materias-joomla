INSERT INTO `novo_banco`.`tbl_permissao`
(`nome`)
VALUES
('ROLE_CRIAR_USUARIO'),
('ROLE_ALTERAR_USUARIO'),
('ROLE_REMOVER_USUARIO'),
('ROLE_VER_USUARIO'),
('ROLE_CRIAR_MATERIA'),
('ROLE_ALTERAR_MATERIA'),
('ROLE_REMOVER_MATERIA'),
('ROLE_VER_MATERIA'),


('ROLE_CRIAR_TAG'),
('ROLE_ALTERAR_TAG'),
('ROLE_REMOVER_TAG'),
('ROLE_VER_TAG'),

('ROLE_CRIAR_AUDIENCIA_EMPRESA'),
('ROLE_ALTERAR_AUDIENCIA_EMPRESA'),
('ROLE_REMOVER_AUDIENCIA_EMPRESA'),
('ROLE_VER_AUDIENCIA_EMPRESA'),

('ROLE_CRIAR_DORES_AUDIENCIA_EMPRESA'),
('ROLE_ALTERAR_DORES_AUDIENCIA_EMPRESA'),
('ROLE_REMOVER_DORES_AUDIENCIA_EMPRESA'),
('ROLE_VER_DORES_AUDIENCIA_EMPRESA'),


('ROLE_CRIAR_TERMO_EMPRESA'),
('ROLE_ALTERAR_TERMO_EMPRESA'),
('ROLE_REMOVER_TERMO_EMPRESA'),
('ROLE_VER_TERMO_EMPRESA'),


('ROLE_CRIAR_CATEGORIA'),
('ROLE_ALTERAR_CATEGORIA'),
('ROLE_REMOVER_CATEGORIA'),
('ROLE_VER_CATEGORIA'),


('ROLE_CRIAR_PALAVRACHAVE'),
('ROLE_ALTERAR_PALAVRACHAVE'),
('ROLE_REMOVER_PALAVRACHAVE'),
('ROLE_VER_PALAVRACHAVE'),

('ROLE_CRIAR_SITE'),
('ROLE_ALTERAR_SITE'),
('ROLE_REMOVER_SITE'),
('ROLE_VER_SITE'),


('ROLE_CARREGAR_PALAVRASCHAVES_LOTE'),
('ROLE_CRIAR_MATERIA_EM_LOTE'),
('ROLE_CRIAR_SUGESTAO_TITULO_EM_LOTE'),

('ROLE_ALTERAR_EMPRESA'),
('ROLE_PUBLICAR_MATERIA');


SELECT UUID() INTO @ID_USUARIO;

INSERT INTO `tbl_usuario` (`id`,`email`,`expira`,`habilitado`,`tipo`,`nome`,`senha`,`usuario`,`cpf`) VALUES
(@ID_USUARIO,'email@gmail.com',null,1,'ADMINISTRADOR','Administrador','$2a$10$j6iKY9lDkA/lWrSvLWCOZOCFoAWg4tU4H3TNXJq98Cov/HO1axXFi','admin','01234567890');



INSERT INTO `tbl_grupo_usuario`(`id`,`nome`,`id_usuario_alterado`,`id_usuario_criador`,`alterado`,`criado`,`ip_criador`)
VALUES
(UUID(),'ADMIN',@ID_USUARIO,@ID_USUARIO,NOW(),NOW(),'127.0.0.1'),
(UUID(),'USUARIO',@ID_USUARIO,@ID_USUARIO,NOW(),NOW(),'127.0.0.1');

INSERT INTO `tbl_grupo_permissao` (`id_grupo`,`id_permissao`)
(SELECT  g.id as id_grupo, p.id as id_permissao FROM `tbl_permissao` AS p, `tbl_grupo_usuario` AS g WHERE g.nome = 'ADMIN' );



INSERT INTO `tbl_grupo_permissao` (`id_grupo`,`id_permissao`)
(SELECT  g.id as id_grupo, p.id as id_permissao FROM `tbl_permissao` AS p, `tbl_grupo_usuario` AS g WHERE p.nome like '%_VER_%' AND g.nome = 'USUARIO' );



INSERT INTO `tbl_usuario_grupo`(`id_usuario`,`id_grupo`)
(SELECT u.id as id_usuario, g.id as id_grupo FROM `tbl_grupo_usuario` AS g, `tbl_usuario` AS u );


















