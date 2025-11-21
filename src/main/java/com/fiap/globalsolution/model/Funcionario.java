package com.fiap.globalsolution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "funcionario")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_funcionario")
    @SequenceGenerator(name = "seq_funcionario", sequenceName = "seq_funcionario", allocationSize = 1)
    @Column(name = "id_funcionario")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "CPF inválido")
    @Column(unique = true, nullable = false, length = 14)
    private String cpf;

    @NotBlank
    @Email
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(length = 20)
    private String telefone;

    @Column(length = 100)
    private String cargo;

    @Builder.Default
    @Column(name = "nivel_atual", length = 50)
    private String nivelAtual = "INICIANTE";

    @Builder.Default
    @Column(name = "pontos_acumulados")
    private Integer pontosAcumulados = 0;

    @CreationTimestamp
    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDate dataCadastro;

    @Builder.Default
    @Column(nullable = false, length = 20)
    private String status = "ATIVO";

    @ManyToMany(mappedBy = "funcionarios")
    private List<Time> times;
}
