package br.com.fiap.techchallenge.agendamento.dto;

import br.com.fiap.techchallenge.agendamento.model.StatusConsulta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaNotificacaoDTO {

    private Long id;
    private Long medicoId;
    private Long pacienteId;
    private String pacienteNome;
    private String pacienteEmail;
    private String pacienteTelefone;
    private LocalDateTime dataHora;
    private StatusConsulta status;
    private String tipoNotificacao; // "CRIACAO" ou "ATUALIZACAO"

}
