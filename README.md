# ![Nome do projeto: Conteúdo Inteligente](https://img.shields.io/badge/nome_do_projeto-Conteúdo_Inteligente-blue)
# ![Versão: 0.0.1-SNAPSHOT](https://img.shields.io/badge/version-0.0.1SNAPSHOT-blue)
# ![Empresa: Sobieski Produções](https://img.shields.io/badge/empresa-sobieski_produções-blue)
# gerador-materias-joomla
:hammer: Projeto em construção :construction:


![Badge em Desenvolvimento](http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=GREEN&style=for-the-badge)
![GitHub Org's stars](https://img.shields.io/github/stars/jorgedemetrio?style=social)


# Índice 
* [Criação de banco](#Criação-de-banco)



# ![Criação-de-banco]Criação de banco
Execute o script a baixo para cria o banco necessário para testes.
-    create database materia;
-    create user 'materia'@'%.%.%.%' identified by 'materia';
-    create user 'materia'@'localhost' identified by 'materia';
-    grant all on materia.* to  'materia'@'%.%.%.%';
-    grant all on materia.* to  'materia'@'localhost';

# Atividades
## 1. Exiba uma tela com lista de mat�rias que foram publicadas
- Carrega primeiro no banco a lista de categorias. ok

## 2. Se clicar em novo ele conecta no Joomla 4 para exibir a lista de categorias e exibe na tela os campos: 
   - Tema da sugest�o de  mat�ria  
   - Categorias para essa mat�ria (extra�da do Joomla)
   - Data da publica��o
   
## 3 . Com essas informa��es ele vai no Chat GPT e pede uma mat�ria com estrat�gia de SEO, Marketing e gatilhos mentais e deve exibis os campos na tela:
   - 3 op��es de  titulo
   - Conte�do com o primeiro paragrafo separado do resto da mat�ria
   - Meta Descri��o
   - Palavras Chaves calda curta para salvar em meta Keywords
   -  Palavras chaves calda Longa para Gravar Tags
   - Sugest�o de nome de arquivo (com SEO tudo min�sculo esperadas por "-") para gravar em alais

## 4 . Deve se selecionar um titulo e quando clicar em programar deve chamar Joomla 4 API para gravar e gravar em um banco de dados local MySQL. Ap�s isso exibir� 2 bot�es: "Gerar Post Instagram / Facebook" e "Gerar Roteiro"

## 4.1 Se clicar em "Gerar Post Instagram / Facebook" ele chamar o Chat GPT novamente pedindo sugest�es de texto  para um post baseado na mat�ria no Instagram com no m�ximos 1600 caracteres usando Social Marketing Emojis, e hashtags e as imagens do post para ins Instagram e as imagens do post sem texto, e deve exibir um modal perguntando se aprova, se o usu�rio aprovar chamara o Facebook API para programar a publica��o na mesma data e hora da mat�ria no Instagram e Facebook e fechar o modal.

## 4.2 Se clicar em "Gerar Roteiro" ele deve chamar o chat GPT e gerar um roteiro de 1:30 minutos s� com falas sobre a mat�ria e exibir junto com uma sugest�o de texto de 1600 caracteres com Social Marketing, Emojis e hashtags.

## 5. Pode clicar em voltar para exibir a lista de mat�rias j� geradas pela ferramenta.




# Integrações
- Joomla 4
- Chat GPT
- Facebook API


# Recursos em suas melhores vers�o a serem usados
- Java
- Spring Boot
- Thymeleaf
- Spring OpenGeign
- MySQL
- Lombok
- MapperStruct
- Jersey
- Spring JPA 
- Spring Actuator
- Logback 
- JUnit
- Spring WEB
- Maven
- Flywey


# Autores

| <img loading="lazy" src="https://avatars.githubusercontent.com/u/10842384?v=4" width="115" /><br/>
[Jorge Demetrio](https://github.com/jorgedemetrio) |


