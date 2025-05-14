package com.hospital.notificacao.repository;

import com.hospital.notificacao.model.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
    List<Notificacao> findByConsultaId(Long consultaId);
    List<Notificacao> findByPacienteId(Long pacienteId);
    List<Notificacao> findByStatus(Notificacao.StatusNotificacao status);
    List<Notificacao> findByTipoNotificacao(Notificacao.TipoNotificacao tipoNotificacao);
}
