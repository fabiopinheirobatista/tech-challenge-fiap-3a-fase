package br.com.techchallenge.application.controller.cliente;

import com.hospital.notificacao.service.NotificacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TechChallengeApplication.class)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class BuscarNotificacaoPorIdControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    private NotificacaoEntity notificacaoExistente;

    @BeforeEach
    void setUp() {
        notificacaoRepository.deleteAll();
        notificacaoExistente = notificacaoRepository.save(
                new NotificacaoEntity(1L, 2L, 3L, "Nome Paciente", "teste@example.com", 4L, "19-05-25", "08:00", "especialidade", "tipo notif", "mensagem teste" )
        );
    }

    @Test
    void deveRetornarNotificacaoPorId() throws Exception {
        mockMvc.perform(get("/api/notificacoes/{id}", notificacaoExistente.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Cliente Teste"))
                .andExpect(jsonPath("$.email").value("cliente@teste.com"));
    }

    @Test
    void deveRetornar404_QuandoNotificacaoNaoExiste() throws Exception {
        mockMvc.perform(get("/api/notificacao/999"))
                .andExpect(status().isNotFound());
    }
}
