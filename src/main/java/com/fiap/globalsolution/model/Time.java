package com.fiap.globalsolution.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "time")
public class Time {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_time")
    @SequenceGenerator(name = "seq_time", sequenceName = "seq_time", allocationSize = 1)
    @Column(name = "id_time")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_gerente", nullable = false)
    private Gerente gerente;

    @NotBlank
    @Column(name = "nome_time", nullable = false, length = 100)
    private String nomeTime;

    @Column(length = 500)
    private String descricao;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDate dataCriacao;

    @Builder.Default
    @Column(nullable = false, length = 20)
    private String status = "ATIVO";

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "funcionario_time",
        joinColumns = @JoinColumn(name = "id_time"),
        inverseJoinColumns = @JoinColumn(name = "id_funcionario")
    )
    private List<Funcionario> funcionarios;
}
