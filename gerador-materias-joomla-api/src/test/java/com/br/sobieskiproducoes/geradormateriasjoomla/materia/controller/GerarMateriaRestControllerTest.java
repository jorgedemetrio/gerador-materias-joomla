package com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.PublicarDTO;
import com.br.sobieskiproducoes.geradormateriasjoomla.materia.controller.dto.SugerirMateriaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
    "wiremock.server.port=8089"
})
public class GerarMateriaRestControllerTest {

  private static WireMockServer wireMockServer;
  private static final ObjectMapper objectMapper = new ObjectMapper();

  @BeforeAll
  static void setUp() throws Exception {
    wireMockServer = new WireMockServer(WireMockConfiguration.options().port(8089));
    wireMockServer.start();
    WireMock.configureFor("localhost", 8089);
  }

  @AfterAll
  static void tearDown() throws Exception {
    wireMockServer.stop();
  }

  @BeforeEach
  void setupMocks() {
    wireMockServer.resetAll();
    setupChatGPTMocks();
  }

  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @BeforeEach
  void setupMockMvc() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  private void setupChatGPTMocks() {
    // Mock criar thread
    wireMockServer.stubFor(WireMock.post(WireMock.urlEqualTo("/v1/threads"))
        .willReturn(WireMock.aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("{\"id\": \"test-thread-id\"}")));

    // Mock postar mensagem
    wireMockServer.stubFor(WireMock.post(WireMock.urlMatching("/v1/threads/.*/messages"))
        .willReturn(WireMock.aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("{\"id\": \"test-message-id\"}")));

    // Mock iniciar run
    wireMockServer.stubFor(WireMock.post(WireMock.urlMatching("/v1/threads/.*/runs"))
        .willReturn(WireMock.aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("{\"id\": \"test-run-id\", \"status\": \"completed\"}")));

    // Mock ler runner
    wireMockServer.stubFor(WireMock.get(WireMock.urlMatching("/v1/threads/.*/runs/.*"))
        .willReturn(WireMock.aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("{\"id\": \"test-run-id\", \"status\": \"completed\"}")));

    // Mock próximo passo
    wireMockServer.stubFor(WireMock.get(WireMock.urlMatching("/v1/threads/.*/runs/.*/steps"))
        .willReturn(WireMock.aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("{\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"id\": \"step_test\",\n" +
                "      \"status\": \"completed\",\n" +
                "      \"step_details\": {\n" +
                "        \"message_creation\": {\n" +
                "          \"message_id\": \"msg_test\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"completed\"\n" +
                "}")));

    // Mock ler mensagem - Dados da matéria
    wireMockServer.stubFor(WireMock.get(WireMock.urlMatching("/v1/threads/.*/messages/.*"))
        .inScenario("ChatGPT Responses")
        .whenScenarioStateIs("Started")
        .willReturn(WireMock.aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("{\n" +
                "  \"content\": [\n" +
                "    {\n" +
                "      \"text\": {\n" +
                "        \"value\": \"{\\\"titulos\\\": [\\\"Como Meditar: Guia Completo para Iniciantes 🧘\\\", \\\"Meditação Fácil: Primeiros Passos para Relaxar\\\", \\\"Aprenda a Meditar em Casa: Técnicas Simples\\\"], \\\"primeiro-paragrafo\\\": \\\"Descubra como a meditação pode transformar sua vida em apenas 10 minutos por dia! 🌟\\\", \\\"url-proposta\\\": \\\"como-meditar-guia-completo-iniciantes\\\", \\\"meta-descricao\\\": \\\"Aprenda técnicas simples de meditação para iniciantes. Guia completo com passo a passo para começar hoje mesmo! 🧘‍♀️✨\\\", \\\"keywords\\\": \\\"meditação, iniciantes, relaxamento, mindfulness, bem-estar\\\", \\\"tags\\\": [\\\"meditação para iniciantes\\\", \\\"como meditar em casa\\\", \\\"técnicas de relaxamento\\\", \\\"mindfulness para iniciantes\\\", \\\"bem-estar mental\\\"], \\\"faqs\\\": [{\\\"pergunta\\\": \\\"Quanto tempo devo meditar por dia?\\\", \\\"resposta\\\": \\\"Comece com 5-10 minutos diários e aumente gradualmente conforme se sentir confortável.\\\"}, {\\\"pergunta\\\": \\\"Preciso de equipamentos especiais?\\\", \\\"resposta\\\": \\\"Não, apenas um local tranquilo e uma posição confortável são suficientes.\\\"}, {\\\"pergunta\\\": \\\"Posso meditar deitado?\\\", \\\"resposta\\\": \\\"É melhor sentar para manter o foco e evitar adormecer durante a prática.\\\"}, {\\\"pergunta\\\": \\\"Quando verei resultados?\\\", \\\"resposta\\\": \\\"Muitas pessoas sentem benefícios já nas primeiras semanas de prática regular.\\\"}]}\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}"))
        .willSetStateTo("Data Received"));

    // Mock ler mensagem - Conteúdo da matéria
    wireMockServer.stubFor(WireMock.get(WireMock.urlMatching("/v1/threads/.*/messages/.*"))
        .inScenario("ChatGPT Responses")
        .whenScenarioStateIs("Data Received")
        .willReturn(WireMock.aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("{\n" +
                "  \"content\": [\n" +
                "    {\n" +
                "      \"text\": {\n" +
                "        \"value\": \"<h2>Introdução à Meditação</h2><p>A meditação é uma prática milenar que tem ganhado cada vez mais adeptos no mundo moderno. Com apenas alguns minutos por dia, você pode experimentar benefícios significativos para sua saúde mental e física.</p><h3>Benefícios da Meditação</h3><ul><li>Redução do estresse e ansiedade</li><li>Melhora da concentração</li><li>Aumento da autoconsciência</li><li>Melhora da qualidade do sono</li></ul><h3>Como Começar</h3><p>Para iniciar sua jornada na meditação, siga estes passos simples:</p><ol><li>Encontre um local tranquilo</li><li>Sente-se confortavelmente</li><li>Feche os olhos e respire profundamente</li><li>Concentre-se na sua respiração</li></ol><h3>Técnicas Básicas</h3><p>Existem várias técnicas de meditação que você pode experimentar...</p><h3>Perguntas Frequentes</h3><h4>Quanto tempo devo meditar?</h4><p>Para iniciantes, recomenda-se começar com 5-10 minutos por dia.</p><h4>Qual o melhor horário para meditar?</h4><p>O melhor horário é aquele que funciona para sua rotina, seja pela manhã ou à noite.</p><h4>É normal a mente divagar?</h4><p>Sim, é completamente normal. O importante é gentilmente retornar o foco para a respiração.</p><h3>Conclusão</h3><p>A meditação é uma ferramenta poderosa para o bem-estar. Comece hoje mesmo e transforme sua vida! Siga-nos nas redes sociais para mais dicas de bem-estar e mindfulness. 🧘‍♀️✨</p>\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}"))
        .willSetStateTo("Content Received"));
  }

  @Test
  public void testSugerirMateriaSuccess() throws Exception {
    final SugerirMateriaDTO sugerirDTO = new SugerirMateriaDTO();
    sugerirDTO.setTema("Meditação para Iniciantes");
    sugerirDTO.setTermos(List.of("meditação", "mindfulness", "relaxamento"));
    sugerirDTO.setPublicar(LocalDateTime.now().plusDays(1));
    sugerirDTO.setCategoria(1L);
    sugerirDTO.setAudiencias(List.of("iniciantes", "praticantes de meditação"));

    mockMvc.perform(post("/gerar-materia/sugerir")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(sugerirDTO)))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  public void testPublicarSuccess() throws Exception {
    // Setup mock para publicação no Joomla
    wireMockServer.stubFor(WireMock.post(WireMock.urlEqualTo("/api/index.php/v1/content/articles"))
        .willReturn(WireMock.aResponse()
            .withStatus(201)
            .withHeader("Content-Type", "application/json")
            .withBody("{\n" +
                "  \"data\": {\n" +
                "    \"type\": \"articles\",\n" +
                "    \"id\": \"123\",\n" +
                "    \"attributes\": {\n" +
                "      \"id\": 123,\n" +
                "      \"title\": \"Como Meditar: Guia Completo para Iniciantes\",\n" +
                "      \"alias\": \"como-meditar-guia-completo-iniciantes\",\n" +
                "      \"state\": 1\n" +
                "    }\n" +
                "  }\n" +
                "}")));

    // Setup mock para criação de tags
    wireMockServer.stubFor(WireMock.post(WireMock.urlEqualTo("/api/index.php/v1/content/tags"))
        .willReturn(WireMock.aResponse()
            .withStatus(201)
            .withHeader("Content-Type", "application/json")
            .withBody("{\n" +
                "  \"data\": {\n" +
                "    \"type\": \"tags\",\n" +
                "    \"id\": \"456\",\n" +
                "    \"attributes\": {\n" +
                "      \"id\": 456,\n" +
                "      \"title\": \"meditação para iniciantes\",\n" +
                "      \"alias\": \"meditacao-para-iniciantes\"\n" +
                "    }\n" +
                "  }\n" +
                "}")));

    final PublicarDTO publicarDTO = new PublicarDTO();

    mockMvc.perform(post("/gerar-materia/1/publicar")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(publicarDTO)))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.data.id").value("123"))
        .andExpect(jsonPath("$.data.attributes.id").value(123))
        .andExpect(jsonPath("$.data.attributes.title").value("Como Meditar: Guia Completo para Iniciantes"));

    // Verificar se os mocks foram chamados
    wireMockServer.verify(WireMock.postRequestedFor(WireMock.urlEqualTo("/api/index.php/v1/content/articles")));
    
    // Verificar se a matéria foi atualizada no banco H2
    // Assumindo que existe uma matéria com ID 1 no banco de teste
    // Esta verificação seria feita através de um repository injetado no teste
  }
}
