package br.com.fiap.techchallenge.agendamento.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "consultas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Paciente é obrigatório")
    @ManyToOne(fetch = FetchType.EAGER) // Eager para simplificar
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "enfermeiro_id")
    private Enfermeiro enfermeiro;

    @NotNull(message = "Data e hora da consulta são obrigatórias")
    @Future(message = "A data da consulta deve ser no futuro")
    @Column(nullable = false)
    private LocalDateTime dataHora;

    //@NotNull(message = "Status da consulta é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusConsulta status;

    @Column(columnDefinition = "TEXT")
    private String observacoes;
}
