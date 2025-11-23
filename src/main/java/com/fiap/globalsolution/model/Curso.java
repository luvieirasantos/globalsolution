package com.fiap.globalsolution.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "curso")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_curso")
    @SequenceGenerator(name = "seq_curso", sequenceName = "seq_curso", allocationSize = 1)
    @Column(name = "id_curso")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoriaCurso categoria;

    @NotBlank
    @Column(name = "nome_curso", nullable = false, length = 200)
    private String nomeCurso;

    @Column(length = 1000)
    private String descricao;

    @NotNull
    @Min(1)
    @Column(name = "carga_horaria", nullable = false)
    private Integer cargaHoraria;

    @NotBlank
    @Column(name = "nivel_dificuldade", nullable = false, length = 50)
    private String nivelDificuldade;

    @Builder.Default
    @Column(name = "pontos_conclusao")
    private Integer pontosConclusao = 100;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDate dataCriacao;

    @Builder.Default
    @Column(nullable = false, length = 20)
    private String status = "ATIVO";

    @JsonIgnore
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    private List<Modulo> modulos;
}
