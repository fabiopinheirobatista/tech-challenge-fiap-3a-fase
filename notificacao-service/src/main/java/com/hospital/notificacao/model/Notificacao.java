package com.hospital.notificacao.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificacoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "consulta_id", nullable = false)
    private Long consultaId;

    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

    @Column(name = "paciente_nome")
    private String pacienteNome;

    @Column(name = "paciente_email", nullable = false)
    private String pacienteEmail;

    @Column(name = "medico_id", nullable = false)
    private Long medicoId;

    @Column(name = "medico_nome")
    private String medicoNome;

    @Column(name = "data_consulta", nullable = false)
    private String dataConsulta;

    @Column(name = "hora_consulta", nullable = false)
    private String horaConsulta;

    private String especialidade;

    @Column(name = "tipo_notificacao", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoNotificacao tipoNotificacao;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusNotificacao status;

    @Column(name = "data_envio")
    private LocalDateTime dataEnvio;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column
    private String mensagem;

    public enum TipoNotificacao {
        CONSULTA_CRIADA,
        CONSULTA_ATUALIZADA,
        CONSULTA_CANCELADA,
        LEMBRETE
    }

    public enum StatusNotificacao {
        PENDENTE,
        ENVIADA,
        FALHA
    }
}
