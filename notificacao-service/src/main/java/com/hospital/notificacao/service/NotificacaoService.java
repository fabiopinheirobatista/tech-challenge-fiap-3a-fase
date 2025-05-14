package com.hospital.notificacao.service;

import com.hospital.notificacao.dto.NotificacaoDTO;
import com.hospital.notificacao.model.Notificacao;
import com.hospital.notificacao.repository.NotificacaoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final EmailService emailService;
    private final ModelMapper modelMapper;

    @Autowired
    public NotificacaoService(NotificacaoRepository notificacaoRepository, 
                             EmailService emailService,
                             ModelMapper modelMapper) {
        this.notificacaoRepository = notificacaoRepository;
        this.emailService = emailService;
        this.modelMapper = modelMapper;
    }

    public List<NotificacaoDTO> findAll() {
        return notificacaoRepository.findAll().stream()
                .map(notificacao -> modelMapper.map(notificacao, NotificacaoDTO.class))
                .collect(Collectors.toList());
    }

    public NotificacaoDTO findById(Long id) {
        Optional<Notificacao> notificacao = notificacaoRepository.findById(id);
        return notificacao.map(value -> modelMapper.map(value, NotificacaoDTO.class)).orElse(null);
    }

    public List<NotificacaoDTO> findByConsultaId(Long consultaId) {
        return notificacaoRepository.findByConsultaId(consultaId).stream()
                .map(notificacao -> modelMapper.map(notificacao, NotificacaoDTO.class))
                .collect(Collectors.toList());
    }

    public List<NotificacaoDTO> findByPacienteId(Long pacienteId) {
        return notificacaoRepository.findByPacienteId(pacienteId).stream()
                .map(notificacao -> modelMapper.map(notificacao, NotificacaoDTO.class))
                .collect(Collectors.toList());
    }

    public List<NotificacaoDTO> findByStatus(Notificacao.StatusNotificacao status) {
        return notificacaoRepository.findByStatus(status).stream()
                .map(notificacao -> modelMapper.map(notificacao, NotificacaoDTO.class))
                .collect(Collectors.toList());
    }

    public NotificacaoDTO save(NotificacaoDTO notificacaoDTO) {
        Notificacao notificacao = modelMapper.map(notificacaoDTO, Notificacao.class);
        
        if (notificacao.getDataCriacao() == null) {
            notificacao.setDataCriacao(LocalDateTime.now());
        }
        
        if (notificacao.getStatus() == null) {
            notificacao.setStatus(Notificacao.StatusNotificacao.PENDENTE);
        }
        
        notificacao = notificacaoRepository.save(notificacao);
        return modelMapper.map(notificacao, NotificacaoDTO.class);
    }

    public NotificacaoDTO update(Long id, NotificacaoDTO notificacaoDTO) {
        if (notificacaoRepository.existsById(id)) {
            Notificacao notificacao = modelMapper.map(notificacaoDTO, Notificacao.class);
            notificacao.setId(id);
            notificacao = notificacaoRepository.save(notificacao);
            return modelMapper.map(notificacao, NotificacaoDTO.class);
        }
        return null;
    }

    public boolean enviarNotificacao(Long id) {
        Optional<Notificacao> notificacaoOpt = notificacaoRepository.findById(id);
        if (notificacaoOpt.isPresent()) {
            Notificacao notificacao = notificacaoOpt.get();
            
            try {
                String assunto = gerarAssuntoNotificacao(notificacao);
                String conteudo = gerarConteudoNotificacao(notificacao);
                
                emailService.enviarEmail(notificacao.getPacienteEmail(), assunto, conteudo);
                
                notificacao.setStatus(Notificacao.StatusNotificacao.ENVIADA);
                notificacao.setDataEnvio(LocalDateTime.now());
                notificacaoRepository.save(notificacao);
                
                return true;
            } catch (Exception e) {
                notificacao.setStatus(Notificacao.StatusNotificacao.FALHA);
                notificacaoRepository.save(notificacao);
                return false;
            }
        }
        return false;
    }

    public void processarNotificacoesPendentes() {
        List<Notificacao> notificacoesPendentes = notificacaoRepository.findByStatus(Notificacao.StatusNotificacao.PENDENTE);
        
        for (Notificacao notificacao : notificacoesPendentes) {
            try {
                String assunto = gerarAssuntoNotificacao(notificacao);
                String conteudo = gerarConteudoNotificacao(notificacao);
                
                emailService.enviarEmail(notificacao.getPacienteEmail(), assunto, conteudo);
                
                notificacao.setStatus(Notificacao.StatusNotificacao.ENVIADA);
                notificacao.setDataEnvio(LocalDateTime.now());
            } catch (Exception e) {
                notificacao.setStatus(Notificacao.StatusNotificacao.FALHA);
            }
            
            notificacaoRepository.save(notificacao);
        }
    }

    public void delete(Long id) {
        notificacaoRepository.deleteById(id);
    }
    
    private String gerarAssuntoNotificacao(Notificacao notificacao) {
        switch (notificacao.getTipoNotificacao()) {
            case CONSULTA_CRIADA:
                return "Confirmação de Agendamento - Hospital";
            case CONSULTA_ATUALIZADA:
                return "Atualização de Consulta - Hospital";
            case CONSULTA_CANCELADA:
                return "Cancelamento de Consulta - Hospital";
            case LEMBRETE:
                return "Lembrete de Consulta - Hospital";
            default:
                return "Notificação - Hospital";
        }
    }
    
    private String gerarConteudoNotificacao(Notificacao notificacao) {
        StringBuilder conteudo = new StringBuilder();
        
        conteudo.append("Olá, ").append(notificacao.getPacienteNome()).append("!\n\n");
        
        switch (notificacao.getTipoNotificacao()) {
            case CONSULTA_CRIADA:
                conteudo.append("Sua consulta foi agendada com sucesso.\n\n");
                break;
            case CONSULTA_ATUALIZADA:
                conteudo.append("Sua consulta foi atualizada.\n\n");
                break;
            case CONSULTA_CANCELADA:
                conteudo.append("Sua consulta foi cancelada.\n\n");
                break;
            case LEMBRETE:
                conteudo.append("Lembrete para sua consulta agendada.\n\n");
                break;
        }
        
        if (notificacao.getTipoNotificacao() != Notificacao.TipoNotificacao.CONSULTA_CANCELADA) {
            conteudo.append("Detalhes da consulta:\n");
            conteudo.append("Data: ").append(notificacao.getDataConsulta()).append("\n");
            conteudo.append("Hora: ").append(notificacao.getHoraConsulta()).append("\n");
            conteudo.append("Médico: Dr(a). ").append(notificacao.getMedicoNome()).append("\n");
            conteudo.append("Especialidade: ").append(notificacao.getEspecialidade()).append("\n\n");
        }
        
        conteudo.append("Em caso de dúvidas, entre em contato com nossa central de atendimento.\n\n");
        conteudo.append("Atenciosamente,\nEquipe do Hospital");
        
        return conteudo.toString();
    }
}
