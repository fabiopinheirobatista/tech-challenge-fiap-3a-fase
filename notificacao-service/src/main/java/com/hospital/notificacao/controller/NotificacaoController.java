package com.hospital.notificacao.controller;

import com.hospital.notificacao.dto.NotificacaoDTO;
import com.hospital.notificacao.model.Notificacao;
import com.hospital.notificacao.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificacoes")
public class NotificacaoController {

    private final NotificacaoService notificacaoService;

    @Autowired
    public NotificacaoController(NotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
    }

    @GetMapping
    public ResponseEntity<List<NotificacaoDTO>> getAllNotificacoes() {
        List<NotificacaoDTO> notificacoes = notificacaoService.findAll();
        return new ResponseEntity<>(notificacoes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificacaoDTO> getNotificacaoById(@PathVariable Long id) {
        NotificacaoDTO notificacao = notificacaoService.findById(id);
        if (notificacao != null) {
            return new ResponseEntity<>(notificacao, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/consulta/{consultaId}")
    public ResponseEntity<List<NotificacaoDTO>> getNotificacoesByConsultaId(@PathVariable Long consultaId) {
        List<NotificacaoDTO> notificacoes = notificacaoService.findByConsultaId(consultaId);
        return new ResponseEntity<>(notificacoes, HttpStatus.OK);
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<NotificacaoDTO>> getNotificacoesByPacienteId(@PathVariable Long pacienteId) {
        List<NotificacaoDTO> notificacoes = notificacaoService.findByPacienteId(pacienteId);
        return new ResponseEntity<>(notificacoes, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<NotificacaoDTO>> getNotificacoesByStatus(@PathVariable Notificacao.StatusNotificacao status) {
        List<NotificacaoDTO> notificacoes = notificacaoService.findByStatus(status);
        return new ResponseEntity<>(notificacoes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<NotificacaoDTO> createNotificacao(@RequestBody NotificacaoDTO notificacaoDTO) {
        NotificacaoDTO newNotificacao = notificacaoService.save(notificacaoDTO);
        return new ResponseEntity<>(newNotificacao, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificacaoDTO> updateNotificacao(@PathVariable Long id, @RequestBody NotificacaoDTO notificacaoDTO) {
        NotificacaoDTO updatedNotificacao = notificacaoService.update(id, notificacaoDTO);
        if (updatedNotificacao != null) {
            return new ResponseEntity<>(updatedNotificacao, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/enviar")
    public ResponseEntity<Void> enviarNotificacao(@PathVariable Long id) {
        boolean enviada = notificacaoService.enviarNotificacao(id);
        if (enviada) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/processar-pendentes")
    public ResponseEntity<Void> processarNotificacoesPendentes() {
        notificacaoService.processarNotificacoesPendentes();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificacao(@PathVariable Long id) {
        notificacaoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
