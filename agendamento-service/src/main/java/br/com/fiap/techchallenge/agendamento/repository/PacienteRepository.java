package br.com.fiap.techchallenge.agendamento.repository;

import br.com.fiap.techchallenge.agendamento.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    
    Paciente findByEmail(String email);
}
