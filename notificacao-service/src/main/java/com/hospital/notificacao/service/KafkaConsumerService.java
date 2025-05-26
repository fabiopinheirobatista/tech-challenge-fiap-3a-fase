package com.hospital.notificacao.service;

import com.hospital.notificacao.dto.NotificacaoDTO;
import com.hospital.notificacao.model.Notificacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class KafkaConsumerService {

    private final NotificacaoService notificacaoService;

    @Autowired
    public KafkaConsumerService(NotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
    }

    @KafkaListener(topics = "${kafka.topic.consulta-events}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeConsultaEvent(Map<String, Object> message) {
        try {
            String eventType = (String) message.get("eventType");
            Long consultaId = Long.valueOf(message.get("consultaId").toString());
            Long pacienteId = Long.valueOf(message.get("pacienteId").toString());
            Long medicoId = Long.valueOf(message.get("medicoId").toString());
            String dataConsulta = (String) message.get("data");
            String horaConsulta = (String) message.get("hora");
            String especialidade = (String) message.get("especialidade");
            String status = (String) message.get("status");
            
            // Informações adicionais que podem estar presentes na mensagem
            String pacienteNome = message.containsKey("pacienteNome") ? (String) message.get("pacienteNome") : "Paciente";
            String pacienteEmail = message.containsKey("pacienteEmail") ? (String) message.get("pacienteEmail") : "paciente@exemplo.com";
            String medicoNome = message.containsKey("medicoNome") ? (String) message.get("medicoNome") : "Médico";
            
            // Criar notificação baseada no evento recebido
            NotificacaoDTO notificacaoDTO = new NotificacaoDTO();
            notificacaoDTO.setConsultaId(consultaId);
            notificacaoDTO.setPacienteId(pacienteId);
            notificacaoDTO.setPacienteNome(pacienteNome);
            notificacaoDTO.setPacienteEmail(pacienteEmail);
            notificacaoDTO.setMedicoId(medicoId);
            notificacaoDTO.setMedicoNome(medicoNome);
            notificacaoDTO.setDataConsulta(dataConsulta);
            notificacaoDTO.setHoraConsulta(horaConsulta);
            notificacaoDTO.setEspecialidade(especialidade);
            notificacaoDTO.setDataCriacao(LocalDateTime.now());
            notificacaoDTO.setStatus(Notificacao.StatusNotificacao.PENDENTE);
            
            // Definir tipo de notificação com base no evento
            switch (eventType) {
                case "CONSULTA_CRIADA":
                    notificacaoDTO.setTipoNotificacao(Notificacao.TipoNotificacao.CONSULTA_CRIADA);
                    notificacaoDTO.setMensagem("Nova consulta agendada");
                    break;
                case "CONSULTA_ATUALIZADA":
                    notificacaoDTO.setTipoNotificacao(Notificacao.TipoNotificacao.CONSULTA_ATUALIZADA);
                    notificacaoDTO.setMensagem("Consulta atualizada");
                    break;
                case "CONSULTA_CANCELADA":
                    notificacaoDTO.setTipoNotificacao(Notificacao.TipoNotificacao.CONSULTA_CANCELADA);
                    notificacaoDTO.setMensagem("Consulta cancelada");
                    break;
                default:
                    notificacaoDTO.setTipoNotificacao(Notificacao.TipoNotificacao.LEMBRETE);
                    notificacaoDTO.setMensagem("Lembrete de consulta");
                    break;
            }
            
            // Salvar a notificação
            NotificacaoDTO savedNotificacao = notificacaoService.save(notificacaoDTO);
            
            // Tentar enviar a notificação imediatamente
            notificacaoService.enviarNotificacao(savedNotificacao.getId());
            
        } catch (Exception e) {
            // Log de erro ao processar a mensagem
            System.err.println("Erro ao processar mensagem Kafka: " + e.getMessage());
        }
    }
}
