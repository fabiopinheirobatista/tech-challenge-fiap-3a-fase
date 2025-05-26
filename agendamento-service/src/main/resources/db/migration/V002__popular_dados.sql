-- Inserir 5 Médicos
INSERT INTO medico (nome, especialidade, crm) VALUES
          ('Dr. Vinicius Souza', 'Cardiologia', 'CRM/SP 123456'),
          ('Dr. Fabio Batista', 'Pediatria', 'CRM/SP 234567'),
          ('Dr. Victor Salles', 'Ortopedia', 'CRM/SP 345678'),
          ('Dr. Ricardo Amaro', 'Dermatologia', 'CRM/SP 456789'),
          ('Dr. Alfredo Hipolito', 'Neurologia', 'CRM/SP 567890');

-- Inserir 5 Pacientes com email
INSERT INTO paciente (nome, data_nascimento, email, telefone) VALUES
          ('Alfredo Hipolito', '1985-07-15', 'alfredo.hipolito@exemplo.com', NULL),
          ('Ricardo Amaro', '1992-03-22', 'ricardo.amaro@exemplo.com', NULL),
          ('Victor Salles', '1978-11-05', 'victor.salles@exemplo.com', NULL),
          ('Fabio Batista', '2001-01-30', 'fabio.batista@exemplo.com', NULL),
          ('Vinicius Souza', '1965-09-10', 'vinicius.souza@exemplo.com', NULL);

-- Inserir 5 Enfermeiros
INSERT INTO enfermeiro (nome, coren) VALUES
          ('Enf. Mariana Almeida','AAA'),
          ('Enf. Pedro Rocha','AAA'),
          ('Enf. Sofia Martins','AAA'),
          ('Enf. Lucas Ferreira','AAA'),
          ('Enf. Gabriela Dias','AAA');