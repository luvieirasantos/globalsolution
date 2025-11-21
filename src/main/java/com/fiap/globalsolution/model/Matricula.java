package com.fiap.globalsolution.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
@Table(name = "matricula")
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_matricula")
    @SequenceGenerator(name = "seq_matricula", sequenceName = "seq_matricula", allocationSize = 1)
    @Column(name = "id_matricula")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_funcionario", nullable = false)
    private Funcionario funcionario;

    @ManyToOne
    @JoinColumn(name = "id_curso", nullable = false)
    private Curso curso;

    @CreationTimestamp
    @Column(name = "data_matricula", nullable = false, updatable = false)
    private LocalDate dataMatricula;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_conclusao")
    private LocalDate dataConclusao;

    @Builder.Default
    @Column(nullable = false, length = 20)
    private String status = "EM_ANDAMENTO";

    @Builder.Default
    @Min(0)
    @Max(100)
    @Column(name = "percentual_conclusao", columnDefinition = "NUMBER")
    private Integer percentualConclusao = 0;

    @Min(0)
    @Max(10)
    @Column(name = "nota_final", columnDefinition = "NUMBER")
    private Double notaFinal;
}
