package br.com.fiap.techchallenge.agendamento.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "medico")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do médico é obrigatório")
    @Column(nullable = false)
    private String nome;

    private String especialidade;

    @NotBlank(message = "CRM do médico é obrigatório")
    @Column(nullable = false, unique = true)
    private String crm;
}

