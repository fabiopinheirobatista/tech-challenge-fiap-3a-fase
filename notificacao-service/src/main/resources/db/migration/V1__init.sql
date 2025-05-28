CREATE TABLE notificacoes (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              consulta_id BIGINT NOT NULL,
                              paciente_id BIGINT NOT NULL,
                              paciente_nome VARCHAR(255),
                              paciente_email VARCHAR(255) NOT NULL,
                              medico_id BIGINT NOT NULL,
                              medico_nome VARCHAR(255),
                              data_consulta VARCHAR(20) NOT NULL,
                              hora_consulta VARCHAR(20) NOT NULL,
                              especialidade VARCHAR(100),
                              tipo_notificacao ENUM('CONSULTA_CRIADA','CONSULTA_ATUALIZADA','CONSULTA_CANCELADA','LEMBRETE') NOT NULL,
                              status ENUM('PENDENTE','ENVIADA','FALHA') NOT NULL,
                              data_envio DATETIME,
                              data_criacao DATETIME NOT NULL,
                              mensagem TEXT
);