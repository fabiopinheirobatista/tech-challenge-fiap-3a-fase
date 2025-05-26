package com.hospital.notificacao.dto;

import com.hospital.notificacao.model.Notificacao.StatusNotificacao;
import com.hospital.notificacao.model.Notificacao.TipoNotificacao;
import lombok.AllArgsConstructor;
import lombok.Data; 
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacaoDTO {
    private Long id;
    private Long consultaId;
    private Long pacienteId;
    private String pacienteNome;
    private String pacienteEmail;
    private Long medicoId;
    private String medicoNome;
    private String dataConsulta;
    private String horaConsulta;
    private String especialidade;
    private TipoNotificacao tipoNotificacao;
    private StatusNotificacao status;
    private LocalDateTime dataEnvio;
    private LocalDateTime dataCriacao;
    private String mensagem;
}
