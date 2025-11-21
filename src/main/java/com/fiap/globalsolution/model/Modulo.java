package com.fiap.globalsolution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "modulo")
public class Modulo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_modulo")
    @SequenceGenerator(name = "seq_modulo", sequenceName = "seq_modulo", allocationSize = 1)
    @Column(name = "id_modulo")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_curso", nullable = false)
    private Curso curso;

    @NotNull
    @Column(name = "numero_modulo", nullable = false)
    private Integer numeroModulo;

    @NotBlank
    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(length = 1000)
    private String descricao;

    @Lob
    @Column(columnDefinition = "CLOB")
    private String conteudo;

    @NotNull
    @Min(1)
    @Column(name = "duracao_minutos", nullable = false)
    private Integer duracaoMinutos;

    @NotNull
    @Column(nullable = false)
    private Integer ordem;

    @Builder.Default
    @Column(nullable = false, length = 20)
    private String status = "ATIVO";
}
