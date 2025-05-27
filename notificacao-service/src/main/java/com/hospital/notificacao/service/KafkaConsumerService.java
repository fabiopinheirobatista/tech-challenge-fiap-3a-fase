package com.hospital.notificacao.service;


import com.hospital.notificacao.dto.NotificacaoDTO;
import com.hospital.notificacao.model.Notificacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class KafkaConsumerService {

    private final NotificacaoService notificacaoService;
    // Formatter para data e hora, ajuste o padrão se necessário
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Autowired
    public KafkaConsumerService(NotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
    }

    @KafkaListener(topics = "${kafka.topic.consulta-events}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeConsultaEvent(Map<String, Object> message) {
        System.out.println("Mensagem Kafka Recebida: " + message); // Log para depuração
        try {
            // Use as chaves corretas do JSON
            String tipoNotificacao = (String) message.get("tipoNotificacao");
            Long consultaId = Long.valueOf(message.get("id").toString()); // Chave 'id'
            Long pacienteId = Long.valueOf(message.get("pacienteId").toString());

            // Chaves que podem não existir - use Optional ou verificação
            Long medicoId = Optional.ofNullable(message.get("medicoId"))
                    .map(Object::toString)
                    .map(Long::valueOf)
                    .orElse(null); // Ou um valor padrão se fizer sentido
            String especialidade = (String) message.get("especialidade"); // Pode ser null
            String statusConsulta = (String) message.get("status"); // Chave 'status'

            // Tratamento do array dataHora
            String dataConsultaStr = "N/A";
            String horaConsultaStr = "N/A";
            Object dataHoraObj = message.get("dataHora");
            if (dataHoraObj instanceof List) {
                try {
                    @SuppressWarnings("unchecked")
                    List<Number> dataHoraList = (List<Number>) dataHoraObj;
                    if (dataHoraList.size() >= 5) {
                        // Monta LocalDateTime a partir da lista [ano, mes, dia, hora, minuto]
                        LocalDateTime dataHora = LocalDateTime.of(
                                dataHoraList.get(0).intValue(),
                                dataHoraList.get(1).intValue(),
                                dataHoraList.get(2).intValue(),
                                dataHoraList.get(3).intValue(),
                                dataHoraList.get(4).intValue()
                        );
                        // Formata para strings separadas (se precisar)
                        dataConsultaStr = dataHora.toLocalDate().toString(); // Formato YYYY-MM-DD
                        horaConsultaStr = dataHora.toLocalTime().toString(); // Formato HH:MM
                    } else {
                        System.err.println("Array dataHora tem tamanho inesperado: " + dataHoraList.size());
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao parsear dataHora: " + dataHoraObj + " - " + e.getMessage());
                }
            }

            // Informações adicionais (já estavam com verificação)
            String pacienteNome = message.containsKey("pacienteNome") ? (String) message.get("pacienteNome") : "Paciente";
            String pacienteEmail = message.containsKey("pacienteEmail") ? (String) message.get("pacienteEmail") : "paciente@exemplo.com";
            String medicoNome = message.containsKey("medicoNome") ? (String) message.get("medicoNome") : "Médico"; // Pode ser null

            // --- Validações Essenciais ---
            if (tipoNotificacao == null || pacienteEmail == null || pacienteEmail.isBlank()) {
                System.err.println("Mensagem inválida recebida, faltando tipoNotificacao ou pacienteEmail: " + message);
                return; // Não processa mensagem inválida
            }

            // Criar notificação baseada no evento recebido
            NotificacaoDTO notificacaoDTO = new NotificacaoDTO();
            notificacaoDTO.setConsultaId(consultaId);
            notificacaoDTO.setPacienteId(pacienteId);
            notificacaoDTO.setPacienteNome(pacienteNome);
            notificacaoDTO.setPacienteEmail(pacienteEmail);
            notificacaoDTO.setMedicoId(medicoId); // Pode ser null
            notificacaoDTO.setMedicoNome(medicoNome); // Pode ser null
            notificacaoDTO.setDataConsulta(dataConsultaStr); // Usa a string formatada
            notificacaoDTO.setHoraConsulta(horaConsultaStr); // Usa a string formatada
            notificacaoDTO.setEspecialidade(especialidade); // Pode ser null
            notificacaoDTO.setDataCriacao(LocalDateTime.now());
            notificacaoDTO.setStatus(Notificacao.StatusNotificacao.PENDENTE);

            // Definir tipo de notificação com base no evento (usando tipoNotificacao)
            // Ajuste os valores do case para corresponder ao que vem no JSON ("CRIACAO", etc.)
            switch (tipoNotificacao) {
                case "CRIACAO": // Valor do JSON
                    notificacaoDTO.setTipoNotificacao(Notificacao.TipoNotificacao.CONSULTA_CRIADA);
                    notificacaoDTO.setMensagem("Nova consulta agendada para " + dataConsultaStr + " às " + horaConsultaStr);
                    break;
                case "ATUALIZACAO": // Ajuste se o valor for diferente
                    notificacaoDTO.setTipoNotificacao(Notificacao.TipoNotificacao.CONSULTA_ATUALIZADA);
                    notificacaoDTO.setMensagem("Consulta atualizada para " + dataConsultaStr + " às " + horaConsultaStr);
                    break;
                case "CANCELAMENTO": // Ajuste se o valor for diferente
                    notificacaoDTO.setTipoNotificacao(Notificacao.TipoNotificacao.CONSULTA_CANCELADA);
                    notificacaoDTO.setMensagem("Consulta em " + dataConsultaStr + " às " + horaConsultaStr + " foi cancelada.");
                    break;
                // Adicione outros tipos se necessário
                default:
                    System.err.println("Tipo de notificação desconhecido recebido: " + tipoNotificacao);
                    // Talvez não salvar ou usar um tipo padrão
                    // notificacaoDTO.setTipoNotificacao(Notificacao.TipoNotificacao.LEMBRETE);
                    // notificacaoDTO.setMensagem("Evento de consulta recebido: " + tipoNotificacao);
                    return; // Ignora tipos desconhecidos
            }

            // Salvar a notificação
            NotificacaoDTO savedNotificacao = notificacaoService.save(notificacaoDTO);
            System.out.println("Notificação salva com ID: " + savedNotificacao.getId());

            // Tentar enviar a notificação imediatamente
            notificacaoService.enviarNotificacao(savedNotificacao.getId());

        } catch (Exception e) {
            // Log de erro mais detalhado
            System.err.println("Erro crítico ao processar mensagem Kafka. Mensagem: " + message);
            e.printStackTrace(); // Imprime o stack trace completo
        }
    }
}
