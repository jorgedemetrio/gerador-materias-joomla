-- Create tables for test database

-- Categoria table
CREATE TABLE tbl_categoria (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_joomla BIGINT NOT NULL UNIQUE,
    titulo VARCHAR(1000) NOT NULL,
    alias VARCHAR(1000) NOT NULL,
    usar_prompts BOOLEAN DEFAULT TRUE,
    id_pai BIGINT,
    FOREIGN KEY (id_pai) REFERENCES tbl_categoria(id)
);

-- Tag table
CREATE TABLE tbl_tag (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid_requisicao VARCHAR(1000),
    id_joomla BIGINT,
    titulo VARCHAR(1000) NOT NULL,
    alias VARCHAR(500),
    idioma VARCHAR(500)
);

-- Mapa Perguntas table
CREATE TABLE tbl_mapa_perguntas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid_requisicao VARCHAR(1000),
    pergunta VARCHAR(3000) NOT NULL,
    ordem_relevancia INTEGER,
    motivo_sugestao VARCHAR(3000),
    perfil_enquadra VARCHAR(3000),
    stream TEXT,
    id_categoria BIGINT NOT NULL,
    data_sugestao_publicacao DATE,
    FOREIGN KEY (id_categoria) REFERENCES tbl_categoria(id)
);

-- Termos Mapa Pergunta table
CREATE TABLE tbl_mapaperguntas_termo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid_requisicao VARCHAR(1000),
    termo VARCHAR(3000) NOT NULL,
    id_mapa_pergunta BIGINT NOT NULL,
    FOREIGN KEY (id_mapa_pergunta) REFERENCES tbl_mapa_perguntas(id)
);

-- Sub Mapa Perguntas table
CREATE TABLE tbl_mapa_subperguntas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid_requisicao VARCHAR(1000),
    pergunta VARCHAR(3000) NOT NULL,
    id_mapa_pergunta BIGINT NOT NULL,
    FOREIGN KEY (id_mapa_pergunta) REFERENCES tbl_mapa_perguntas(id)
);

-- Carga Massa table
CREATE TABLE tbl_carga_promover (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid_requisicao VARCHAR(1000),
    status VARCHAR(50),
    data_inicio_processo TIMESTAMP,
    data_fim_processo TIMESTAMP,
    data_inicio_carga TIMESTAMP,
    data_fim_carga TIMESTAMP,
    nota VARCHAR(3000),
    requisicao TEXT
);

-- Materia table
CREATE TABLE tbl_materia (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_joomla BIGINT,
    tema_proposto VARCHAR(1000),
    uuid_requisicao VARCHAR(1000),
    titulo_selecionado INTEGER,
    titulo_1 VARCHAR(200),
    titulo_2 VARCHAR(200),
    titulo_3 VARCHAR(200),
    meta_descircao VARCHAR(250),
    keywords VARCHAR(1000),
    introducao VARCHAR(2000),
    materia TEXT,
    stream TEXT,
    alias VARCHAR(500),
    post_instagram VARCHAR(2000),
    data_publicar TIMESTAMP,
    roteiro VARCHAR(2000),
    cricao_na_plataforma TIMESTAMP,
    cricao_banco TIMESTAMP,
    exportado_para_plataforma TIMESTAMP,
    id_categoria BIGINT,
    id_mapa_pergunta BIGINT,
    status VARCHAR(50),
    FOREIGN KEY (id_categoria) REFERENCES tbl_categoria(id),
    FOREIGN KEY (id_mapa_pergunta) REFERENCES tbl_mapa_perguntas(id)
);

-- FAQ table
CREATE TABLE tbl_faq (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid_requisicao VARCHAR(1000),
    pergunta VARCHAR(1000) NOT NULL,
    resposta VARCHAR(1000) NOT NULL,
    id_materia BIGINT,
    FOREIGN KEY (id_materia) REFERENCES tbl_materia(id)
);

-- Materia Tag junction table
CREATE TABLE tbl_materia_tag (
    id_materia BIGINT,
    id_tag BIGINT,
    PRIMARY KEY (id_materia, id_tag),
    FOREIGN KEY (id_materia) REFERENCES tbl_materia(id),
    FOREIGN KEY (id_tag) REFERENCES tbl_tag(id)
);
