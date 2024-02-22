# gerador-materias-joomla
gerador-materias-joomla



1. Exiba uma tela com lista de matérias que foram publicadas
2. Se clicar em novo ele conecta no Joomla 4 para exibir a lista de categorias e exibe na tela os campos: 
   - Tema da sugestão de  matéria  
   - Categorias para essa matéria (extraída do Joomla)
   - Data da publicação
   
3 . Com essas informações ele vai no Chat GPT e pede uma matéria com estratégia de SEO, Marketing e gatilhos mentais e deve exibis os campos na tela:
   - 3 opções de  titulo
   - Conteúdo com o primeiro paragrafo separado do resto da matéria
   - Meta Descrição
   - Palavras Chaves calda curta para salvar em meta Keywords
   -  Palavras chaves calda Longa para Gravar Tags
   - Sugestão de nome de arquivo (com SEO tudo minúsculo esperadas por "-") para gravar em alais

4 . Deve se selecionar um titulo e quando clicar em programar deve chamar Joomla 4 API para gravar e gravar em um banco de dados local MySQL. Após isso exibirá 2 botões: "Gerar Post Instagram / Facebook" e "Gerar Roteiro"

4.1 Se clicar em "Gerar Post Instagram / Facebook" ele chamar o Chat GPT novamente pedindo sugestões de texto  para um post baseado na matéria no Instagram com no máximos 1600 caracteres usando Social Marketing Emojis, e hashtags e as imagens do post para ins Instagram e as imagens do post sem texto, e deve exibir um modal perguntando se aprova, se o usuário aprovar chamara o Facebook API para programar a publicação na mesma data e hora da matéria no Instagram e Facebook e fechar o modal.

4.2 Se clicar em "Gerar Roteiro" ele deve chamar o chat GPT e gerar um roteiro de 1:30 minutos só com falas sobre a matéria e exibir junto com uma sugestão de texto de 1600 caracteres com Social Marketing, Emojis e hashtags.

5. Pode clicar em voltar para exibir a lista de matérias já geradas pela ferramenta.


Nome do projeto: Conteúdo Inteligente
Versão: 1.0
Empresa: Sobieski Produções

Integrações
- Joomla 4
- Chat GPT
- Facebook API


Recursos em suas melhores versão a serem usados
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