ALTER TABLE consultas
    MODIFY COLUMN status ENUM('agendada', 'realizada', 'cancelada');