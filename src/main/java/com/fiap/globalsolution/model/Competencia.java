package com.fiap.globalsolution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "competencia")
public class Competencia {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_competencia")
    @SequenceGenerator(name = "seq_competencia", sequenceName = "seq_competencia", allocationSize = 1)
    @Column(name = "id_competencia")
    private Long id;

    @NotBlank
    @Column(name = "nome_competencia", nullable = false, unique = true, length = 100)
    private String nomeCompetencia;

    @Column(length = 500)
    private String descricao;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String categoria;

    @Builder.Default
    @Column(name = "nivel_mercado", length = 50)
    private String nivelMercado = "EM_ALTA";

    @CreationTimestamp
    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDate dataCadastro;

    @Builder.Default
    @Column(nullable = false, length = 20)
    private String status = "ATIVO";
}
