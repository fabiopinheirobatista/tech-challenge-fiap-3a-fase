package br.com.fiap.techchallenge.agendamento.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;


@Configuration
public class KafkaTopicConfig {


    @Value("${kafka.topic.consulta.nome}")
    private String nomeTopicoConsulta;

    @Bean
    public NewTopic consultaTopic() {
        return TopicBuilder.name(nomeTopicoConsulta)
                .partitions(1)
                .replicas(1)
                .build();
    }
}

