CREATE TABLE medico (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        nome VARCHAR(255) NOT NULL,
                        especialidade VARCHAR(255) NOT NULL,
                        crm VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE paciente (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nome VARCHAR(255) NOT NULL,
                          data_nascimento DATE NOT NULL,
                          email VARCHAR(255) NOT NULL UNIQUE,
                          telefone VARCHAR(50) NULL
);

CREATE TABLE enfermeiro (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            nome VARCHAR(255) NOT NULL
);

CREATE TABLE consultas (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           paciente_id BIGINT NOT NULL,
                           medico_id BIGINT NOT NULL,
                           enfermeiro_id BIGINT, -- coluna adicionada
                           data_hora DATETIME NOT NULL,
                           status VARCHAR(50),
                           observacoes VARCHAR(255),
                           FOREIGN KEY (paciente_id) REFERENCES paciente(id),
                           FOREIGN KEY (medico_id) REFERENCES medico(id),
                           FOREIGN KEY (enfermeiro_id) REFERENCES enfermeiro(id)
);