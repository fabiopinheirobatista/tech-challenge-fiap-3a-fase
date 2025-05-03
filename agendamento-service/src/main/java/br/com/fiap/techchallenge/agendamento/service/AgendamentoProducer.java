package br.com.fiap.techchallenge.agendamento.service;

import br.com.fiap.techchallenge.agendamento.dto.ConsultaNotificacaoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoProducer {

    private static final Logger logger = LoggerFactory.getLogger(AgendamentoProducer.class);

    private final KafkaTemplate<String, ConsultaNotificacaoDTO> kafkaTemplate;

    @Value("${kafka.topic.consulta.nome}")
    private String nomeTopicoConsulta;

    @Autowired
    public AgendamentoProducer(KafkaTemplate<String, ConsultaNotificacaoDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviarMensagemConsulta(ConsultaNotificacaoDTO dto) {
        try {
            logger.info("Enviando mensagem para o topico Kafka {}:{}", nomeTopicoConsulta, dto);
            // Envia a mensagem usando o ID da consulta como chave (para garantir ordenação por consulta, se necessário)
            kafkaTemplate.send(nomeTopicoConsulta, String.valueOf(dto.getId()), dto);
            logger.info("Mensagem enviada com sucesso para o topico {}", nomeTopicoConsulta);
        } catch (Exception e) {
            logger.error("Erro ao enviar mensagem para o topico Kafka {}: {}", nomeTopicoConsulta, e.getMessage(), e);
            // Implementar lógica de tratamento de erro (ex: retry, DLQ) se necessário
        }
    }
}

