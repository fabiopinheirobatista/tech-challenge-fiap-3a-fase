package br.com.techchallenge.Controller;

import com.hospital.notificacao.service.NotificacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class BuscarNotificacaoPorIdControllerTest {

    @InjectMocks
    private BuscarNotificacaoPorIdController buscarNotificacaoPorIdController;

    @Mock
    private BuscarNotificacaoPorIdUseCase buscarNotificacaoPorIdUseCase;


    private Long notificacaoId = 1L;
    private NotificacaoDTO notificacaoResponse;

    @BeforeEach
    public void setUp() {
        notificacaoResponse = new NotificacaoDTO(1L, 2L, 3L, "Nome Paciente", "teste@example.com", 4L, "19-05-25", "08:00", "especialidade", "tipo notif", "mensagem teste" );
    }

    @Test
    void deveRetornarNotificacaoComSucesso() {
        when(buscarNotificacaoPorIdUseCase.execute(notificacaoId)).thenReturn(notificacaoResponse);

        ResponseEntity<?> response = buscarNotificacaoPorIdController.buscarPorId(notificacaoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notificacaoResponse, response.getBody());
        verify(buscarNotificacaoPorIdUseCase).execute(notificacaoId);
    }
}