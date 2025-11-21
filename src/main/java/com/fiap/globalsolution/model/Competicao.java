package com.fiap.globalsolution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "competicao")
public class Competicao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_competicao")
    @SequenceGenerator(name = "seq_competicao", sequenceName = "seq_competicao", allocationSize = 1)
    @Column(name = "id_competicao")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;

    @NotBlank
    @Column(name = "nome_competicao", nullable = false, length = 200)
    private String nomeCompeticao;

    @Column(length = 1000)
    private String descricao;

    @NotNull
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @NotNull
    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @NotBlank
    @Column(name = "tipo_competicao", nullable = false, length = 50)
    private String tipoCompeticao;

    @Column(name = "meta_pontos")
    private Integer metaPontos;

    @Builder.Default
    @Column(nullable = false, length = 20)
    private String status = "ATIVA";
}
