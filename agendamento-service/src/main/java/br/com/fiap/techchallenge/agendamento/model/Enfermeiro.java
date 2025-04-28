package br.com.fiap.techchallenge.agendamento.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "enfermeiros")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enfermeiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do enfermeiro é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "COREN do enfermeiro é obrigatório")
    @Column(nullable = false, unique = true)
    private String coren;
}

