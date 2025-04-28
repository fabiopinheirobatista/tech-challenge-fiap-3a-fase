package br.com.fiap.techchallenge.agendamento.repository;

import br.com.fiap.techchallenge.agendamento.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    List<Consulta> findByPacienteId(Long pacienteId);

    @Query("SELECT c FROM Consulta c WHERE c.paciente.id = :pacienteId AND c.dataHora > :agora")
    List<Consulta> findConsultasFuturasByPacienteId(@Param("pacienteId") Long pacienteId, @Param("agora") LocalDateTime agora);

    List<Consulta> findByMedicoId(Long medicoId);

    List<Consulta> findByEnfermeiroId(Long enfermeiroId);
}

